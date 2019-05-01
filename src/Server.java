import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    public ArrayList<ServerConnection> serverConnections = new ArrayList();
    public ArrayList username = new ArrayList();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {

        try {

            System.out.println("Server init...");
            serverSocket = new ServerSocket(3333);
            username.add("Online Users : ");

            while(true){

                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, this);
                serverConnection.start();
                serverConnections.add(serverConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(ServerConnection serverConnection) {

        for(int i = 0 ; i < serverConnections.size() ; i++)
            if(serverConnections.get(i).toString().equals(serverConnection.toString())) {
                serverConnections.remove(i);
                username.remove(i+1);
                break;
            }
    }
}