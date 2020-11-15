import java.io.Serializable;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Type { // TODO: ADD VALUE
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, // 4-COLOR
        SKIP, REVERSE, ADD_TWO, // 4-COLOR
        WILD, WILD_DRAW_FOUR // BLACK
    }
    public enum Color{
        RED, YELLOW, GREEN, BLUE,
        BLACK
    }
    public Type type;
    public Color color;
    public Card(Type type, Color color){
        this.type = type;
        this.color = color;
        //TODO: check if card is legal
    }
}
