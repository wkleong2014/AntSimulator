//Parent class for All Ants
public class Ant{
  private String name;
  private Colony colony;  //The colony which the Ant belongs to

  public Ant(String name, Colony colony){
    this.name = name;
    this.colony = colony;
  }

  //Getter Methods
  public String getName() {return name;}

  public Colony getColony() {return colony;}

  //Setter Methods
  public void setName(String name){
    this.name = name;
  }

  public void setColony(Colony colony) {
    this.colony = colony;
  }
}
