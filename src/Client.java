public class Client {

    public String username;

    public ClientConnection clientConnection;

    public LoginFrame loginFrame;
    public ChatFrame chatFrame;

    public Client() {
        loginFrame = new LoginFrame(this);
    }

    public static void main(String[] args) {
        new Client();
    }

}