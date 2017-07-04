import java.sql.Timestamp;
import java.util.*;
public class Message{
	String id;
	int port;
	String msg;
	Message(String i, int p, String m){
		id = i;
		port = p;
		msg = m;
	}
}
