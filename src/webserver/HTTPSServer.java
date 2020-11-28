package webserver;

import java.io.*;
import java.net.InetSocketAddress;
import java.lang.*;
import com.sun.net.httpserver.HttpsServer;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import com.sun.net.httpserver.*;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import javax.net.ssl.SSLContext;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.nio.file.Files;


public class HTTPSServer {

    static InetSocketAddress address = new InetSocketAddress(8080);

    static HttpsServer httpsServer;
    public static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
           // System.out.println(t.getRequestURI());
          
            String html = t.getRequestURI().toString();
            
            if(html.equals("/")){
                loadFiles();
                html = "/index.html";
            }

            File file = new File(html.substring(1));
            t.sendResponseHeaders(200, file.length());
            try (OutputStream os = t.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

    public static void stop(){
        httpsServer.stop(0);
    }

    public static void iniciar() throws Exception {

        try {
            // setup the socket address
            httpsServer = HttpsServer.create(address, 0);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // Obtener el certificado
            char[] password = "redes123".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("C:/Users/David/Desktop/ServerHTTPS/proyectoRedes.jks");
            ks.load(fis, password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // Inicializar el contexto SSL
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        //SSL con parametros
                        SSLParameters sslParameters = context.getSupportedSSLParameters();
                        params.setSSLParameters(sslParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
       
            httpsServer.createContext("/", new MyHandler());
            httpsServer.setExecutor(null); 
            System.out.println("Servidor HTTPS Iniciado.\nEsperanco conexión en el puerto : 8080 \n");
            httpsServer.start();

        } catch (Exception exception) {
            System.out.println("Error al crear servidor HTTPS en el puerto 8080 del localhost");
            exception.printStackTrace();

        }
    }
    
    private static void loadFiles() throws IOException{
            
            IndexHtml html = new IndexHtml();
            
            File file2 = new File(".", "files");
                                
            String[] files = file2.list();


            File file3 = new File(".", "index.html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file3)); 
            bw.write(html.head);
            for ( int i = 0; i < files.length  ; i ++ ) {

                bw.write("<a href=\"files/"+files[i]+"\" class=\"badge badge-light page\">"+files[i]+"</a></br>" );
            }
            bw.write(html.footer);
            bw.close();
        }

}