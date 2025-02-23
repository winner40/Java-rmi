import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatImpl implements Chat_itf {
    private List<Client_itf> clients = new ArrayList<>();
    private List<String> history = new ArrayList<>();
    private static final String HISTORY_FILE = "chat_history.txt";
    ChatImpl() {
        loadHistory();
    }

    // Charger l'historique depuis un fichier au démarrage
    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
            System.out.println("Historique chargé.");
        } catch (IOException e) {
            System.out.println("Aucun historique trouvé.");
        }
    }
    
    // Sauvegarder un message dans le fichier
    private void saveMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Enregistrer un client
    public synchronized void registerClient(Client_itf client) throws RemoteException {
        clients.add(client);
        System.out.println("Leclient "+client.getName()+" s'est connecté.");
    }

    // Désenregistrer un client
    public synchronized void unregisterClient(Client_itf client) throws RemoteException {
        clients.remove(client);
        System.out.println("Le client "+client.getName()+" s'est déconnecté.");
    }

    // Envoyer un message à tous les clients et le sauvegarder
    public synchronized void sendMessage(Client_itf client, String message) throws RemoteException {
        String fullMessage = client.getName() + ": " + message;
        history.add(fullMessage);
        saveMessage(fullMessage); // Sauvegarde persistante
        System.out.println(fullMessage);

        for (Client_itf cl : clients) {
            cl.receiveMessage(fullMessage);
        }
    }

    // Récupérer l'historique des messages
    public synchronized List<String> getHistory() throws RemoteException {
        return history;
    }
}
