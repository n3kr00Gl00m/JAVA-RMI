package Cliente;

import java.rmi.RemoteException;
import java.util.Random;

import IGasolinera.IGasolinera;
import cliente.CanvasGasolinera;

public class Coche implements Runnable {
    private final int id;
    private final IGasolinera gasolinera;
    private final CanvasGasolinera canvas;
    private boolean enCentrales;

    public Coche(int id, IGasolinera gasolinera, CanvasGasolinera canvas, boolean enCentrales) {
        this.id = id;
        this.gasolinera = gasolinera;
        this.canvas = canvas;
        this.enCentrales = enCentrales;
        canvas.agregarVehiculo(this.id,'C');
    }

    @Override
    public void run() {
        try {
            int posicion = gasolinera.entraCoche(id); // El coche entra a la gasolinera y obtiene la posición
            this.canvas.iniciarRepostaje(this.id);
            Thread.sleep(new Random().nextInt(1000) + 2000); // Simula el tiempo de repostaje
            gasolinera.saleCoche(this.id, posicion); // El coche sale de la gasolinera
            this.canvas.finalizarRespostaje(this.id);
        } catch (RemoteException e) {
            System.err.println("Error de comunicación RMI: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Hilo interrumpido: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
