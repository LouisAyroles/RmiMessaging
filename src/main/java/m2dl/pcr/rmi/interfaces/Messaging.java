package m2dl.pcr.rmi.interfaces;


import m2dl.pcr.rmi.domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Messaging extends Remote {
    List<Message> getAllMessages() throws RemoteException;
    void sendMessage(Message message) throws RemoteException;
    void addObserver(RemoteObserver o) throws RemoteException;
}
