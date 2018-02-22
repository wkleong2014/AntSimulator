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

  public WorkerAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  @Override
  public void run(){
    barrier = new CyclicBarrier(3, new Runnable(){
      public void run(){
        // harvest();
        System.out.println("Harvesting can begin!!");
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
        } else{
          try{ //AKA harvest need to wait
            System.out.println(Thread.currentThread().getName() + " is waiting to harvest");
            System.out.println("There are " + (barrier.getNumberWaiting()+1) + " waiting to begin harvesting");
            barrier.await();
            //3 ,4 ,9
            System.out.println(Thread.currentThread().getName() + " is currently harvesting...");
          } catch(Exception e) {}
        }
      } else{
        rest();
      }
    }
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

  public void harvest(){
    for(int i=0;i<10;i++){
      System.out.println(Thread.currentThread().getName() + " is currently harvesting...");
    }

  }

  public void randomDelay(int time) { // create this method so no need keep try catch in main code
    try{
      Thread.sleep(time);
    }catch(Exception e){}
    }
  }
