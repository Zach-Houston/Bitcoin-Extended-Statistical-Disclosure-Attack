import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;
public class Manager implements Runnable{
	String IP;
	Map sent;
	Map ack;
	ArrayList<String> nodeids;
	ArrayList<Private> psent;
	ArrayList<Private> pack;
	ArrayList<Message> newSent;
	ArrayList<Message> newtry;
	int totalcount;
	ArrayList<Integer> privatedict;
	ArrayList<ArrayList<Integer> > publicdict;

	public synchronized void addSent(Timestamp time, Message m){
		Collection values = sent.values();
		boolean foundVal = false;
		totalcount++;
		for(Message a : newSent){
			if( a.msg.equals(m.msg) ){
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

	public synchronized void addPSent(String m, int p){
		boolean found = false;
		totalcount++;
		for(Private pr : psent){
			if(m.equals(pr.msg)){
				if(pr.ports.size() < 8){
					pr.ports.add(p);
					found = true;
				}
				else{
					found = true;
				}
			}
		}
		if((found == false) && (psent.size() < 16)){
			psent.add(new Private(m, p));
		}
	}

	public synchronized void addPAck(String m, int p, Timestamp t, Message msg){
		boolean found = false;
		totalcount++;

	Collection values = ack.values();
	boolean foundval = false;
	boolean oldack = true;
	if(newtry.size() == 16){ oldack = false; }
	if(oldack == true){
		for(Message a : newtry){
			if(a.msg.equals(msg.msg)){
				foundval = true;
			}
		}
		if(foundval == true){ return; }
		else{ newtry.add(msg); return;}
	}

		for(Message a : newtry){
			if( a.msg.equals(msg.msg) ){
				return;
			}
		}



		for(Private pr : pack){
			if(m.equals(pr.msg)){
				if(pr.ports.size() < 8){
					pr.ports.add(p);
					found = true;
				}
				else{
					found = true;
				}
			}
		}
		if((found == false) && (pack.size() < 16)){
			pack.add(new Private(m, p));
		}
	}

	public synchronized boolean addAck(Timestamp time, Message m){
		Collection values = ack.values();
		boolean foundVal = false;
		if(this.ack.size() == 16){ return true; }
		for(Object a : values){
			if( ((Message) a).msg.equals(m.msg) ){
				foundVal = true;
			}
		}
		if(foundVal == true){ return false; }
		else{ ack.put(time, m); }
		return false;
	}

	public synchronized void printResults(){
		ArrayList<Integer> finalsent = new ArrayList<Integer>();
		ArrayList<Integer> finalack = new ArrayList<Integer>();

		Collection values = ack.values();
		for(Object a : values){
			System.out.printf("%d%n", ((Message) a).port);
		}


		for(Private a : psent){
			Collections.sort(a.ports);
			for(ArrayList<Integer> al : publicdict){
				if(a.ports.equals(al)){
					int index = publicdict.indexOf(al);
					finalsent.add(privatedict.get(index));
					break;
				}
			}
		}

		for(Private a : pack){
			Collections.sort(a.ports);
			for(ArrayList<Integer> al : publicdict){
				if(a.ports.equals(al)){
					int index = publicdict.indexOf(al);
					finalack.add(privatedict.get(index));
					break;
				}
			}
		}
		
		
		
		System.out.printf("S:[");
		int count = 0;
		for(Integer a : finalsent){
			if(count == 15){
				System.out.printf("%d]%n",a);
				break;
			}
			else{
				System.out.printf("%d, ", a);
			}
			count++;
		}
		count = 0;
		System.out.printf("R:[");
		for( Integer a : finalack ){
			if(count == 15){
				System.out.printf("%d]%n", a);
				break;
			}
			else{
				System.out.printf("%d, ", a);
			}
			count++;
		}
	}

	public void run(){
		while( true ){
			if((psent.size() == 16) && (pack.size() == 16) && (totalcount >=1920*4)){
				printResults();
				sent = new HashMap();
				ack = new HashMap();
				psent = new ArrayList<Private>();
				pack = new ArrayList<Private>();
				newSent = new ArrayList<Message>();
				newtry = new ArrayList<Message>();
				totalcount = 0;
			}
			try{
				Thread.sleep(2000);
				System.err.printf("SENT:%d, ACK:%d, and TOTAL:%d%n", psent.size(), pack.size(), totalcount);
			}
			catch(Exception e){ System.out.printf("run error%n");return; }
		}
	}

	Manager(){
		sent = new HashMap();
		ack = new HashMap();
		ArrayList<Integer> tempal = new ArrayList<Integer>();
		publicdict = new ArrayList<ArrayList<Integer> >();
		privatedict = new ArrayList<Integer>();
		psent = new ArrayList<Private>();
		pack = new ArrayList<Private>();
		newSent = new ArrayList<Message>();
		newtry = new ArrayList<Message>();
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

			File privatefile = new File("private.txt");
			Scanner sc2 = new Scanner(privatefile);
			while(sc2.hasNextLine()){
				ArrayList<Integer> publics = new ArrayList<Integer>();
				int privateport = Integer.parseInt(sc2.next());
				temp =sc2.next();
				sc2.nextLine();
				for(int i = 0; i < 8; i++){
					int publicport = Integer.parseInt(sc2.next());
					publics.add(publicport);
				}
				privatedict.add(privateport);
				Collections.sort(publics);
				publicdict.add(publics);
				sc2.nextLine();
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
