/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import IGasolinera.IGasolinera;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 *
 * @author pedro
 */
public class Generador {

    /**
     * @param args the command line arguments
     */

     private static final int NUMEROCOCHES = 15;
     private static final int NUMEROCAMIONES = 15;
     private static final int NUMEROVEHICULOS = 30;
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            Registry Registro = LocateRegistry.getRegistry("localhost", 2015);
            // Vemos lo que ofrece el registro
            String[] oferta = Registro.list();
            for (int i = 0; i < oferta.length; i++) {
                System.out.println("Elemento " + i + " del registro: " + oferta[i]);
            }

            IGasolinera gasolinera = (IGasolinera) Naming.lookup("rmi://localhost:2015/gasolinera");

            Random rd = new Random();

            CanvasGasolinera canvas = new CanvasGasolinera();

            JFrame frame = new JFrame("Simulación Servidor.Remoto.Gasolinera");
            frame.add(canvas);
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            Thread[] coches = new Thread[NUMEROCOCHES];
            Camion[] camiones = new Camion[NUMEROCAMIONES];
            Thread[] camionThreads = new Thread[NUMEROCAMIONES];

            for (int i = 0; i < NUMEROCOCHES; i++) {
                coches[i] = new Thread(new Cliente.Coche(i, gasolinera, canvas, true));
            }

            for (int i = 0; i < NUMEROCAMIONES; i++) {
                camiones[i] = new Camion(i + NUMEROCOCHES, gasolinera, canvas, false);
                camionThreads[i] = new Thread(camiones[i]);
            }

            int numeroCamionesLanzados = 0, numeroCochesLanzados = 0;

            for (int i = 0; i < NUMEROVEHICULOS; i++) {
                if (rd.nextBoolean() && numeroCamionesLanzados < NUMEROCAMIONES) {
                    camionThreads[numeroCamionesLanzados].start(); // Iniciar hilo de camión
                    numeroCamionesLanzados++;
                } else if (numeroCochesLanzados < NUMEROCOCHES) {
                    coches[numeroCochesLanzados].start(); // Iniciar hilo de coche
                    numeroCochesLanzados++;
                }

                Thread.sleep(500); // Esperar antes de lanzar el siguiente vehículo
            }

            // Esperar a que todos los coches y camiones terminen
            for (int i = 0; i < numeroCochesLanzados; i++) {
                coches[i].join();
            }

            for (int i = 0; i < numeroCamionesLanzados; i++) {
                camionThreads[i].join();
            }

            System.out.println("Termina MAIN.");

        } catch (NotBoundException | MalformedURLException | RemoteException | InterruptedException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
