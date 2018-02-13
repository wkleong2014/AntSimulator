//Colony
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Colony{
  private String currentRank;
  private int reputation;
  private int size;
  private final String[][] COLONY_RANKS = new String[][]{{"Hut", "100"}, {"Village", "200"}, {"Town", "300"}, {"City", "400"}, {"Empire", "500"}};
  private Storage storage;

  public Colony(){
    reputation = 10;
    size = 1;
    currentRank = COLONY_RANKS[0][0];
    storage = new Storage();
  }

  public void upRank(){
    if(reputation >= Integer.parseInt(COLONY_RANKS[4][1])){ //500
      currentRank = COLONY_RANKS[4][0];
          System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank + " | Current Resources: " + storage.getResources());

    } else if(reputation >= Integer.parseInt(COLONY_RANKS[3][1])){ //400
      currentRank = COLONY_RANKS[3][0];
          System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank + " | Current Resources: " + storage.getResources());

    } else if(reputation >= Integer.parseInt(COLONY_RANKS[2][1])){ //300
      currentRank = COLONY_RANKS[2][0];
          System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank + " | Current Resources: " + storage.getResources());

    } else if(reputation >= Integer.parseInt(COLONY_RANKS[1][1])){ //200
      currentRank = COLONY_RANKS[1][0];
          System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank + " | Current Resources: " + storage.getResources());
    }
  }

  public void incReputation(int amount){
    reputation += amount;
  }

  public void decReputation(int amount){
    reputation -= amount;
  }

  public void setSize(int amount){
    size += amount;
  }

  public Storage getStorage(){return storage;}

  public void printStuff(){
    System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank + " | Current Resources: " + storage.getResources());
  }

}
