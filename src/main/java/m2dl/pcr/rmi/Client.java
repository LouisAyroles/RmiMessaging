package m2dl.pcr.rmi;

import m2dl.pcr.rmi.domain.Message;
import m2dl.pcr.rmi.interfaces.Messaging;
import m2dl.pcr.rmi.interfaces.RemoteObserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Client  extends UnicastRemoteObject implements RemoteObserver{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static String userName = "";
    private static final Scanner userInput = new Scanner(System.in);

    private Client() throws RemoteException {
        super();
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Messaging stub = (Messaging) registry.lookup("Message");

            userName = getUserName();
            Client client = new Client();
            stub.addObserver(client);

            while(true) {
               Message message = new Message(userName, getMessage(), getHour());
               stub.sendMessage(message);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void update(Object updateMsg) throws RemoteException {
        System.out.println(beautifulMessage((Message) updateMsg));
    }

    public static String getUserName(){
        System.out.println("What's your name?");
        String input = userInput.nextLine();
        if (!input.isEmpty()) {
            return input;
        }
        return "You";
    }

    public static String getMessage(){
        String input = userInput.nextLine();
        if (!input.isEmpty()) {
            return input;
        }
        return "Empty message";
    }

    public static String getHour(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void printMessages(List<Message> messages){
        for (Message message : messages) {
           printMessage(message);
        }
    }

    public static void printMessage(Message message){
        System.out.println("[" + message.date + "] " + message.sender + ": " + message.message);
    }

    public static String  beautifulMessage(Message message){
       return ANSI_RED + "[" + message.date + "] " + ANSI_GREEN +  message.sender + ": " + ANSI_BLUE + message.message + ANSI_RESET;
    }
}