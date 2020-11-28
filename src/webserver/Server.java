package webserver;

import java.net.Socket;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author David
 */
public class Server {

    static GraphicsConfiguration gc;
    static JButton btnHTTPS;
    static JButton btnHTTP;
    static JButton btnStop;
    static HTTPSServer https;
    static HTTPServer http;
    static boolean httpsServer;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        JFrame frame = new JFrame(gc);
        frame.setTitle("Servidor HTTP/HTTPS");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel lblTilte = new JLabel("¿Que tipo de servidor quiere iniciar?");
        lblTilte.setBounds(90, 50, 220, 20);

        btnHTTPS = new JButton("HTTPS");
        btnHTTPS.setBounds(100, 100, 80, 20);

        btnHTTP = new JButton("HTTP");
        btnHTTP.setBounds(200, 100, 80, 20);

        btnStop = new JButton("Detener");
        btnStop.setBounds(150, 150, 80, 20);
        btnStop.setEnabled(false);
        
        Socket socket = null;
        http = new HTTPServer(socket);
        
        https = new HTTPSServer();

        actionListeners();

        
        frame.add(lblTilte);
        frame.add(btnHTTP);
        frame.add(btnHTTPS);
        frame.add(btnStop);

        frame.setVisible(true);
    }

    public static void actionListeners() {
        btnHTTPS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStop.setEnabled(true);
                btnHTTP.setEnabled(false);
                btnHTTPS.setEnabled(false);
                
                try {
                    https.iniciar();
                } catch (Exception ex) {
                    System.out.println("NO Inicio Servidor HTTPS");
                }
            }
        });

        btnHTTP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStop.setEnabled(true);
                btnHTTP.setEnabled(false);
                btnHTTPS.setEnabled(false);
                
                
                try {
                    Thread.sleep(2000);
                    http.setRunning(true);
                    http.iniciar();
                } catch (Exception ex) {
                    System.out.println("NO Inicio Servidor HTTP");
                }
            }
        });
        
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStop.setEnabled(false);
                btnHTTP.setEnabled(true);
                btnHTTPS.setEnabled(true);
                
              
                try {
                    https.stop();
                    http.setRunning(false);
                    
                } catch (Exception ex) {
                    System.out.println("NO Inicio Servidor HTTP");
                }
            }
        });

    }
}
