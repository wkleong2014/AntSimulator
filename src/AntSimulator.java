import java.lang.*;

public class AntSimulator{

  public static void main(String[] args){
    QueenAnt queenAnt = new QueenAnt("Queen Ant", 20, 20);
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

    // System.out.println(queenAnt);
    // System.out.println("***** Simulation Begun *****");
    colony.printStuff();
    System.out.println("***** Simulation Ended *****");

  }
}
