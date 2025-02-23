import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.List;
import java.util.Scanner;

public class ChatClient implements Client_itf{
    private String username;
    private Chat_itf chat;
    public ChatClient(String username,Chat_itf chat) throws RemoteException {
        this.username = username;
        this.chat = chat;
    }
    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
    @Override
    public String getName() throws RemoteException {
        return username;
    }
    public static void main(String[] args) {
        try {
            if (args.length < 3) {
                System.out.println("Usage: java -classpath .:classes:lib/Hello.jar HelloClient <rmiregistry host> <rmiregistry port> <username>");
                return;
            }

            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String username = args[2];

            Registry registry = LocateRegistry.getRegistry(host,port);
            Chat_itf chat = (Chat_itf) registry.lookup("ChatService");

            ChatClient clientObj = new ChatClient(username, chat);
            Client_itf stub = (Client_itf) UnicastRemoteObject.exportObject(clientObj, 0);
            chat.registerClient(stub);
            // Afficher l'historique
            List<String> history = chat.getHistory();
            System.out.println("=== Historique des messages ===");
            for (String msg : history) {
                System.out.println(msg);
            }

            // Lire et envoyer des messages
            Scanner scanner = new Scanner(System.in);
            String message;
            while (!((message = scanner.nextLine()).equals("exit"))) {
                chat.sendMessage(stub, message);
            }
            chat.unregisterClient(stub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
