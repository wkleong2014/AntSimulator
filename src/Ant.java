//Parent class for All Ants
public class Ant{

  private String name;
  private int height;
  private int weight;
  private Colony colony;

  public Ant(String name, int height, int weight, Colony colony){
    this.name = name;
    this.height = height;
    this.weight = weight;
    this.colony = colony;
  }

  public String getName() {return name;}

  public int getHeight() {return height;}

  public int getWeight() {return weight;}

  public Colony getColony() {return colony;}

  public void setColony(Colony colony) {
    this.colony = colony;
  }

}
