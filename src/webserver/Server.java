package webserver;

import java.net.Socket;

/**
 *
 * @author David
 */
public class Server {
    
    public static void main(String[] args) {
        SimpleHTTPSServer https = new SimpleHTTPSServer();
        
        Socket socket = null;
        
        WebServer http = new WebServer(socket);
        
        boolean httpsServer = true;
        
        if(httpsServer){
            try {
                https.iniciar();
            } catch (Exception ex) {
                System.out.println("NO Inicio Servidor HTTPS");
            }
        }else{
            http.iniciar();
        }
        
        
            
        
    }
    
}
