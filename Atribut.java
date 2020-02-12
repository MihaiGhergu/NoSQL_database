package tema2poo;

public class Atribut {
    
    private String type;
    private String name;

    public Atribut() {
    }

    public Atribut(String name, String type) {
        this.name = name;
        this.type = type;
    }   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }    
}
