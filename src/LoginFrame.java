import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginFrame {

    private Client client;

    private JFrame loginFrame;
    private JLabel usernameLabel, passwordLabel;
    private JPanel centerPanel, southPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    public LoginFrame(Client client) {
        this.client = client;
        this.initiateComponents();
    }

    private void initiateComponents() {

        loginFrame = new JFrame("Log in");
        loginFrame.getContentPane().setLayout(new BorderLayout());

        usernameLabel = new JLabel("Username : ");
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(15f));
        usernameField = new JTextField(15);

        passwordLabel = new JLabel("Password : ");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(15f));
        passwordField = new JPasswordField(15);
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,4));

        centerPanel.add(new JLabel());
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);

        for(int i = 0 ; i < 6 ; i++)
            centerPanel.add(new JLabel());

        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());

        loginButton = new JButton("Log in");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(0,5));

        southPanel.add(new JLabel());
        southPanel.add(loginButton);
        southPanel.add(new JLabel());
        southPanel.add(cancelButton);

        for(int i = 0 ; i < 7 ; i++)
            southPanel.add(new JLabel());

        loginFrame.add(new JPanel(), "North");
        loginFrame.add(centerPanel, "Center");
        loginFrame.add(southPanel, "South");

        loginFrame.pack();
        loginFrame.setDefaultCloseOperation(3);
        loginFrame.setVisible(true);
    }

    private void startConnection(String fullname) {

        try {
            Socket socket = new Socket("localhost", 3333);
            client.clientConnection = new ClientConnection(socket, client);

            client.username = fullname;
            client.loginFrame = null;
            loginFrame.dispose();
            client.chatFrame = new ChatFrame(client, client.clientConnection);

            client.clientConnection.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error Connection" ,0);
        }
    }

    private void login(){

        String username = usernameField.getText();
        char array[] = passwordField.getPassword();

        String password = "";

        for(int i = 0; i < array.length; i++)
            password += (int) array[i];

        if(username.equals("") | password.equals(""))
            JOptionPane.showMessageDialog(null, "Please enter all of your information", "Error", 0);
        else {
            this.startConnection(username);
        }

    }

}