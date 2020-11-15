import java.io.Serializable;

public class Lobby implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    public String name;
    public Lobby(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
