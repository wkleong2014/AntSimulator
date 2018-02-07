//Worker Ant
import java.lang.*;
import java.util.Random;

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
    //layEgg()
    for(int i=0;i<10;i++){
      //String temp = Thread.currentThread().getName();
      // System.out.println("WorkerAnt " + getName() + " is running at " + i);
      // try{
      //   Thread.sleep(10);
      // }catch(Exception e){}
      if(energy > 0){
        if(getColony().getResources() > 0){ //condition might change if we want to build different objects with different resource cost
          Random r = new Random();
          int random = r.nextInt(2);
          if(random == 1){
            build();
          } else {
            forage();
          }
        } else{
          forage();
        }
      } else{
        rest();
      }
    }
  }

  public void build(){
    System.out.println(getName() + " is trying to build");
  }

  public void forage(){
    System.out.println(getName() + " is trying to forage");
  }

  public void rest(){
    try{
        Thread.sleep(10000);
        energy = 100;
    }catch(InterruptedException e){
      System.out.println("WorkerAnt has died from InterruptedException");
    }
  }

}
