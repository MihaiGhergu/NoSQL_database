package tema2poo;
import java.util.*;

public class Nod {
    private int idx;
    private int maxCapacity;
    private ArrayList<Instanta> instances = new ArrayList<>();

    public int getIdx() {
        return idx;
    }
    
    public Nod(int idx, int maxCapacity) {
        this.idx = idx;
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Instanta> getInstances() {
        return instances;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
