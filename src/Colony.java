import java.util.concurrent.locks.ReentrantLock;

public class Colony{
  private String currentRank;
  private int reputation;
  private int size;
  private Storage storage;          //Each colony contains only one storage class
  private ReentrantLock colonyLock; //Each colony will have their own lock

  //Stores the Rank and the required reputation to achieve the corresponding Rank <Rank, Reputation Required>
  private final String[][] COLONY_RANKS = new String[][]{{"Hut", "100"}, {"Village", "200"}, {"Town", "300"}, {"City", "400"}, {"Empire", "500"}};

  public Colony(){
    reputation = 10;
    size = 1;
    currentRank = COLONY_RANKS[0][0];
    storage = new Storage();
    colonyLock = new ReentrantLock();
  }

  //Getter Methods
  public String getCurrentRank(){return currentRank;}

  public int getSize(){return size;}

  public Storage getStorage(){return storage;}

  public int getReputation(){return reputation;}

  //Setter Methods
  public void setRank(String newRank){
    if(currentRank != newRank){
      currentRank = newRank;
      System.out.println("*******************************************************\nCongratulations! The Colony has achieved a new Rank!");
      printInfo();
    }
  }

  public void incSize(int amount){
    size += amount;
  }

  //Used when WorkerAnt perform builds which increases the Colony's reputation
  public void incReputation(int amount){
    try{
      colonyLock.lock();
      //increase chances of race condition
      Thread.sleep(1);
      reputation += amount;
      //increase chances of race condition
      Thread.sleep(1);
    } catch(Exception e){
    } finally {
      colonyLock.unlock();
    }
    //If reputation is more than 500, set Rank as Empire
    if(reputation >= Integer.parseInt(COLONY_RANKS[4][1])){
      setRank(COLONY_RANKS[4][0]);
    //If reputation is more than 400, set Rank as City
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[3][1])){
      setRank(COLONY_RANKS[2][0]);
      //If reputation is more than 300, set Rank as Town
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[2][1])){
      setRank(COLONY_RANKS[3][0]);
      //If reputation is more than 200, set Rank as Village
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[1][1])){
      setRank(COLONY_RANKS[1][0]);
    }
  }

  //Used for future development of application when Colony can fight wars
  public void decReputation(int amount){
    try{
      colonyLock.lock();
      //increase chances of race condition
      Thread.sleep(1);
      reputation -= amount;
      //increase chances of race condition
      Thread.sleep(1);
    } catch(Exception e){
    } finally {
      colonyLock.unlock();
    }
  }

  //Prints Colony's information
  public void printInfo(){
    //Locks are used to prevent race condition when printing Colony's information
    colonyLock.lock();
    storage.lockStorage();
    try{
      System.out.println("*******************************************************\nReputation: " + reputation + "\t\t\tSize: " + size + "\nCurrentRank: " +
      currentRank + "\t\tCurrent Resources: " + storage.getResources() + "\n*******************************************************");
    } finally{
      storage.unlockStorage();
      colonyLock.unlock();
    }
  }
}
