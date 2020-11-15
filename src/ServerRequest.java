import java.io.Serializable;
import java.util.List;

public class ServerRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    public final RequestType requestType;

    public enum RequestType {
        LOGIN_SUCCESSFUL,
        LOGIN_UNSUCCESSFUL,
        LOBBY_LIST,
        LIST_OF_PLAYERS,
        YOUR_CARDS,
        YOUR_TURN
    }

    /* Request fields */
    public List<Lobby> lobbyList;
    public List<Player> players;
    public List<Card> cardsOnHand;
    public Card cardOnTable;

    public ServerRequest(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}