//Worker Ant
import java.lang.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WorkerAnt extends Ant implements Runnable{
  private int energy = 100;

  public WorkerAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }
  @Override
  public void run(){

  for(int i=0;i<20;i++){ // start to work
      //String temp = Thread.currentThread().getName();
      // System.out.println("WorkerAnt " + getName() + " is running at " + i);
      if(energy > 0){
        if(getColony().getResources() > 0){ //condition might change if we want to build different objects with different resource cost
          Random r = new Random();
          int random = r.nextInt(2);
          if(random == 1){
            randomDelay(10);
            build();
          } else {
            randomDelay(10);
            forage();
          }
        } else{
            randomDelay(10);
          forage();
        }
      } else{
          randomDelay(10);
        rest();
      }
    }
  }

  public void build(){  // when build, minus resource, and add reputation (KIV how much to add), and always check for rank
    Storage.decResource(1);   
    getColony().incReputation(10);
    getColony().upRank();
    energy -=50;
    System.out.println(getName() + " is trying to build");
  }

  public void forage(){ // increase resource 
    Storage.incResource(1); 
    energy -=50;
    System.out.println(getName() + " is trying to forage");
  }

  public void rest(){ // sleep when ant energy == 0
    try{
        System.out.println(getName() + " when to sleep");
        Thread.sleep(10);
        energy = 100;
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
