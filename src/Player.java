import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 2L;
    public String name;
    public int numberOfCards = 0;
    public Player(String name) {
        this.name = name;
    }
    public Player(String name, int numberOfCards) {
        this(name);
        this.numberOfCards = numberOfCards;
    }
}
