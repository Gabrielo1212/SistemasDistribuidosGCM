package bo.edu.usfx.calidad.ejercicio2clases;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Nodo2 {

  public static void main(String args[]) {
    // Nodo 2
    int puertoEscucha = 5001; 
    
    // Nodo 3
    int puertoNodo3 = 5002;
    String ipNodo3 = "10.121.247.229";

    try {
      DatagramSocket socketUDP = new DatagramSocket(puertoEscucha);
      byte[] bufer = new byte[1000];

      System.out.println("Nodo 2 esperando datos del Nodo 1 en el puerto " + puertoEscucha + "...");

     // while (true) {
        DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
        socketUDP.receive(peticion);

        String datosRecibidos = new String(peticion.getData(), 0, peticion.getLength());

        System.out.println("Información recibida desde Nodo 1 (" + peticion.getAddress() + "):");
        System.out.println("Dato bruto: " + datosRecibidos);

        String informacionProcesada = procesar(datosRecibidos);

        System.out.println("Resultado procesado a enviar a Nodo 3: " + informacionProcesada);

        byte[] mensaje = informacionProcesada.getBytes();
        InetAddress hostNodo3 = InetAddress.getByName(ipNodo3);

        DatagramPacket envioNodo3 = new DatagramPacket(
            mensaje,
            mensaje.length,
            hostNodo3,
            puertoNodo3
        );


        socketUDP.send(envioNodo3);
        System.out.println("Información enviada con éxito al Nodo 3.");
     // }

    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    }
  }

  public static String procesar(String cadena) {
    String[] partes = cadena.split("\\|");
    
    String textoOriginal = partes[0];
    int cantidadCaracteres = Integer.parseInt(partes[1].trim());

    int cantidadPalabras = 0;
    if (!textoOriginal.trim().isEmpty()) {
      cantidadPalabras = textoOriginal.trim().split("\\s+").length;
    }

    String paridad = (cantidadCaracteres % 2 == 0) ? "Par" : "Impar";

    System.out.println("-> Texto: " + textoOriginal);
    System.out.println("-> Caracteres: " + cantidadCaracteres);
    System.out.println("-> Palabras calculadas: " + cantidadPalabras);
    System.out.println("-> Paridad calculada: " + paridad);

    return textoOriginal + "|" + cantidadCaracteres + "|" + cantidadPalabras + "|" + paridad;
  }
}