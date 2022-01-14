import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	
  public final static int SOCKET_PORT = 5056;  // Port boleh diganti
  public static String FILE_LOCATION;
  
 
  
  public static void main (String [] args ) throws IOException {
	  
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    FILE_LOCATION = null;
    System.out.println("Masukan Alamat File :");
    Scanner scanner = new Scanner(System.in);
    String FILE_LOCATION = scanner.nextLine();
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Menunggu...");
        try {
          sock = servsock.accept();
          System.out.println("Koneksi Di : " + sock);
          
          // Program mengirim file
          File myFile = new File (FILE_LOCATION);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_LOCATION + "(" + mybytearray.length + " bytes)"); // Menampilkan besaran file yang di ambil dari server
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
