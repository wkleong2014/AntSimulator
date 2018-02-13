import java.lang.*;

public class AntSimulator{

  public static void main(String[] args){
    System.out.println("***** Simulation Begun *****");
    // start timer
    System.out.println("Starting timer...");
    StopWatch timer = new StopWatch();
    timer.start();

    QueenAnt queenAnt = new QueenAnt("Queen Ant", 20, 20, null);
    //can replace Thread with ExecutorService
    Thread t1 = new Thread(queenAnt);
    t1.start();

    //submit takes in Thread or Runnable
    // Thread t1 = new Thread(queenAnt);
    // try{
    //   t1.start();
    //   t1.join();
    // } catch(InterruptedException e){
    //
    // }

    while(!QueenAnt.exec.isTerminated()){
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){};
    }; //Check if WorkerAnt's Threads are all terminated
    System.out.println("Final Print of Storage: " + queenAnt.getColony().getStorage().getResources());
    System.out.println("Build Count: " + WorkerAnt.buildCount);
    System.out.println("Forage Count: " + WorkerAnt.forageCount);
    System.out.println("Expected Final Storage: " + (100 - (WorkerAnt.buildCount*10) + (WorkerAnt.forageCount)));
    // print summary
    System.out.println("Time elapsed : " + timer.toString());
    System.out.println("***** Simulation Ended *****");

  }
}
