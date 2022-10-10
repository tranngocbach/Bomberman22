package src.uet.oop.bomberman.entities;

public class Query {
    Entity e;
    String type;

    public Query(String type, Entity e){
        this.type = type;
        this.e = e;
    }
    public String getType(){return type;}

    public Entity getE(){return e;}
}
