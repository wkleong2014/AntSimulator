//Colony
public class Colony{
  private String currentRank;
  private int reputation;
  private int size;
  private final String[][] COLONY_RANKS = new String[][]{{"Hut", "100"}, {"Village", "200"}, {"Town", "300"}, {"City", "400"}, {"Empire", "500"}};

  public Colony(){
    reputation = 10;
    size = 1;
    currentRank = COLONY_RANKS[0][0];
  }

  public void upRank(){
    if(reputation >= Integer.parseInt(COLONY_RANKS[4][1])){ //500
      currentRank = COLONY_RANKS[4][0];
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[3][1])){ //400
      currentRank = COLONY_RANKS[3][0];
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[2][1])){ //300
      currentRank = COLONY_RANKS[2][0];
    } else if(reputation >= Integer.parseInt(COLONY_RANKS[1][1])){ //200
      currentRank = COLONY_RANKS[1][0];
    }
  }

  public void incReputation(int amount){
    reputation += amount;
  }

  public void decReputation(int amount){
    reputation -= amount;
  }

  public void printStuff(){
    System.out.println("Reputation: " + reputation + " | " + "Size: " + size + " | " + "currentRank: " + currentRank);
  }

}
