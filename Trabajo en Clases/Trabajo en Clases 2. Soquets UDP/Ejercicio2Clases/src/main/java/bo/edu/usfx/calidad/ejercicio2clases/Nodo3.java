/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.usfx.calidad.ejercicio2clases;

/**
 *
 * @author antra
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Nodo3 {

  public static void main(String args[]) {
    int puertoEscucha = 6791; // Nodo 2
    int puertoNodo1 = 6789;   // Nodo 1
    String ipNodo1 = "localhost";

    try {
      DatagramSocket socketUDP = new DatagramSocket(puertoEscucha);
      byte[] bufer = new byte[1000];

      System.out.println("Nodo 3 esperando datos del Nodo 2 en el puerto " + puertoEscucha + "...");

      while (true) {
        DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
        socketUDP.receive(peticion);

        System.out.print("Información recibida desde: " + peticion.getAddress());
        System.out.println(" por el puerto: " + peticion.getPort());

        String datosRecibidos = new String(peticion.getData(), 0, peticion.getLength());

        String resumenFinal = procesar(datosRecibidos);

        byte[] mensaje = resumenFinal.getBytes();
        InetAddress hostNodo1 = InetAddress.getByName(ipNodo1);

        DatagramPacket envioNodo1 = new DatagramPacket(
            mensaje,
            mensaje.length,
            hostNodo1,
            puertoNodo1
        );

        socketUDP.send(envioNodo1);
        System.out.println("Resumen final enviado con éxito al Nodo 1.");
      }

    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    }
  }

  public static String procesar(String cadena) {
    String[] partes = cadena.split(";");
    String textoOriginal = partes[0];
    String cantidadCaracteres = partes[1];
    String cantidadPalabras = partes[2];
    String paridad = partes[3];

    String textoMayusculas = textoOriginal.toUpperCase();

    int cantidadVocales = 0;
    String textoMinusculas = textoOriginal.toLowerCase();
    for (int i = 0; i < textoMinusculas.length(); i++) {
      char c = textoMinusculas.charAt(i);
      if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
          c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú') {
        cantidadVocales++;
      }
    }

    StringBuilder resumen = new StringBuilder();
    resumen.append("Texto Original: ").append(textoOriginal).append("\n");
    resumen.append("Texto en Mayúsculas: ").append(textoMayusculas).append("\n");
    resumen.append("Cantidad de Caracteres: ").append(cantidadCaracteres).append("\n");
    resumen.append("Cantidad de Palabras: ").append(cantidadPalabras).append("\n");
    resumen.append("Paridad de Caracteres: ").append(paridad).append("\n");
    resumen.append("Cantidad de Vocales: ").append(cantidadVocales);

    return resumen.toString();
  }
}
