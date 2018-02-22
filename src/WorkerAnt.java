//Worker Ant
import java.lang.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;

public class WorkerAnt extends Ant implements Runnable{
  private int energy = 100;
  public static int buildCount = 0;
  public static int forageCount = 0;
  public static final int BUILD_COST = 0;
  public static final int FORAGE_GAIN = 2;
  public static CyclicBarrier barrier;
  private int expeditionID = 1;
  public static Expedition expedition;
  private static int mapProgression = 0;
  private final int NUM_OF_WORKER_REQUIRED = 3;

  public WorkerAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  @Override
  public void run(){
    barrier = new CyclicBarrier(NUM_OF_WORKER_REQUIRED, new Runnable(){
      public void run(){
        // harvest();
        expedition = new Expedition(expeditionID++);
        mapProgression += 10;
        System.out.println("Expedition can begin!!");
      }
    });

    for(int i=0;i<100;i++){ // start to work: run 10 times each WorkerAnt
      /*
      WorkerAnt can always try to build/forage
      */

      randomDelay(1000);

      if(energy > 0){
        Random r = new Random();
        int random = r.nextInt(3);
        if(random == 0){
          build();
        } else if(random == 1){
          forage();
        } else if (random == 2 && mapProgression < 100){
            try{ //AKA harvest need to wait
              System.out.println(getName() + " is waiting to harvest");
              System.out.println("There are " + (barrier.getNumberWaiting()+1) + " waiting to begin harvesting");
              barrier.await();
              //3 ,4 ,9
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
          } catch(Exception e) {}
          }
        } else{
          rest();
        }
      }
    }

    public void expedition(Expedition expedition){ //3 local version of expeditions
      randomDelay(1000);
      while(expedition.getProgress(expedition.getID()) < 100){
        System.out.println(getName() + " is exploring expedition " + expedition.getID() +" ...");
        expedition.addProgress(expedition.getID(), expedition.getProgress(expedition.getID())+10);
      }
      System.out.println(getName() + " has completed exploring expedition " + expedition.getID());
    }

    public void build(){  // when build, minus resource, and add reputation (KIV how much to add), and always check for rank
      System.out.println(getName() + " is trying to build");
      randomDelay(10);
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
        energy -= 5;
      }
    }

    public void forage(){ // increase resource
      System.out.println(getName() + " is trying to forage");
      randomDelay(10);
      getColony().getStorage().incResource(FORAGE_GAIN);
      forageCount+=1;
      energy -= 5;
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
      }catch(Exception e){}
      }
    }
