//Colony
import java.util.concurrent.locks.ReentrantLock;

public class Colony{
  private String currentRank;
  private int reputation;
  private int size;
  private final String[][] COLONY_RANKS = new String[][]{{"Hut", "100"}, {"Village", "200"}, {"Town", "300"}, {"City", "400"}, {"Empire", "500"}};
  private Storage storage;
  private ReentrantLock colonyLock;

  public Colony(){
    reputation = 10;
    size = 1;
    currentRank = COLONY_RANKS[0][0];
    storage = new Storage();
    colonyLock = new ReentrantLock();
  }

  public void setRank(String newRank){
    if(currentRank != newRank){
      currentRank = newRank;
      System.out.println("*******************************************************\nCongratulations! The Colony has achieved a new Rank!");
      printInfo();
    }
  }

  public int getReputation(){return reputation;}

  public void incReputation(int amount){
    try{
      colonyLock.lock();
      Thread.sleep(1); //increase chances of race condition
      reputation += amount;
      Thread.sleep(1); //increase chances of race condition
    } catch(Exception e){
    } finally {
      colonyLock.unlock();
    }
    if(reputation >= Integer.parseInt(COLONY_RANKS[4][1])){ //500
      setRank(COLONY_RANKS[4][0]);
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[3][1])){ //400
      setRank(COLONY_RANKS[2][0]);
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[2][1])){ //300
      setRank(COLONY_RANKS[3][0]);
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[1][1])){ //200
      setRank(COLONY_RANKS[1][0]);
    }
  }

  public void decReputation(int amount){
    try{
      colonyLock.lock();
      Thread.sleep(1); //increase chances of race condition
      reputation -= amount;
      Thread.sleep(1); //increase chances of race condition
    } catch(Exception e){
    } finally {
      colonyLock.unlock();
    }
  }

  public String getCurrentRank(){return currentRank;}

  public void setSize(int amount){
    size += amount;
  }

  public Storage getStorage(){return storage;}

  public void printInfo(){
    colonyLock.lock();
    storage.storageLock.lock();
    System.out.println("*******************************************************\nReputation: " + reputation + "\t\t\tSize: " + size + "\nCurrentRank: " +
    currentRank + "\t\tCurrent Resources: " + storage.getResources() + "\n*******************************************************");
    colonyLock.unlock();
    storage.storageLock.unlock();
  }

}
