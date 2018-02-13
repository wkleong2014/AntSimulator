//Queen Ant
import java.lang.*;
import java.util.concurrent.*;

public class QueenAnt extends Ant implements Runnable{
  private final int MAX_NO_OF_EGGS_LAID = 10; //Maximum number of times that Queen Ant can layEgg()
  private int antId = 1;
  public static ExecutorService exec = Executors.newFixedThreadPool(100000);

  public QueenAnt(String name, int height, int weight, Colony colony){
    super(name, height, weight, colony);
  }

  public void layEgg(){
    //TODO - Change randomNumOfAnts into Random r = new Random()
    int randomNumOfAnts = 1; //Number of Ants produced by an egg
    for(int i=0; i<randomNumOfAnts; i++){
      WorkerAnt ant = new WorkerAnt("Ant_" + antId, 20, 20, getColony());
      antId++;
      Thread t1 = new Thread(ant);
      exec.submit(t1);
    }
    getColony().setSize(randomNumOfAnts);
  }

  public void establishColony(){
    setColony(new Colony());
    // QueenAnt auditor = new QueenAnt("Auditor", 20, 20, getColony());
    // Thread audit = new Thread(auditor::auditResources);
    // audit.start();
    getColony().printStuff();
  }

  @Override
  public void run(){
    System.out.println("QueenAnt is Running...");
    System.out.println("QueenAnt is Establishing her Colony");
    establishColony();

    //Timer for QueenAnt to lay eggs
    for(int i=1;i<=MAX_NO_OF_EGGS_LAID;i++){
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {

      }
      layEgg();
      System.out.println("QueenAnt is laying Egg for the " + i + " times");
    }
    exec.shutdown();
  }

  // public void auditResources(){
  //   while(!exec.isTerminated()){
  //     System.out.println("Colony Resources: " + getColony().getStorage().getResources());
  //   }; //Check if WorkerAnt's Threads are all terminated
  // }

}
