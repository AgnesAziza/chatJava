import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ReaderClient extends Thread {
    BufferedReader inchan;
    Socket socket;
    ReaderClient(Socket socketClient)
    {
        try
        {
            inchan = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        socket = socketClient;
    }
    public void run() {
        try {
            while (true) {
                String command = inchan.readLine();
                if(command.equals("")) {
                    System.out.println("Fin de connexion.");
                    break;
                }
                System.out.println(command);
            }
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}