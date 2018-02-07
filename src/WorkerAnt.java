//Worker Ant
import java.lang.*;

public class WorkerAnt extends Ant implements Runnable{
  private int energy = 100;

  public WorkerAnt(String name, int height, int weight){
    super(name, height, weight);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }

  @Override
  public void run(){
    //layEgg()
    for(int i=0;i<10;i++){
    //  String temp = Thread.currentThread().getName();
      System.out.println("WorkerAnt " + getName() + " is running at " + i);
      // try{
      //   Thread.sleep(10);
      // }catch(Exception e){}
    if(energy != 0) {  // first check if there is energy else rest
      if (Storage.resources() != 0) { // always start with Storage, if empty then go gather resources 
          Storage.decResource();
          Colony.incReputation();
          //build
      } else {
        Storage.incResources();    
      }
    } else {
      rest();
    }
    
    }
  }

  public void build(){

  }

  public void forage(){

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
