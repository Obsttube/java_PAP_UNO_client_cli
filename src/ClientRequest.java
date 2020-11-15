import java.io.Serializable;

public class ClientRequest implements Serializable{
    private static final long serialVersionUID = 2L;

    public final RequestType requestType;

    public enum RequestType {
        LOGIN,
        REGISTER,
        GET_LOBBY_LIST,
        CREATE_LOBBY,
        JOIN_LOBBY,
        CLICK_START,
        CHOOSE_CARD,
        CHOOSE_COLOR
    }

    /* Request fields */
    public String playerName; // for logging in
    public String lobbyName; // for creating a new lobby
    public String lobbyId; // for joining a lobby
    public int choosenCardIndex; // for sending choosen card
    public Card.Color choosenColor; // for sending choosen color

    public ClientRequest(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}