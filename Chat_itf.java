import java.rmi.*;
import java.util.List;

public interface Chat_itf extends Remote {
    void registerClient(Client_itf client) throws RemoteException;
    void unregisterClient(Client_itf client) throws RemoteException;
    void sendMessage(Client_itf client, String message) throws RemoteException;
    List<String> getHistory() throws RemoteException;
}
