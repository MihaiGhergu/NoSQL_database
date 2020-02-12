package tema2poo;
import java.util.*;

public class DataBase {
  private String dbName;
  private int noNodes;
  private int maxCapacity;
  private ArrayList<Nod> nodes = new ArrayList<>();
  private ArrayList<Entitate> entities = new ArrayList<>();
/*Construirea bazei de date cu tot cu noduri*/
    public DataBase(String dbName, int noNodes, int maxCapacity) {
        this.dbName = dbName;
        this.noNodes = noNodes;
        this.maxCapacity = maxCapacity;     
        int i = 0;
        while(i < noNodes){
            nodes.add(new Nod(i+1,maxCapacity));
            i++;
        }
    }
   
    public ArrayList<Entitate> getEntities() {
        return entities;
    }
    
    public ArrayList<Nod> getNodes() {
        return nodes;
    } 

    public int getNoNodes() {
        return noNodes;
    }

    public void setNoNodes(int noNodes) {
        this.noNodes = noNodes;
    }

    public String getDbName() {
        return dbName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    } 
  
}

