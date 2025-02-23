import java.rmi.*;

public interface Client_itf extends Remote {
    String getName() throws RemoteException;
    void receiveMessage(String message) throws RemoteException;
}
