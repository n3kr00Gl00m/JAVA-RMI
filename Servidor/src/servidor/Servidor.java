package servidor;


import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Remoto.Gasolinera;

public class Servidor {
    public static void main(String[] args) throws IOException {

        Registry registro = LocateRegistry.createRegistry(2015);
        Gasolinera gasolinera = new Gasolinera();
        registro.rebind("gasolinera", gasolinera);


        System.out.println("Servidor Funcionando ....");
        System.out.println("pulsa una tecla para finalizar");

        System.in.read();

        System.out.println("Saliendo del servidor ...");
        System.exit(0);

    }
}