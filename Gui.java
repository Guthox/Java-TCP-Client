import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.FlowLayout;

public class Gui extends JFrame implements ActionListener{
    
    private JLabel lbIp;
    private JLabel lbPort;
    private JTextField tfIp;
    private JTextField tfPort;

    private JTextArea taServerData;
    private JScrollPane scrollServer;

    private JCheckBox cbKeepConn;

    private JTextArea taData;
    private JScrollPane scroll;
    private JButton btSend;
    private JButton btLimpar;

    private MySocket socket;

    public Gui(){
        super("TCP Client");

        this.socket = null;

        initComponents();
        Container container = getContentPane();
        container.setLayout(null);

        setLayout(null);

        JPanel north = new JPanel(new FlowLayout());
        north.add(lbIp);
        north.add(tfIp);
        north.add(lbPort);
        north.add(tfPort);
        north.add(btSend);
        north.add(cbKeepConn);

        north.setBounds(0, 10, 480, 40);
        add(north);
        
        JPanel center = new JPanel(new FlowLayout());
        center.add(scroll);
    
        center.setBounds(0, 50, 480, 250);
        add(center);

        JPanel serverSide = new JPanel(new FlowLayout());
        serverSide.add(scrollServer);

        serverSide.setBounds(0, 350, 480, 250);
        add(serverSide);

        btLimpar.setBounds(385, 310, 75, 25);
        add(btLimpar);

        setVisible(true);
        setSize(500,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        receive.start();

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
                //JOptionPane.showMessageDialog(this, "Success");
            }
            catch(Exception err){
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
        if (e.getSource() == btLimpar){
            clearServerData();
        }
    }

    private void initComponents(){
        lbIp = new JLabel("IP: ");
        lbPort = new JLabel("Port: ");
        tfIp = new JTextField("", 10);
        tfPort = new JTextField("", 6);

        taData = new JTextArea(15, 40);
        scroll = new JScrollPane(taData);

        btSend = new JButton("Send");
        btSend.addActionListener(this);

        btLimpar = new JButton("Clear");
        btLimpar.addActionListener(this);

        taServerData = new JTextArea(15, 40);
        taServerData.setEditable(false);
        DefaultCaret cartet = (DefaultCaret)taServerData.getCaret();
        cartet.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollServer = new JScrollPane(taServerData);

        cbKeepConn = new JCheckBox("Keep Connection");

    }

    // Thread
    private Thread receive = new Thread(() -> {
        String data;
        while (true){
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                ;
            }
            if (socket != null){
                if ((data = socket.receiveData()) != null){
                    taServerData.setText(taServerData.getText() + "\n" + data);
                }
            }
        }
    });

    // Clear JTextArea of Server Data
    private void clearServerData(){
        taServerData.setText("");
    }

}


