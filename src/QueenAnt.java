//Queen Ant
import java.lang.*;
import java.util.Random;
import java.util.concurrent.*;

public class QueenAnt extends Ant implements Runnable{
  private int antId = 1;
  public static ExecutorService exec = Executors.newCachedThreadPool(); //ExecutorService was used to start WorkerAnts threads so that Main waits for both QueenAnt & WorkerAnt Threads
  private final int MAX_NO_OF_EGGS_LAID = 10;                           //Maximum number of times that Queen Ant can lay egg

  public QueenAnt(String name, Colony colony){
    super(name, colony);
  }

  //Creates and starts WorkerThreads based on the Maximum Number of Eggs Laid
  public void layEgg(){
    // Random r = new Random();                          //Uncomment to randomise the number of Ants spawn from each Egg
    // int randomNumOfAnts = r.nextInt(3) + 1;           //Uncomment to randomise the number of Ants spawn from each Egg
    //Number of Ants produced by an egg
    int randomNumOfAnts = 1;
    for(int i=0; i<randomNumOfAnts; i++){
      WorkerAnt ant = new WorkerAnt("Ant_" + antId, getColony());
      antId++;
      Thread t1 = new Thread(ant);
      exec.submit(t1);
    }
    //Adds Colony Size based on number of Ants spawn from each Egg
    getColony().incSize(randomNumOfAnts);
  }

  //QueenAnt have to begin by Establishing her Colony
  public void establishColony(){
    setColony(new Colony());
    getColony().printInfo();
  }

  @Override
  public void run(){
    System.out.println("QueenAnt is Running...");
    System.out.println("QueenAnt is Establishing her Colony");

    establishColony();
    WorkerAnt.initialiseCyclicBarrier();

    //QueenAnt will lay until the maximum number of eggs laid
    for(int i=1;i<=MAX_NO_OF_EGGS_LAID;i++){
      try {
        /*
          Can be increase to slow down the creation & start of each WorkerAnt threads, will affect concurrency depending
          on NUM_OF_WORK_ACTIONS each WorkerAntthread will do and its initial delay per work action
        */
        Thread.sleep(1);
      } catch (InterruptedException e) {
      }
      layEgg();
      System.out.println("QueenAnt is laying Egg for the " + i + " times");
    }
    exec.shutdown();
  }

}
