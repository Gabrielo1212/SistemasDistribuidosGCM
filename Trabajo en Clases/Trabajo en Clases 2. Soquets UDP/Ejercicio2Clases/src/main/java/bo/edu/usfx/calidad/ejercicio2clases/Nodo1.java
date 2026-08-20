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
import java.util.Scanner;

public class Nodo1 {

  public static void main(String args[]) {
    int puertoEscucha = 5001; //  Nodo 3
    int puertoNodo2 = 5002;   //  Nodo 2
    String ipNodo2 = "10.121.247.229";

    Scanner sc = new Scanner(System.in);

    try {
      System.out.print("Ingrese una palabra o frase: ");
      String texto = sc.nextLine();

      // 1. Contar la cantidad de caractere
      int cantidadCaracteres = texto.length();

      String mensajeEnviar = texto + ";" + cantidadCaracteres;

      DatagramSocket socketUDP = new DatagramSocket(puertoEscucha);
      byte[] mensajeBytes = mensajeEnviar.getBytes();
      InetAddress hostNodo2 = InetAddress.getByName(ipNodo2);

      DatagramPacket peticion = new DatagramPacket(
          mensajeBytes, 
          mensajeBytes.length, 
          hostNodo2, 
          puertoNodo2
      );
      socketUDP.send(peticion);
      System.out.println("Datos enviados al Nodo 2. Esperando resultado final...");

      byte[] bufer = new byte[2000];
      DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
      socketUDP.receive(respuesta);

      String resultadoFinal = new String(respuesta.getData(), 0, respuesta.getLength());
      
      System.out.println("\n Resultado: \n");
      System.out.println(resultadoFinal);

      socketUDP.close();

    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    }
  }
}
