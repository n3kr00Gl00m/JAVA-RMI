/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IGasolinera;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author pedro
 */
public interface IGasolinera extends Remote{
    void vehiculoALateral(int id) throws RemoteException;

    int entraCoche(int id) throws RemoteException, InterruptedException;

    int entraCamion(int id) throws RemoteException, InterruptedException;

    void saleCoche(int id, int lado) throws RemoteException;

    void saleCamion(int id, int lado) throws RemoteException;
}
