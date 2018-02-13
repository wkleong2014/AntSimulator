//Worker Ant
import java.lang.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WorkerAnt extends Ant implements Runnable{
  private int energy = 100;
  public static int buildCount = 0;
  public static int forageCount = 0;
  public WorkerAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }
  @Override
  public void run(){

    for(int i=0;i<10;i++){ // start to work: run 10 times each WorkerAnt
      //For more obvious race condition, final storage output should be -350

      if(i%2==0){
        getColony().getStorage().storageLock.lock();
        if(getColony().getStorage().getResources() >= 10){
          build();
        } else{
          forage();
        }
        getColony().getStorage().storageLock.unlock();
      } else{
        forage();
      }

      // if(energy > 0){
      //   //lock here
      //   if(getColony().getStorage().getResources() > 0){ //condition might change if we want to build different objects with different resource cost
      //     Random r = new Random();
      //     int random = r.nextInt(2);
      //     if(random == 1){
      //       randomDelay(1000);
      //       build();
      //     } else {
      //       randomDelay(1000);
      //       forage();
      //     }
      //   } else{
      //       randomDelay(1000);
      //     forage();
      //   }
      // } else{
      //     randomDelay(1000);
      //   rest();
      // }
    }
  }

  public void build(){  // when build, minus resource, and add reputation (KIV how much to add), and always check for rank
    // System.out.println(getName() + " Before -10: " + getColony().getStorage().getResources());
    // randomDelay(100);
    getColony().getStorage().decResource(10);
    // System.out.println(getName() + " After -10: " + getColony().getStorage().getResources());
    buildCount++;
    // getColony().incReputation(10);
    // getColony().upRank();
    energy -= 20;
    // System.out.println(getName() + " is trying to build");
  }

  public void forage(){ // increase resource
    // System.out.println(getName() + " Before +1: " + getColony().getStorage().getResources());
    // randomDelay(100);
    getColony().getStorage().incResource(1);
    // System.out.println(getName() + " After +1: " + getColony().getStorage().getResources());
    forageCount++;
    energy -= 20;
    // System.out.println(getName() + " is trying to forage");
  }

  public void rest(){ // sleep when ant energy == 0
    try{
      // System.out.println(getName() + " went to sleep");
      Thread.sleep(4000);
      energy = 100;
      // System.out.println(getName() + " ready to work");
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
