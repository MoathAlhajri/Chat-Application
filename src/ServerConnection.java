import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread {

    private Server server;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean shouldRun = true;
    private String serverConnectionNumber;

    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
        this.serverConnectionNumber = "" + server.serverConnections.size();
    }

    private void sendToAllClient(String text){

        for(int i = 0; i < server.serverConnections.size(); i++) {
            server.serverConnections.get(i).sendToClient(text);
        }
    }

    private void sendToClient(String text) {

        try{
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {

        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while(shouldRun){

                while(dataInputStream.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String input = dataInputStream.readUTF();

                if(input.contains("New user : ")){
                    input = input.split(":")[1].trim();
                    server.username.add(input);
                    String onlineUser = "";
                    for(int i = 0; i < server.username.size(); i++)
                        onlineUser += server.username.get(i) + "\n";

                    sendToAllClient(onlineUser);
                }
                else if(input.equals("logoff"))
                    shouldRun = false;
                else
                    sendToAllClient(input);
            }
            server.remove(this);

            String onlineUser = "";
            for(int i = 0; i < server.username.size(); i++)
                onlineUser += server.username.get(i) + "\n";

            sendToAllClient(onlineUser);

            this.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void close(){
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String toString() {
        return serverConnectionNumber;
    }
}