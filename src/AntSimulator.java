import java.lang.*;

public class AntSimulator{

  public static void main(String[] args){
    System.out.println("Starting timer...");
    StopWatch timer = new StopWatch();
    timer.start();

    System.out.println("***** Simulation Begun *****");
    System.out.println("Enter C to get information about Colony");


    QueenAnt queenAnt = new QueenAnt("Queen Ant", 20, 20, null);
    //can replace Thread with ExecutorService
    Thread queen_thread = new Thread(queenAnt);
    queen_thread.start();

    ReadUserInput reader = null;

    while(!QueenAnt.exec.isTerminated()){
      if(queenAnt.getColony()!= null && reader == null){
        reader = new ReadUserInput(queenAnt.getColony());
        Thread reader_thread = new Thread(reader);
        reader_thread.start();
      }
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){};
    }; //Check if WorkerAnt's Threads are all terminated
    reader.kill();

    System.out.println("********************************");
    System.out.println("Final Print of Storage: " + queenAnt.getColony().getStorage().getResources());
    System.out.println("Final Print of Reputation: " + queenAnt.getColony().getReputation());
    System.out.println("Final Print of Rank: " + queenAnt.getColony().getCurrentRank());
    System.out.println("********************************");
    System.out.println("Expected Final Storage: " + (queenAnt.getColony().getStorage().STARTING_RESOURCES - (WorkerAnt.buildCount*WorkerAnt.BUILD_COST) + (WorkerAnt.forageCount * WorkerAnt.FORAGE_GAIN)));
    System.out.println("Expected Reputation: " + (10 + (WorkerAnt.buildCount*15)));
    System.out.println("Build Count: " + WorkerAnt.buildCount);
    System.out.println("Forage Count: " + WorkerAnt.forageCount);
    // print summary
    System.out.println("Time elapsed : " + timer.toString());
    System.out.println("******* Simulation Ended *******");

  }
}
