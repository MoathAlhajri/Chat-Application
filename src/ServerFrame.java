import javax.swing.*;
import java.awt.*;

/**
 * Created by Moath Mohamed on 04-May-2019 At 15:29
 */

public class ServerFrame {

    private JFrame serverFrame;
    public JTextArea statusArea;
    private JLabel headerLabel;
    private JPanel centerPanel;
    private JScrollPane statusScrollPane;

    public ServerFrame() {
        this.initiateComponents();
    }

    public static void main(String[] args) {
        new ServerFrame();
    }

    private void initiateComponents() {

        serverFrame = new JFrame("Server");
        serverFrame.getContentPane().setLayout(new BorderLayout());

        headerLabel = new JLabel("Server Status:");
        headerLabel.setFont(headerLabel.getFont().deriveFont(15f));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        statusArea = new JTextArea(5,10);
        statusArea.setLineWrap(true);
        statusArea.setFont(statusArea.getFont().deriveFont(20f));
        statusArea.setEditable(false);
        statusScrollPane = new JScrollPane(statusArea);
        statusScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,1));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerPanel.add(headerLabel);
        centerPanel.add(statusScrollPane);

        serverFrame.add(centerPanel, "Center");

        serverFrame.pack();
        serverFrame.setDefaultCloseOperation(3);
        serverFrame.setVisible(true);
    }
}
