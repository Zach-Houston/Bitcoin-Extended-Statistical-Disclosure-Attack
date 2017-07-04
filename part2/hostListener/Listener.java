import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;
public class Listener implements Runnable{
	Socket sock;
	int p;
	Manager man;
	String id;

	public void run(){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String userInput = new String();
			while( (userInput = reader.readLine()) != null ){
				if(userInput.equals("OFFER")){
					Timestamp t = new Timestamp(System.currentTimeMillis());
					String msg = reader.readLine();
					String checker = msg.substring(0,1);
					if(checker.equals("0")){
						continue;
					}
					man.addSent(t, new Message(id, p, msg));
				}
				if(userInput.equals("ACK")){
					Timestamp t = new Timestamp(System.currentTimeMillis());
					String msg = reader.readLine();
					man.addAck(t, new Message(id, p, msg));
				}
			}
		}
		catch(Exception e){ e.printStackTrace();return; }
	}

	Listener(Socket s, int port, Manager m, String i){
		sock = s;
		p = port;
		man = m;
		id = i;
	}
}
