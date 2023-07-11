import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;;

public class Gui extends JFrame implements ActionListener{
    
    private JLabel lbIp;
    private JLabel lbPort;
    private JTextField tfIp;
    private JTextField tfPort;

    private JTextArea taData;
    private JScrollPane scroll;
    private JButton btSend;

    public Gui(){
        super("TCP Client");

        initComponents();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel north = new JPanel(new FlowLayout());
        north.add(lbIp);
        north.add(tfIp);
        north.add(lbPort);
        north.add(tfPort);
        north.add(btSend);

        container.add(north, BorderLayout.NORTH);
        
        JPanel center = new JPanel(new FlowLayout());
        center.add(scroll);
        container.add(center, BorderLayout.CENTER);

        setVisible(true);
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btSend){
            try{
                MySocket socket = new MySocket(tfIp.getText(), Integer.parseInt(tfPort.getText()));
                socket.sendData(taData.getText());
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
        tfIp = new JTextField("", 15);
        tfPort = new JTextField("", 6);

        taData = new JTextArea(30, 40);
        scroll = new JScrollPane(taData);

        btSend = new JButton("Send");
        btSend.addActionListener(this);
    }

}
