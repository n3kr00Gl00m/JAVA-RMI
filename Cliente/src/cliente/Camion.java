package cliente;


import java.rmi.RemoteException;
import java.util.Random;

import IGasolinera.IGasolinera;

public class Camion implements Runnable {
    private final int id;
    private final IGasolinera gasolinera;
    private final CanvasGasolinera canvas;
    public Camion(int id, IGasolinera gasolinera, CanvasGasolinera canvas, boolean b) {
        this.id = id;
        this.gasolinera = gasolinera;
        this.canvas = canvas;
        canvas.agregarVehiculo(this.id,'T');

    }

    @Override
    public void run() {
        try {
            int posicion = gasolinera.entraCamion(id); // El camion entra a la gasolinera y obtiene la posicion
            this.canvas.iniciarRepostaje(this.id);

            Thread.sleep(new Random().nextInt(1000) + 1000); // Simula el tiempo de repostaje
            gasolinera.saleCamion(this.id, posicion); // El camión sale de la gasolinera
            this.canvas.finalizarRespostaje(this.id);

        }  catch (RemoteException e) {
        System.err.println("Error de comunicación RMI: " + e.getMessage());
        e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Hilo interrumpido: " + e.getMessage());
            e.printStackTrace();
        }

}
}
