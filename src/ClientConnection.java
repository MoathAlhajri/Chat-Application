import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection extends Thread {

    private Socket socket;
    private Client client;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public boolean shouldRun = true;
    private String logOff = " has logOff";

    public ClientConnection(Socket socket, Client client){
        this.socket = socket;
        this.client = client;
    }

    public void sendStringToServer(String text){

        try {
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void run() {

        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

            sendStringToServer("New user : " + client.username);

            while (shouldRun) {
                while (dataInputStream.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String replay = dataInputStream.readUTF();
                if(replay.contains("Online Users : ")) {
                    client.chatFrame.getOnlineClientsArea().setText(replay);
                }
                else
                    client.chatFrame.getChatArea().append(replay + "\n");
            }

            close();
        }
        catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void close(){
        try {
            sendStringToServer(client.username + logOff);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}