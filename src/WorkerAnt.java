//Queen Ant
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
      String temp = Thread.currentThread().getName();
      System.out.println("WorkerAnt " + temp + " is running at " + i);
      // try{
      //   Thread.sleep(10);
      // }catch(Exception e){}

    }

  }

}
