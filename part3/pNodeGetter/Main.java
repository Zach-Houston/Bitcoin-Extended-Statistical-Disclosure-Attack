import java.util.*;
import java.net.*;
import java.io.*;
public class Main implements Runnable{
	ArrayList<Private> p;

	public synchronized void addPrivate(String id, int port, int peerport){
		for( Private pr : p ){
			if((pr.id.equals(id)) && (pr.port == port)){
				if(!pr.peers.contains(peerport)){
					pr.peers.add(peerport);
				}
				return;
			}
		}
		p.add(new Private(port, id));
	}
	public synchronized void done(){
		for(Private pr : p){
			System.out.printf("%d %s%n", pr.port, pr.id);
			for(int a : pr.peers){
				System.out.printf("%d ", a);
			}
			System.out.printf("%n");
		}
	}

	public void run(){
		while(true){
			boolean done = true;
			if(p.size() == 120){
				for(Private pr : p){
					if(pr.peers.size() != 8){
						done = false;
					}
				}
				if(done == true){ done(); return; }
			}
		}
	}


	Main(){
		p = new ArrayList<Private>();
		try{
			for(int i = 15000; i < 15120; i++){
				(new Thread(new Finder(new Socket("REMOVED_FOR_PRIVACY", i), i, this))).start();
			}
		}
		catch(Exception e){ System.out.printf("error");return;}
		(new Thread(this)).start();
	}

	public static void main(String args[]){
		new Main();
	}
}
