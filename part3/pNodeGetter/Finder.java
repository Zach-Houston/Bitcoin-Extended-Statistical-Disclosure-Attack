import java.io.*;
import java.net.*;
import java.util.*;
public class Finder implements Runnable{
	int port;
	Socket s;
	Main m;

	public void run(){
		try{
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String userInput = new String();
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		out.println("PEERS");
		while( (userInput = reader.readLine()) != null){
			if((userInput.equals("ACK")) || (userInput.equals("OFFER"))){
				userInput = reader.readLine();
				continue;
			}
			String id = userInput.substring(0,63);
			String IP = userInput.substring(65, 78);
			String portnew = userInput.substring(78, 83);

			if((Integer.parseInt(portnew) > 15119) || (Integer.parseInt(portnew) < 15000)){
				m.addPrivate(id, Integer.parseInt(portnew), this.port);	
			}
			
			out.println("PEERS");
		}
		}
		catch(Exception e){ return; }
	}

	Finder(Socket sock, int p, Main man){
		port = p;
		s = sock;
		m = man;
	}
	
}
