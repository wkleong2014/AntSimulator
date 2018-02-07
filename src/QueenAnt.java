//Queen Ant
import java.lang.*;
import java.util.concurrent.*;

public class QueenAnt extends Ant implements Runnable{
  private final int MAX_NO_OF_EGGS_LAID = 10;
  private int antId = 1;
  public static ExecutorService exec = Executors.newFixedThreadPool(100000);

  public QueenAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }

  public void layEgg(){
    int randomNumOfEgg = 1; //to change this to random int
    for(int i=0; i<randomNumOfEgg; i++){
      WorkerAnt ant = new WorkerAnt("Ant_" + antId, 20, 20, getColony());
      antId++;
      Thread t1 = new Thread(ant);
      exec.submit(t1);
    }
  }

  public void establishColony(){
    setColony(new Colony());
    getColony().printStuff();
  }

  @Override
  public void run(){
    System.out.println("QueenAnt is Running...");
    System.out.println("QueenAnt is Establishing her Colony");
    establishColony();

    //Timer for QueenAnt to lay eggs
    for(int i=0;i<MAX_NO_OF_EGGS_LAID;i++){
      try {
        Thread.sleep(4);
      } catch (InterruptedException e) {

      }
      layEgg();
      System.out.println("QueenAnt is laying Egg for the " + i + " times");

    }
    exec.shutdown();
  }

}
