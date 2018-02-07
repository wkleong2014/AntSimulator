//Queen Ant
import java.lang.*;
import java.util.concurrent.*;

public class QueenAnt extends Ant implements Runnable{
  private int no_of_eggs_laid = 0;
  private final int DEATH = 10;
  private int antId = 0;
  public static ExecutorService exec = Executors.newFixedThreadPool(100000);
 

  public QueenAnt(String name, int height, int weight){
    super(name, height, weight);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }

  public void layEgg(){
    WorkerAnt ant = new WorkerAnt("Ant" + antId, 20, 20);
    antId++;
    Thread t1 = new Thread(ant);
    // t1.start();
    exec.submit(t1);
  }

  public void establishColony(){
    Colony colony = new Colony();
    colony.printStuff();
  }

  @Override
  public void run(){
    System.out.println("QueenAnt is Running...");
    System.out.println("QueenAnt is Establishing her Colony");
    establishColony();

    //Timer for QueenAnt to lay eggs
    for(int i=0;i<5;i++){
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
