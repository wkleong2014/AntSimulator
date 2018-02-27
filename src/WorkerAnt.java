//Worker Ant
import java.lang.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class WorkerAnt extends Ant implements Runnable{
  private static final int NUM_OF_WORKER_REQUIRED = 3;
  private final int NUM_OF_WORK_ACTIONS = 10;
  private final int FORAGE_ENERGY_COST = 10;
  private final int BUILD_ENERGY_COST = 10;
  private final int EXPEDITION_ENERGY_COST = 30;

  private int energy = 100;
  private static int expeditionID = 1;
  private static CyclicBarrier barrier;
  private static Expedition expedition;

  public static int mapProgression = 0;
  public static int buildCount = 0;
  public static int forageCount = 0;
  public static final int BUILD_COST = 0;
  public static final int FORAGE_GAIN = 2;

  public WorkerAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  public static void initialiseCyclicBarrier(){
      barrier = new CyclicBarrier(NUM_OF_WORKER_REQUIRED, new Runnable(){
      //Barrier Action
      public void run(){
        expedition = new Expedition(expeditionID++);
        mapProgression += 10;
        System.out.println("Expedition can begin!!");
      }
    });
  }

  @Override
  public void run(){
    //Number of times WorkerAnt will build/forage/expedition
    for(int i=0;i<NUM_OF_WORK_ACTIONS;i++){

      //Ensures that the print order of the log is not so messy
      // randomDelay(1000);

      Random r = new Random();
      int random = r.nextInt(3);
      if(random == 0 && energy >= BUILD_ENERGY_COST){
        build();
      } else if(random == 1 && energy >= FORAGE_ENERGY_COST){
        forage();
      } else if (random == 2 && mapProgression < 100 && energy >= EXPEDITION_ENERGY_COST){
        try{
          System.out.println(getName() + " is waiting for expedition");
          System.out.println("There are " + (barrier.getNumberWaiting()+1) + " waiting to begin expedition");
          barrier.await(3000, TimeUnit.MILLISECONDS);
          Expedition temp = expedition;
          int currentMapProgression = mapProgression;
          expedition(temp);
          temp.addCounter();
          if(temp.getCounter() == NUM_OF_WORKER_REQUIRED){
            String output = "";
            output += "[";
            for (int j = 1; j <= currentMapProgression/10; j++) {
              output += " * ";
            }
            for (int k = 1; k <= 10 - (currentMapProgression/10); k++) {
              output += " - ";
            }
            output+= "]";
            System.out.println(output + " Current Overall Map Progress is: " + currentMapProgression + "%");
          }
        } catch(Exception e) {
          barrier.reset();
          System.out.println("Please wait for the next batch as there are insufficient WorkerAnts to go on a Expedition");
        }
      } else{
        rest();
      }
    }
  }

  public void expedition(Expedition expedition){
    for(int i=0;i<10;i++){
      expedition.expeditionLock.lock();
      if(expedition.getProgress(expedition.getID()) < 100){
        System.out.println(getName() + " is exploring expedition " + expedition.getID() +" ...");
        expedition.addProgress(expedition.getID(), expedition.getProgress(expedition.getID())+10);
        // System.out.println(expedition.getProgress(expedition.getID())); //Uncomment to check that each expedition is adding correctly
      } else{
        i=10;
      }
      expedition.expeditionLock.unlock();
      randomDelay(1); //the delay here ensures that the Thread can be passed to other WorkerAnt who are in the same expedition.
    }
    energy -= EXPEDITION_ENERGY_COST;
    System.out.println(getName() + " has completed exploring expedition " + expedition.getID());
  }

  public void build(){
    // System.out.println(getName() + " is trying to build");
    randomDelay(2000); //To simulate the time it takes to build something
    boolean sufficient_resource = false;
    try{
      getColony().getStorage().storageLock.lock();
      if(getColony().getStorage().getResources() >= BUILD_COST){
        getColony().getStorage().decResource(BUILD_COST);
        sufficient_resource = true;
        System.out.println(getName() + " has built successfully");
      } else{
        System.out.println("Insufficent Resource");
      }
    }
    finally{
      getColony().getStorage().storageLock.unlock();
    }
    if(sufficient_resource){
      buildCount+=1;
      getColony().incReputation(15);
      energy -= BUILD_ENERGY_COST;
    }
  }

  public void forage(){
    // System.out.println(getName() + " is trying to forage");
    randomDelay(1000); //To simulate the time it takes to forage
    getColony().getStorage().incResource(FORAGE_GAIN);
    forageCount+=1;
    energy -= FORAGE_ENERGY_COST;
    System.out.println(getName() + " forage successfully");
  }

  public void rest(){ // sleep when ant energy == 0
    try{
      System.out.println(getName() + " went to sleep");
      Thread.sleep(1000);
      energy = 100;
      System.out.println(getName() + " ready to work");
    }catch(InterruptedException e){
      System.out.println("WorkerAnt has died from InterruptedException");
    }
  }

  public void randomDelay(int time) { // create this method so no need keep try catch in main code
    try{
      Thread.sleep(time);
    }catch(Exception e){
    }
  }
}
