package m2dl.pcr.rmi;

import m2dl.pcr.rmi.domain.Message;
import m2dl.pcr.rmi.interfaces.Messaging;
import m2dl.pcr.rmi.interfaces.RemoteObserver;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Server extends Observable implements Messaging {

    List<RemoteObserver> observers = new ArrayList<>();
    List<Message> messages = new ArrayList<>();

    public Server() {
    }

    public List<Message> getAllMessages() {
        return messages;
    }

    public void sendMessage(Message message) throws RemoteException {
        messages.add(message);
        for (RemoteObserver observer : observers) {
            observer.update(message);
        }
    }

    @Override
    public void addObserver(RemoteObserver o) throws RemoteException {
        observers.add(o);
    }


    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Messaging stub = (Messaging) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Message", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}