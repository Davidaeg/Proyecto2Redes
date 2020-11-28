package webserver;

/**
 *
 * @author David
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HTTPServer implements Runnable{ 
	
	static final File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";

	static final int PORT = 8080;

	private Socket connect;
        
        private static boolean running;

	public HTTPServer(Socket c) {
            connect = c; 
            running = false;
	}
        
        public boolean getRunning(){
            return running;
        }
        
        public static void setRunning(boolean _runnig){
            running = _runnig;
        } 
	
	public static void iniciar() {
		try {
			ServerSocket serverConnect;

                        serverConnect = new ServerSocket(PORT);
                        
			System.out.println("Servidor HTTP Iniciado.\nEsperanco conexión en el puerto : " + PORT + "\n");
			
			while (true) {
				HTTPServer myServer = new HTTPServer(serverConnect.accept());
				Thread thread = new Thread(myServer);
				thread.start();
			}
			
		} catch (Exception e) {
			System.err.println("Error en la conexión : " + e.getMessage());
		} 
	}

	@Override
	public void run() {
		BufferedReader in = null; 
                PrintWriter out = null; 
                BufferedOutputStream dataOut = null;
		String fileRequested = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			String input = in.readLine();
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase(); 
			fileRequested = parse.nextToken().toLowerCase();
			
			if (!method.equals("GET")  &&  !method.equals("HEAD")) {
				
				File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
				int fileLength = (int) file.length();

				byte[] fileData = readFileData(file, fileLength);
					
				// Encabezado Http
				out.println("HTTP/1.1");
				out.println(); 
				out.flush(); 
				// archivo
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				
			} else {
				if (fileRequested.endsWith("/")) {
					fileRequested += DEFAULT_FILE;
				}
                                
                                loadFiles();//Refresca la lista en caso de que haya una nueva página
				
                                File file = new File(WEB_ROOT, fileRequested);
				int fileLength = (int) file.length();
				
				if (method.equals("GET")) { 
					byte[] fileData = readFileData(file, fileLength);
					
					// Encabezado Http
					out.println("HTTP/1.1");
					out.println(); 
					out.flush(); 
					
					dataOut.write(fileData, 0, fileLength);
					dataOut.flush();
				}				
				
			}
			
		} catch (FileNotFoundException fnfe) {
			try {
				fileNotFound(out, dataOut, fileRequested);
			} catch (IOException ioe) {
				System.out.println("Archivo no encontrado : " + ioe.getMessage());
			}
			
		} catch (IOException ioe) {
			System.err.println("Error de Servidor : " + ioe);
		} catch(java.lang.NullPointerException ex){
                    System.out.println("Esperando...");
                }finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				connect.close(); 
			} catch (Exception e) {
				System.err.println("Error cerrando el stream : " + e.getMessage());
			} 
		}
	}
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
                            fileIn.close();
		}
		return fileData;
	}
	
	
	private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = readFileData(file, fileLength);
		
		out.println("HTTP/1.1");
		out.println(); 
		out.flush(); 
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();	
	}
        
        private void loadFiles() throws IOException{
            
            IndexHtml html = new IndexHtml();
            
            File file2 = new File(WEB_ROOT, "files");
                                
            String[] files = file2.list();


            File file3 = new File(WEB_ROOT, DEFAULT_FILE);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file3)); 
            bw.write(html.head);
            for ( int i = 0; i < files.length  ; i ++ ) {

                bw.write("<a href=\"files/"+files[i]+"\" class=\"badge badge-light page\">"+files[i]+"</a></br>" );
            }
            bw.write(html.footer);
            bw.close();
        }
}