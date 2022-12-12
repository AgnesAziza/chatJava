import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Redefinable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static ReaderClient readerClient = null;
    public static void main(String[] args) {

        String address = "localhost";
        int port = 6767;

        try (Socket sock = new Socket(address, port)) {

            DataOutputStream outchan = new DataOutputStream(sock.getOutputStream());
            readerClient = new ReaderClient(sock);
            readerClient.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                outchan.writeBytes(message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}