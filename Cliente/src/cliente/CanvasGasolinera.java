package cliente;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CanvasGasolinera extends JPanel {
    private final Map<Integer, Character> vehiculos; // Mapa para identificar vehículos y su tipo (C: coche, T: camión)
    private final String[] surtidores; // Estado de los surtidores (vacío, coche, camión)
    private final DefaultListModel<String> colaEspera; // Cola de espera para mostrar en la interfaz

    public CanvasGasolinera() {
        this.vehiculos = new HashMap<>();
        this.surtidores = new String[]{"Vacío", "Vacío", "Vacío", "Vacío"};
        this.colaEspera = new DefaultListModel<>();

        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
    }


    // Método para agregar un vehículo a la visualización
    public synchronized void agregarVehiculo(int id, char tipo) {
        vehiculos.put(id, tipo);
        colaEspera.addElement("Vehículo " + id + " (" + (tipo == 'C' ? "Coche" : "Camión") + ")");
        repaint();
    }

    // Método para iniciar el repostaje y asignar surtidor
    public synchronized void iniciarRepostaje(int id) {
        for (int i = 0; i < surtidores.length; i++) {
            if (surtidores[i].equals("Vacío")) {
                surtidores[i] = vehiculos.get(id) == 'C' ? "Coche " + id : "Camión " + id;
                colaEspera.removeElement("Vehículo " + id + " (" + (vehiculos.get(id) == 'C' ? "Coche" : "Camión") + ")");
                break;
            }
        }
        repaint();
    }

    // Método para finalizar el repostaje y liberar surtidor
    public synchronized void finalizarRespostaje(int id) {
        for (int i = 0; i < surtidores.length; i++) {
            if (surtidores[i].contains(String.valueOf(id))) {
                surtidores[i] = "Vacío";
                break;
            }
        }
        vehiculos.remove(id);
        repaint();
    }

    // Método para dibujar el estado del Canvas
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawString("Gasolinera", 150, 20);

        // Dibujar surtidores
        for (int i = 0; i < surtidores.length; i++) {
            int x = 50 + (i % 2) * 150; // Columna
            int y = 50 + (i / 2) * 100; // Fila
            g.drawRect(x, y, 100, 50); // Surtidor
            g.drawString(surtidores[i], x + 10, y + 30);
        }

        // Dibujar cola de espera
        g.drawString("Cola de espera:", 50, 220);
        int y = 240;
        for (int i = 0; i < colaEspera.size(); i++) {
            g.drawString(colaEspera.get(i), 60, y);
            y += 15;
        }
    }
}
