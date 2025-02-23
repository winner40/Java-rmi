
import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ChatImpl c= new ChatImpl();
            Chat_itf stub = (Chat_itf) UnicastRemoteObject.exportObject(c, 0);

            Registry registry = null;
	        if (args.length>0)
		        registry= LocateRegistry.getRegistry(Integer.parseInt(args[0])); 
	        else
		        registry = LocateRegistry.getRegistry();
	        registry.rebind("ChatService", stub);

            System.out.println ("Server ready");
        } catch (Exception e) {
            System.err.println("Error on server :" + e) ;
            e.printStackTrace();
        }
    }
}
