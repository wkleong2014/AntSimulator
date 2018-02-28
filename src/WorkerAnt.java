import java.lang.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WorkerAnt extends Ant implements Runnable{
  private int energy = 100;
  private static int expeditionID = 1;
  private static CyclicBarrier barrier;                 //At any point of time, there will only be one barrier
  private static Expedition expedition;                 //Will have only one static instance of expedition object shared among all WorkerAnt threads

  //final variables that affects the simulation logic
  private static final int NUM_OF_WORKER_REQUIRED = 3;  //Number of WorkerAnts required for an Expedition
  private final int NUM_OF_WORK_ACTIONS = 30;           //Total number of work actions a WorkerAnt can perform
  private final int FORAGE_ENERGY_COST = 10;            //Sets energy cost for foraging (should always be the lowest among the 3)
  private final int BUILD_ENERGY_COST = 10;             //Sets energy cost for building
  private final int EXPEDITION_ENERGY_COST = 30;        //Sets energy cost for expedition

  //variables used for printing final output
  public static final int OVERALL_MAP_PARTS = 15;
  public static int mapParts = 0;
  public static int buildCount = 0;
  public static int forageCount = 0;
  public static final int BUILD_COST = 10;
  public static final int FORAGE_GAIN = 5;

  public WorkerAnt(String name, Colony colony){
    super(name, colony);
  }

  //Initialises the CyclicBarrier so that WorkerAnts can wait at the barrier when they are going for an expedition
  public static void initialiseCyclicBarrier(){
      barrier = new CyclicBarrier(NUM_OF_WORKER_REQUIRED, new Runnable(){
      //Barrier Action will occur only ONCE there are the minimum number of threads (NUM_OF_WORKER_REQUIRED) waiting at the barrier
      public void run(){
        expedition = new Expedition(expeditionID++);
        mapParts += 1;
        System.out.println("Expedition can begin!!");
      }
    });
  }

  @Override
  public void run(){
    //Number of times WorkerAnt will build/forage/expedition
    for(int i=0;i<NUM_OF_WORK_ACTIONS;i++){

      //randomDelay(1000); //Uncomment this to slow down the log of application

      Random r = new Random();
      int random = r.nextInt(3); //Randomise 0, 1 or 2
      if(random == 0 && energy >= BUILD_ENERGY_COST){
        build();
      } else if(random == 1 && energy >= FORAGE_ENERGY_COST){
        forage();
      } else if (random == 2 && mapParts < OVERALL_MAP_PARTS && energy >= EXPEDITION_ENERGY_COST){
          try{
            //Prints the WorkerAnt that is going for an expedition
            System.out.println(getName() + " is waiting for expedition");
            //Prints number of WorkerAnt threads waiting at barrier
            System.out.println("There are " + (barrier.getNumberWaiting()+1) + " waiting to begin expedition");
            barrier.await(3000, TimeUnit.MILLISECONDS);

            //The following codes will not be executed until NUM_OF_WORKER_REQUIRED (default: 3) threads are waiting at the barrier

            //Avoid the race condition of mapParts by assigning it to a temporary variable
            int currentMapPartsCompleted = mapParts;

            //Stores the currentExpedition as a temporary variable so that counter can be added later to print Overall Map Progress ONCE
            Expedition currentExpedition = expedition;

            //By passing the current static expedition object into expedition method, it can create multiple instances of expedition
            expedition(currentExpedition);
            currentExpedition.addCounter();

            //Check that it is the last thread to print the current map parts completed (ensures no duplication of printing)
            if(currentExpedition.getCounter() == NUM_OF_WORKER_REQUIRED){
              //Adds to a string to prevent any chopped up printing
              String output = "";
              output += "[";
              for (int j = 1; j <= currentMapPartsCompleted; j++) {
                output += " * ";
              }
              for (int k = 1; k <= OVERALL_MAP_PARTS - (currentMapPartsCompleted); k++) {
                output += " - ";
              }
              output+= "]";
              //Prints the current map parts completed and also in percentage
              System.out.println(output + " Map Parts Completed: " + currentMapPartsCompleted + "/" + OVERALL_MAP_PARTS + " ("
              + Math.round(100.0*currentMapPartsCompleted/OVERALL_MAP_PARTS*100.0)/100.0  + "%)");
            }
          } catch(Exception e) {
            //Catches the exception thrown when CyclicBarrier is timeout (default: 3000 miliseconds) or reset
            barrier.reset();
            System.out.println("Please wait for the next batch as there are insufficient WorkerAnts to go on a Expedition");
          }
      } else{
        //When insufficient energy to perform respective task
        rest();
      }
    }
  }

  /*
    Every expedition requires NUMBER_OF_EXPLORES_REQUIRED (default: 10) "explores" to complete the expedition
    Every expedition will add 1 part to the overall map parts
    Each WorkerAnt thread (default: 3) in an expedition will have their own memory of expedition
  */
  public void expedition(Expedition expedition){
    //Each WorkerAnt will at least explore the total number of exploration required (Worst Case scenario where only 1 WorkerAnt completes the expedition on its own)
    for(int i=0;i<expedition.NUMBER_OF_EXPLORES_REQUIRED;i++){
      //Locks expedition before if condition to ensure that the total number of explores is EXACTLY the number of explores required
      expedition.lockExpedition();
      try{
        //As each WorkerAnt Thread has their own memory of expedition, HashMap is used to store & retrieve information about the progress of the same expedition
        if(expedition.getProgress(expedition.getID()) < expedition.MAX_PROGRESS){
          System.out.println(getName() + " is exploring expedition " + expedition.getID() + " ...");
          expedition.addProgress(expedition.getID(), expedition.getProgress(expedition.getID()) + expedition.PROGRESS_PER_ANT);
          // System.out.println(expedition.getProgress(expedition.getID())); //Uncomment to check that each expedition is adding correctly
        } else{
          //Once the expedition reaches the maximum progress (default: 10), all WorkerAnts will stop exploring
          i=expedition.NUMBER_OF_EXPLORES_REQUIRED;
        }
      }finally{
        expedition.unlockExpedition();
      }
      randomDelay(1000); //the delay here ensures that the Thread can be passed to other WorkerAnt who are in the same expedition. Increasing this will also reflect that multiple expedition can run at the same time
    }
    energy -= EXPEDITION_ENERGY_COST;
    System.out.println(getName() + " has completed exploring expedition " + expedition.getID());
  }

  //Decrease resources of colony's storage and increases reputation of colony
  public void build(){
    //To simulate the time it takes to build something
    randomDelay(2000);
    //temporary boolean is used so that storage can be unlock while increasing colony's reputation
    boolean sufficient_resource = false;
    try{
      //Locks storage before if condition to ensure that resouces will never be negative
      getColony().getStorage().lockStorage();
      if(getColony().getStorage().getResources() >= BUILD_COST){
        int currentResources = getColony().getStorage().decResource(BUILD_COST);
        sufficient_resource = true;
        System.out.println(getName() + " has built successfully");
        System.out.println("Current Resources: " + currentResources);
      } else{
        System.out.println("Insufficent Resource to build");
      }
    }
    finally{
      getColony().getStorage().unlockStorage();
    }
    if(sufficient_resource){
      buildCount+=1;
      getColony().incReputation(15);
      energy -= BUILD_ENERGY_COST;
    } else{
      forage();
    }
  }

  //Increases resources of colony's storage
  public void forage(){
    //To simulate the time it takes to forage
    randomDelay(1000);
    int currentResources = getColony().getStorage().incResource(FORAGE_GAIN);
    forageCount+=1;
    energy -= FORAGE_ENERGY_COST;
    System.out.println(getName() + " forage successfully");
    System.out.println("Current Resources: " + currentResources);
  }

  // Sleep when WorkerAnt's energy is insufficient to perform the respective task
  public void rest(){
    System.out.println(getName() + " went to sleep");
    randomDelay(1000);
    energy = 100;
    System.out.println(getName() + " ready to work");
  }

   //Method used to sleep current thread
  public void randomDelay(int time) {
    try{
      Thread.sleep(time);
    }catch(Exception e){
    }
  }
}
