import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<Card> cards = new ArrayList<>();
    public String name;
    public Player(String name) {
        this.name = name;
    }
}
