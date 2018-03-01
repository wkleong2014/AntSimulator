import java.lang.*;

public class AntSimulator{
  public static void main(String[] args){

    //Starts the timer which is used for analysing the concurrency in our application
    StopWatch timer = new StopWatch();
    timer.start();

    System.out.println("******* Simulation Begun *******");
    System.out.println("Enter C to get information about Colony");

    QueenAnt queenAnt = new QueenAnt("Queen Ant", null);
    Thread queen_thread = new Thread(queenAnt);
    queen_thread.start();

    ReadUserInputRunnable reader_runnable = null;

    //Check if WorkerAnt's threads are all terminated
    while(!QueenAnt.exec.isTerminated()){
      //initialise and start thread that reads user inputs once colony has been established
      if(queenAnt.getColony()!= null && reader_runnable == null){
        reader_runnable = new ReadUserInputRunnable(queenAnt.getColony());
        Thread reader_thread = new Thread(reader_runnable);
        reader_thread.start();
      }
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){};
    };
    timer.stop();
    //kill reader thread when all WorkerAnt's threads have been terminated
    reader_runnable.kill();

    //Print final summary of all information about the Ant Simulation
    System.out.println("********************************");
    System.out.println("Final Rank of Colony: " + queenAnt.getColony().getCurrentRank());
    System.out.println("Final Size of Colony: " + queenAnt.getColony().getSize());
    System.out.println("Final Reputation of Colony: " + queenAnt.getColony().getReputation());
    System.out.println("Final Storage of Colony: " + queenAnt.getColony().getStorage().getResources());
    System.out.println("Final Map Parts Completed: " + WorkerAnt.mapParts + "/" + WorkerAnt.OVERALL_MAP_PARTS + " ("
    + Math.round(100.0*WorkerAnt.mapParts/WorkerAnt.OVERALL_MAP_PARTS*100.0)/100.0  + "%)");
    System.out.println("********************************");
    System.out.println("Expected Reputation: " + (10 + (WorkerAnt.buildCount*15))); //Uncomment this to check resources race condition
    System.out.println("Expected Final Storage: " + (queenAnt.getColony().getStorage().STARTING_RESOURCES - (WorkerAnt.buildCount*WorkerAnt.BUILD_COST)
    + (WorkerAnt.forageCount * WorkerAnt.FORAGE_GAIN))); //Uncomment this to check resources race condition
    System.out.println("Build Count: " + WorkerAnt.buildCount); //Uncomment this to check resources race condition
    System.out.println("Forage Count: " + WorkerAnt.forageCount); //Uncomment this to check resources race condition
    System.out.println("Time elapsed : " + timer.toString());
    System.out.println("******* Simulation Ended *******");

  }
}
