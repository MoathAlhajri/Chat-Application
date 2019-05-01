import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame {

    private String fullname;

    private JFrame chatFrame;
    private JTextArea onlineClientsArea, chatArea;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem clear, logOff;
    private JScrollPane scrollPaneChat, scrollPaneClients;
    private JButton sendButton;
    private JTextField messageField;
    private JPanel southPanel;

    private ClientConnection clientConnection;
    private Client client;

    public ChatFrame(Client client, ClientConnection clientConnection){
        this.client = client;
        this.clientConnection = clientConnection;
        this.fullname = client.username;
        this.initiateComponents();
    }

    private void initiateComponents(){

        chatFrame = new JFrame(this.fullname);
        chatFrame.getContentPane().setLayout(new BorderLayout());

        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        clear = new JMenuItem("Clear");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chatArea.setText("");
            }
        });
        file.add(clear);
        logOff = new JMenuItem("Log off");
        logOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientConnection.sendStringToServer("logoff");
                System.exit(0);
            }
        });
        file.add(logOff);

        onlineClientsArea = new JTextArea(20,10);
        onlineClientsArea.setLineWrap(true);
        onlineClientsArea.setFont(onlineClientsArea.getFont().deriveFont(20f));
        onlineClientsArea.setEditable(false);
        onlineClientsArea.setText("Online User : ");
        onlineClientsArea.setBackground(Color.orange);
        scrollPaneClients = new JScrollPane(onlineClientsArea);

        chatArea = new JTextArea(20,20);
        chatArea.setLineWrap(true);
        chatArea.setFont(chatArea.getFont().deriveFont(20f));
        chatArea.setEditable(false);
        scrollPaneChat = new JScrollPane(chatArea);

        messageField = new JTextField(20);
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(messageField.getText().equals("")){

                }
                else {
                    clientConnection.sendStringToServer(client.username + " : " + messageField.getText());
                    messageField.setText("");
                }
            }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(messageField.getText().equals("")){

                }
                else {
                    clientConnection.sendStringToServer(client.username + " : " + messageField.getText());
                    messageField.setText("");
                }
            }
        });

        southPanel = new JPanel();
        southPanel.add(messageField);
        southPanel.add(sendButton);

        chatFrame.setJMenuBar(menuBar);
        chatFrame.getContentPane().add(scrollPaneChat,"Center");
        chatFrame.getContentPane().add(scrollPaneClients, "East");
        chatFrame.add(southPanel, "South");

        chatFrame.setDefaultCloseOperation(3);
        chatFrame.pack();
        chatFrame.setVisible(true);
    }

    public JTextArea getOnlineClientsArea() {
        return onlineClientsArea;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public JFrame getChatFrame() {
        return chatFrame;
    }
}
