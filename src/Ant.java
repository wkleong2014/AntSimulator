//Parent class for All Ants
public class Ant{

  private String name;
  private int height;
  private int weight;

  public Ant(String name, int height, int weight){
    this.name = name;
    this.height = height;
    this.weight = weight;
  }

  public String getName() {return name;}

  public int getHeight() {return height;}

  public int getWeight() {return weight;}

}
