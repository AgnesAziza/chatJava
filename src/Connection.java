import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class Connection extends Thread {
    private Socket socket;
    private DataOutputStream outputStream;
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void interrupt() {
        super.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transmission(String message) throws IOException {
        outputStream.writeBytes("Copain : " + message + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return socket.equals(that.socket) && outputStream.equals(that.outputStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, outputStream);
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String command = bufferedReader.readLine();
                if ( command.equals("")) {
                    System.out.println("End");
                    socket.close();
                    break;
                }
                Server.dispatchMessages(command, this);
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
