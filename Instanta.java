package tema2poo;
import java.util.ArrayList;

public class Instanta{
    
    private String name;
    private ArrayList<Object> objects = new ArrayList<>();
    private long timestamp;
    
    public Instanta(){ setTimestamp(System.nanoTime());}
    
    public Instanta(String name) {
        this.name = name;
        setTimestamp(System.nanoTime());
    }

    public String getName() {
        return name;
    }
      
    public ArrayList<Object> getObjects() {
        return objects;
    }
    
    public void setI(Object o,int k){
      objects.add(k, o);
    }
    
    public void remove(int k){
      objects.remove(k);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    } 
}
