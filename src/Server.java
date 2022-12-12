import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Connection> tabConversationsClients = new ArrayList<Connection>();

        public static void dispatchMessages(String message, Connection whichConnection) throws IOException {
            for (int i=0; i<tabConversationsClients.size(); i++) {
                if(!tabConversationsClients.get(i).equals(whichConnection)) {
                    tabConversationsClients.get(i).transmission(message);
                }
            }
        }

    public static void main(String[] args) {

        int port = 6767;
        try (ServerSocket serv = new ServerSocket(port)) {
            System.out.println("Server launched - listening port: " + port);

            while(true) {
                System.out.println("waiting for client...");
                Socket client = serv.accept();
                Connection conversationClient = new Connection(client);
                tabConversationsClients.add(conversationClient);
                conversationClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
