package m2dl.pcr.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObserver extends Remote {
    void update(Object updateMsg) throws RemoteException;
}
