import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MySocket {
    
    private String ip;
    private int port;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader reader;

    // CONSTRUCTORS
    public MySocket(String ip){
        socket = null;
        reader = null;
        setIp(ip);
        setPort(9100);
    }

    public MySocket(String ip, int port){
        socket = null;
        reader = null;
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
                createReader();
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

    // Create reader
    private void createReader(){
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            this.reader = null;
        }
        
    }

    // Return data
    public String receiveData(){
        try{
            if(reader == null){
                createReader();
            }
            if (reader.ready()){
                return reader.readLine();
            }
            else{
                return null;
            }
        }catch(Exception e){
            return "";
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
