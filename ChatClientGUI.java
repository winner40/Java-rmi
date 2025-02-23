import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.List;

public class ChatClientGUI extends JFrame implements Client_itf {
    private String username;
    private Chat_itf chat;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatClientGUI(String username, Chat_itf chat) throws RemoteException {
        this.username = username;
        this.chat = chat;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Chat Client - " + username);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }
    @Override
    public void receiveMessage(String message) throws RemoteException {
        chatArea.append(message + "\n");
    }
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return username;
    }
    private void sendMessage() {
        try {
            String message = inputField.getText().trim();
            if (!message.isEmpty()) {
                chat.sendMessage(this, message);
                inputField.setText("");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            if (args.length < 3) {
                System.out.println("Usage: java ChatClientGUI <rmiregistry host> <rmiregistry port> <username>");
                return;
            }

            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String username = args[2];

            Registry registry = LocateRegistry.getRegistry(host, port);
            Chat_itf chat = (Chat_itf) registry.lookup("ChatService");

            ChatClientGUI clientObj = new ChatClientGUI(username, chat);
            Client_itf stub = (Client_itf) UnicastRemoteObject.exportObject(clientObj, 0);
            chat.registerClient(stub);

            // Display chat history
            List<String> history = chat.getHistory();
            for (String msg : history) {
                clientObj.receiveMessage(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
