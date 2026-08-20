/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package bo.edu.usfx.sockets.soquetsudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author gabriel
 */
public class SoquetsUDP {
    private static final int PUERTO_ESCUCHA_N3 = 5002;
    private static final int PUERTO_NODO1 = 5000;
    private static final String IP_NODO1 = "10.121.247.80";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PUERTO_ESCUCHA_N3)) {
            System.out.println("Nodo 3 listo y escuchando en el puerto, " + PUERTO_ESCUCHA_N3 + "...");

            byte[] buffer = new byte[2048];
            DatagramPacket packetRecibido = new DatagramPacket(buffer, buffer.length);

            socket.receive(packetRecibido);
            String mensajeRecibido = new String(packetRecibido.getData(), 0, packetRecibido.getLength(), StandardCharsets.UTF_8);

            String[] partes = mensajeRecibido.split("\\|", 4);
            String textoOriginal = partes[0];
            String numCaracteres = partes[1];
            String numPalabras = partes[2];
            String esParOImpar = partes[3];

            String textoMayusculas = textoOriginal.toUpperCase();
            int cantidadVocales = contarVocales(textoOriginal);

            String resumenFinal = String.format(
                "RESUMEN FINAL DE PROCESAMIENTO\n" +
                "Texto Original: %s\n" +
                "Texto en Mayusculas: %s\n" +
                "Cantidad de Caracteres: %s (%s)\n" +
                "Cantidad de Palabras: %s\n" +
                "Cantidad de Vocales: %d\n",
                textoOriginal, textoMayusculas, numCaracteres, esParOImpar, numPalabras, cantidadVocales
            );

            byte[] datosEnviar = resumenFinal.getBytes(StandardCharsets.UTF_8);
            InetAddress ipDestino = InetAddress.getByName(IP_NODO1);
            DatagramPacket packetEnviar = new DatagramPacket(datosEnviar, datosEnviar.length, ipDestino, PUERTO_NODO1);
            
            socket.send(packetEnviar);
            System.out.println("Resumen enviado exitosamente al Nodo 1.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int contarVocales(String texto) {
        int contador = 0;
        String textoL = texto.toLowerCase();
        for (int i = 0; i < textoL.length(); i++) {
            char c = textoL.charAt(i);
            if ("aeiouáéíóú".indexOf(c) != -1) {
                contador++;
            }
        }
        return contador;
    }
}
