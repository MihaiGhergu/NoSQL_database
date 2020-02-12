package tema2poo;
import java.util.*;

public class Entitate {
    private String name;
    private int rFactor;
    private int noAttributes;
    private Atribut primaryKey;
    private ArrayList<Atribut> attributes = new ArrayList<>();

    public Entitate(String name, int rFactor, int noAttributes) {
        this.name = name;
        this.rFactor = rFactor;
        this.noAttributes = noAttributes;
    }
    
    public String getName() {
        return name;
    }
    
    public int getrFactor() {
        return rFactor;
    }    

    public int getNoAttributes() {
        return noAttributes;
    }

    public ArrayList<Atribut> getAttributes() {
        return attributes;
    }

    public Atribut getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Atribut primaryKey) {
        this.primaryKey = primaryKey;
    }
}
