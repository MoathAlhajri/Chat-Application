import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private ServerFrame serverFrame;
    public ArrayList<ServerConnection> serverConnections = new ArrayList();
    public ArrayList username = new ArrayList();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {

        serverFrame = new ServerFrame();

        try {

            serverFrame.statusArea.append("Server init... \n");

            serverSocket = new ServerSocket(3333);
            username.add("Online Users : ");
            serverFrame.statusArea.append("Server start \n");

            while(true){

                serverFrame.statusArea.append("Server waiting... \n");

                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, this);
                serverConnection.start();
                serverConnections.add(serverConnection);

                serverFrame.statusArea.append("User connected \n");
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
                serverFrame.statusArea.append("User logoff \n");
                serverFrame.statusArea.append("Server waiting... \n");
                break;
            }
    }
}