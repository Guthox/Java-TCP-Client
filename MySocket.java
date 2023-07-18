import java.net.Socket;
import java.io.PrintWriter;

public class MySocket {
    
    private String ip;
    private int port;
    private Socket socket;
    private PrintWriter pw;

    // CONSTRUCTORS
    public MySocket(String ip){
        socket = null;
        setIp(ip);
        setPort(9100);
    }

    public MySocket(String ip, int port){
        socket = null;
        setIp(ip);
        setPort(port);
    }

    // Create a socket
    private boolean connect() {
        try{
            this.socket = new Socket(getIp(), getPort());
            return true;
        }catch (Exception e){
            return false;
        }
    } 

    public void disconnect() throws Exception{
        this.socket.close();
    }

    // Send data to socket
    public boolean sendData(String data, boolean keepConn){
        try{
            if (this.socket == null || this.socket.isClosed()){
                if (connect() == false){
                    return false;
                }
                this.pw = new PrintWriter(socket.getOutputStream(), true);
            }
            this.pw.println(data);
            if (keepConn == false){
                disconnect();
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    // GET AND SET

    public void setIp(String ip){
        this.ip = ip;
    }
    public void setPort(int port){
        this.port = port;
    }

    public String getIp(){
        return this.ip;
    }
    public int getPort(){
        return this.port;
    }
}
