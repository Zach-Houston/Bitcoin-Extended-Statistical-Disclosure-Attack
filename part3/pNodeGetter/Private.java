import java.util.*;
public class Private{
	int port;
	String id;
	ArrayList<Integer> peers;

	public synchronized void addPeer(int port){
		if(peers.contains(port)){ return; }
		else{ peers.add(port); }
	}

	Private(int p, String i){
		port = p;
		id = i;
		peers = new ArrayList<Integer>();
	}

}
