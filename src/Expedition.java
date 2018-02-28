import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;

public class Expedition{
  private int id;
  private HashMap<Integer, Double> progress = new HashMap<Integer, Double>(); //HashMap to store Expedition's progress <ExpeditionID, CurrentProgress>
  private ReentrantLock expeditionLock = new ReentrantLock();                 //Each Expedition will have its own lock
  private int counter = 0;                                                    //Is used so that only the last WorkerAnt thread in each expedition will print result

  //final variables for Expedition
  public final int MAX_PROGRESS = 100;                                                            //Sets the maximum number of progression (default: 100)
  public final double PROGRESS_PER_ANT = 10.0;                                                    //Sets the number of progression added per WorkerAnt's exploration
  public final int NUMBER_OF_EXPLORES_REQUIRED = (int)Math.ceil(MAX_PROGRESS / PROGRESS_PER_ANT); //Calculates the number of required WorkerAnt's exploration

  public Expedition(int id){
    this.id = id;
    //Sets CurrentProgress to 0.0
    progress.put(id, 0.0);
  }

  //Getter Methods
  public int getCounter(){return counter;}

  public int getID(){return id;}

  public double getProgress(int id){return progress.get(id);}

  //Increment Methods
  public void addCounter(){
    counter++;
  }

  public void addProgress(int id, double amount){
      progress.put(id, amount);
  }

  public void lockExpedition(){
    expeditionLock.lock();
  }

  public void unlockExpedition(){
    expeditionLock.unlock();
  }

}
