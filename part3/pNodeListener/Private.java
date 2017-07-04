import java.util.*;
public class Private{
	String msg;
	ArrayList<Integer> ports;
	
	public synchronized void addPort(int p){
		ports.add(p);
	}

	Private(String msgID, int initport){
		msg = msgID;
		ports = new ArrayList<Integer>();
		ports.add(initport);
	}
}
