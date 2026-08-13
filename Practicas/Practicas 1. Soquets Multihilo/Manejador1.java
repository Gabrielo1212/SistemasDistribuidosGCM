package bo.edu.usfx.sockets;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Manejador1 implements Runnable {
    // Estado COMPARTIDO por todos los hilos (Thread-Safe)
    private static final Set<Manejador1> CLIENTES = new CopyOnWriteArraySet<>();
    
    private final Socket cliente;
    private final int id;
    private PrintWriter salida;

    public Manejador1(Socket cliente, int id) {
        this.cliente = cliente;
        this.id = id;
    }

    @Override
    public void run() {
        String hilo = Thread.currentThread().getName();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
             PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            this.salida = out;
            CLIENTES.add(this); // Nos registramos en la lista global

            out.println("Bienvenido al Chat. Le atiende el hilo: " + hilo);
            difundir(">>> Cliente " + id + " se ha unido al chat.");

            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println("[" + hilo + "] cliente " + id + ": " + linea);
                // Difundir a todos los DEMÁS clientes
                difundir("cliente-" + id + "> " + linea);
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente " + id + ": " + e.getMessage());
        } finally {
            CLIENTES.remove(this); // Nos quitamos al salir
            difundir("<<< Cliente " + id + " ha salido del chat.");
            try { cliente.close(); } catch (IOException e) { }
            System.out.println("Cliente " + id + " desconectado");
        }
    }

    // Método para enviar mensaje a todos los demás hilos/clientes
    private void difundir(String mensaje) {
        for (Manejador1 m : CLIENTES) {
            if (m != this && m.salida != null) {
                m.salida.println(mensaje);
            }
        }
    }
}