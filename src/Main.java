import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 25566);
            
            Thread.sleep(100); // TODO

            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream objectInputStream;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream); // TODO should be created before objectInputStream at both sides https://stackoverflow.com/a/60361496
            ClientRequest clientRequest;
            ServerRequest serverRequest;

            clientRequest = new ClientRequest(ClientRequest.RequestType.LOGIN);
            clientRequest.playerName = args[0];
            objectOutputStream.writeObject(clientRequest);

            objectInputStream = new ObjectInputStream(bufferedInputStream);
            serverRequest = (ServerRequest)objectInputStream.readObject();
            System.out.println(serverRequest.requestType); // LOGIN_SUCCESSFUL

            clientRequest = new ClientRequest(ClientRequest.RequestType.GET_LOBBY_LIST);
            objectOutputStream.writeObject(clientRequest);

            serverRequest = (ServerRequest)objectInputStream.readObject();
            System.out.println(serverRequest.requestType); // LOBBY_LIST
            List<Lobby> lobbyList = serverRequest.lobbyList;
            for (Lobby lobby : lobbyList) {
                System.out.println(lobby.id + " " + lobby.name);
            }

            clientRequest = new ClientRequest(ClientRequest.RequestType.JOIN_LOBBY);
            clientRequest.lobbyId = "1";
            objectOutputStream.writeObject(clientRequest);

            InputStreamReader userInputStreamReader = new InputStreamReader(System.in);
            BufferedReader userInputReader = new BufferedReader(userInputStreamReader);

            while(true){
                if(bufferedInputStream.available() > 0){
                    serverRequest = (ServerRequest)objectInputStream.readObject();
                    System.out.println(serverRequest.requestType);
                    switch(serverRequest.requestType){
                        case YOUR_TURN: {
                            System.out.println("Select a card (-1 = get card from a pile and skip your move):");
                            String line = userInputReader.readLine();
                            //System.out.println(line);
                            int selectedCard = Integer.parseInt(line);
                            clientRequest = new ClientRequest(ClientRequest.RequestType.CHOOSE_CARD);
                            clientRequest.choosenCardIndex = selectedCard;
                            objectOutputStream.writeObject(clientRequest);
                            break;
                        }
                        case CHOOSE_COLOR: {
                            System.out.println("Choose a color for your wild card (0 = RED, 1 = YELLOW, 2 = GREEN, 3 = BLUE):");
                            String line = userInputReader.readLine();
                            //System.out.println(line);
                            int selectedCard = Integer.parseInt(line);
                            clientRequest = new ClientRequest(ClientRequest.RequestType.CHOOSE_COLOR);
                            Card.Color color = null;
                            switch(selectedCard){
                                case 0:
                                    color = Card.Color.RED;
                                    break;
                                case 1:
                                    color = Card.Color.YELLOW;
                                    break;
                                case 2:
                                    color = Card.Color.GREEN;
                                    break;
                                case 3:
                                    color = Card.Color.BLUE;
                                    break;
                                default:
                            }
                            clientRequest.choosenColor = color;
                            objectOutputStream.writeObject(clientRequest);
                            break;
                        }
                        case ILLEGAL_MOVE:
                            System.out.println("Illegal move! (It wasn't accepted)");
                            break;
                        case LIST_OF_PLAYERS:
                            List<Player> players2 = serverRequest.players;
                            System.out.print("{ ");
                            for (Player player : players2) {
                                System.out.print(player.name + " ");
                            }
                            System.out.println("}");
                            System.out.println("Write START to begin.");
                            break;
                        case YOUR_CARDS:
                            Card table = serverRequest.cardOnTable;
                            if(table == null)
                                System.out.print("Table: empty, hand: ");
                            else
                                System.out.print("Table: " + table.type + "-" + table.color + ", hand: ");
                            List<Card> cards = serverRequest.cardsOnHand;
                            System.out.print("{ ");
                            for (Card card : cards) {
                                System.out.print(card.type + "-" + card.color + " ");
                            }
                            System.out.println("}");
                            Card.Color currentWildColor = serverRequest.currentWildColor;
                            if(currentWildColor != null)
                                System.out.println("Current wild color: " + currentWildColor);
                            break;
                        default:
                            break;
                    }
                }
                if(userInputReader.ready()){
                    String line = userInputReader.readLine();
                    if(line.equalsIgnoreCase("START")){ // if user wrote START in console
                        clientRequest = new ClientRequest(ClientRequest.RequestType.CLICK_START);
                        objectOutputStream.writeObject(clientRequest);
                    }
                }
                Thread.sleep(100);
            }

        } catch (ClassNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (UnknownHostException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            // TODO
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }
}