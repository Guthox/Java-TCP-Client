import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class Gui extends JFrame implements ActionListener{
    
    private JLabel lbIp;
    private JLabel lbPort;
    private JTextField tfIp;
    private JTextField tfPort;

    private JCheckBox cbKeepConn;

    private JTextArea taData;
    private JScrollPane scroll;
    private JButton btSend;

    private MySocket socket;

    public Gui(){
        super("TCP Client");

        this.socket = null;

        initComponents();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel north = new JPanel(new FlowLayout());
        north.add(lbIp);
        north.add(tfIp);
        north.add(lbPort);
        north.add(tfPort);
        north.add(btSend);
        north.add(cbKeepConn);

        container.add(north, BorderLayout.NORTH);
        
        JPanel center = new JPanel(new FlowLayout());
        center.add(scroll);
        container.add(center, BorderLayout.CENTER);

        setVisible(true);
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    // Button action
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btSend){
            try{
                // If socket don't exist or ip has changed or port has change
                if (this.socket == null ||
                this.socket.getIp().equals(tfIp.getText()) == false || 
                this.socket.getPort() != Integer.parseInt(tfPort.getText()))
                {
                    // disconnect if there is a connection
                    if (this.socket != null){
                        this.socket.disconnect();
                    }
                    // create a new connection
                    this.socket = new MySocket(tfIp.getText(), Integer.parseInt(tfPort.getText()));
                }
                // Send data
                this.socket.sendData(taData.getText(), cbKeepConn.isSelected());
                JOptionPane.showMessageDialog(this, "Success");
                
            }
            catch(Exception err){
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }

    public void initComponents(){
        lbIp = new JLabel("IP: ");
        lbPort = new JLabel("Port: ");
        tfIp = new JTextField("", 10);
        tfPort = new JTextField("", 6);

        taData = new JTextArea(30, 40);
        scroll = new JScrollPane(taData);

        btSend = new JButton("Send");
        btSend.addActionListener(this);

        cbKeepConn = new JCheckBox("Keep Connection");

    }

}
