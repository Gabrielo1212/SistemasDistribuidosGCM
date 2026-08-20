/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package bo.edu.usfx.sockets.practicaenclases;

/**
 *
 * @author villa
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Nodo1 {

    public static void main(String args[]) {

        // Puerto donde Nodo 2 recibe
        int puertoNodo2 = 5001;

        // Puerto donde Nodo 1 espera la respuesta de Nodo 3
        int puertoNodo1 = 5000;

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Introduzca una palabra o frase: ");
            String texto = sc.nextLine();

            int cantidadCaracteres = texto.length();

            System.out.println("Texto: " + texto);
            System.out.println("Cantidad de caracteres: " + cantidadCaracteres);

            String ipNodo2 = "10.121.247.75";
            DatagramSocket socketUDP = new DatagramSocket(puertoNodo1);

            String dato = texto + "|" + cantidadCaracteres;

            byte[] mensaje = dato.getBytes();

            InetAddress hostNodo2 =
                    InetAddress.getByName(ipNodo2);
            DatagramPacket peticion =
                    new DatagramPacket(
                            mensaje,
                            mensaje.length,
                            hostNodo2,
                            puertoNodo2
                    );
            socketUDP.send(peticion);

            System.out.println("Informacion enviada al Nodo 2."); 
            byte[] bufer = new byte[2000];

            DatagramPacket respuesta =
                    new DatagramPacket(
                            bufer,
                            bufer.length
                    );

            System.out.println("Esperando resultado del Nodo 3...");

            socketUDP.receive(respuesta);

            String resultado =
                    new String(
                            respuesta.getData(),
                            0,
                            respuesta.getLength()
                    );
            System.out.println("resultado final");
            System.out.println(resultado);

      
            socketUDP.close();

        } catch (SocketException e) {

            System.out.println("Socket: " + e.getMessage());

        } catch (IOException e) {

            System.out.println("IO: " + e.getMessage());
        }

        sc.close();
    }
}