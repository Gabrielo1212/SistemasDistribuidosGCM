/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.usfx.sockets;

import java.io.*;
import java.net.*;
/**
 *
 * @author gabriel
 */
public class Manejador implements Runnable {
    private final Socket cliente;
    private final int id;

    public Manejador(Socket cliente, int id) {
        this.cliente = cliente;
        this.id = id;
    }

    @Override
    public void run() { // Se ejecuta en un HILO INDEPENDIENTE
        String hilo = Thread.currentThread().getName();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
             PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            out.println("Bienvenido. Le atiende el hilo: " + hilo);
            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println("[" + hilo + "] cliente " + id + ": " + linea);
                out.println("ECO(" + hilo + "): " + linea);
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente " + id + ": " + e.getMessage());
        } finally {
            try { cliente.close(); } catch (IOException e) { }
            System.out.println("Cliente " + id + " desconectado");
        }
    }
}