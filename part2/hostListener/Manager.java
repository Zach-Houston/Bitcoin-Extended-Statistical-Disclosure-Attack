import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;
public class Manager implements Runnable{
	String IP;
	ArrayList<Message> newSent;
	ArrayList<Message> newAck;
	ArrayList<String> nodeids;
	int totalcount;

	public synchronized void addSent(Timestamp time, Message m){
		boolean foundVal = false;
		totalcount++;
		for(Message a : newSent){
			if( a.msg.equals(m.msg)){
				foundVal = true;
			}
		}
		if(foundVal == true){
			return;
		}
		else{
			newSent.add(m);
		}
	}

	public synchronized void addAck(Timestamp time, Message m){
		boolean foundVal = false;
		totalcount++;
		if(this.newAck.size() == 16){ return; }
		for(Message a : newAck){
			if( a.msg.equals(m.msg)){
				foundVal = true;
			}
		}
		if(foundVal == true){ return; }
		else{ 
			newAck.add(m);}
	}

	public synchronized void printResults(){
		System.out.printf("S:[");
		int count = 0;
		for(Message m : newSent){
			if(count == 15){
				System.out.printf("%d]%n",m.port);
				break;
			}
			else{
				System.out.printf("%d, ", m.port);
			}
			count++;
		}
		count = 0;
		System.out.printf("R:[");
		for( Message m : newAck){
			if(count == 15){
				System.out.printf("%d]%n",m.port);
				break;
			}
			else{
				System.out.printf("%d, ", m.port);
			}
			count++;
		}
	}

	public void run(){
		while( true ){
			if((newSent.size() == 16) && (newAck.size() == 16) && (totalcount >= 5760)){
				printResults();
				newSent = new ArrayList<Message>();
				newAck = new ArrayList<Message>();
				totalcount = 0;
			}
			try{
				Thread.sleep(2000);
				System.err.printf("SENT:%d, ACK:%d, and TOTAL:%d%n", newSent.size(),newAck.size(), totalcount);
			}
			catch(Exception e){ System.out.printf("run error%n");return; }
		}
	}

	Manager(){
		newAck = new ArrayList<Message>();
		newSent = new ArrayList<Message>();
		IP = "REMOVED_FOR_PRIVACY";
		String temp = new String();
		nodeids = new ArrayList<String>();
		try{
			File f = new File("Nodes_data");
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()){
				if(sc.hasNext()){ temp = sc.next(); sc.next(); }
				else{ break; }
				nodeids.add(temp);
			}
		}
		catch(Exception e){ e.printStackTrace();return; }

		try{
			for(int i = 15000; i < 15120; i++){

				(new Thread(new Listener(new Socket(IP, i), i, this, nodeids.get(i-15000)))).start();
			}
		}
		catch(Exception e){ e.printStackTrace();System.out.printf("error%n");return; }
		(new Thread(this)).start();
	}

	public static void main(String args[]){
		new Manager();
	}
}
