package menwic.pra_transacciones;

import java.util.Scanner;

public class PracticaTransacciones {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println();
            System.out.println(" ~-~-~-~-~-~-~-~-~-~-~-~-~");
            System.out.println(" - - - - Practica 1 - - - -");
            System.out.println(" - - -  Transacciones - - -");
            System.out.println(" ~-~-~-~-~-~-~-~-~-~-~-~-~");
            System.out.println(" 1.Escenario 1 (sin bloqueos)");
            System.out.println(" 2.Escenario 2 (con bloqueos)");
            System.out.println(" ~-~-~-~-~-~-~-~-~-~-~-~-~");

            int opcion;
            while (true) {
                System.out.print(" Selecciona el Algoritmo: \n > ");
                opcion = scanner.nextInt();
                if (opcion == 1 || opcion == 2) {
                    break; // Salir del bucle si la opción es válida
                }
                System.out.println("Opción inválida. Por favor, elige 1 o 2.");
            }

            System.out.println("\n - - - Parámetros - - -");
            System.out.print(" 1.Intervalo de incremento (ms): \n > ");
            long incrementoIntervalo = scanner.nextLong();
            System.out.print(" 2.Cantidad a incrementar: \n > ");
            int incrementoCantidad = scanner.nextInt();
            System.out.print(" 3.Intervalo de decremento (ms): \n > ");
            long decrementoIntervalo = scanner.nextLong();
            System.out.print(" 4.Cantidad a decrementar: \n > ");
            int decrementoCantidad = scanner.nextInt();
            System.out.print(" 5.Tiempo total de ejecucion (ms): \n > ");
            long tiempoEjecucion = scanner.nextLong();
            System.out.print(" 6.Valor inicial de \"contador\": \n > ");
            int valorInicial = scanner.nextInt();
            System.out.println();
            TransaccionManager.inicializarContador(valorInicial); //Settear valor inicial indicado
            System.out.print(" - - - Output - - - \n");

            //Elegir el Escenario/Algoritmo a utilizar
            switch (opcion) {
                case 1:
                    new Algoritmo1().ejecutar(incrementoCantidad, decrementoCantidad, incrementoIntervalo, decrementoIntervalo, tiempoEjecucion);
                    break;
                case 2:
                    new Algoritmo2().ejecutar(incrementoCantidad, decrementoCantidad, incrementoIntervalo, decrementoIntervalo, tiempoEjecucion);
                    break;
            }
            System.out.println("\nPresiona enter para salir al Menu...");
            scanner.nextLine(); // Limpia el buffer
            scanner.nextLine(); // Espera por una tecla
        }
        scanner.close();
    }
}
