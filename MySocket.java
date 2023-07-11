import java.net.Socket;
import java.io.PrintWriter;
import java.io.OutputStream;

public class MySocket {
    
    private String ip;
    private int port;

    // CONSTRUCTORS
    public MySocket(String ip){
        setIp(ip);
        setPort(9100);
    }

    public MySocket(String ip, int port){
        setIp(ip);
        setPort(port);
    }

    // Create a socket
    private Socket connect() throws Exception{
        return new Socket(getIp(), getPort());
    } 

    // Send data to socket
    public boolean sendData(String data){
        try(Socket socket = connect()){
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(data);
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
