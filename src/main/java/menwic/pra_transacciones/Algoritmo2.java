package menwic.pra_transacciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;

public class Algoritmo2 {

    private static final String URL = "jdbc:mysql://localhost:3306/pra_transacciones";
    private static final String USER = "root";
    private static final String PASSWORD = "menwic467";

    public void ejecutar(int incrementoCantidad, int decrementoCantidad, long incrementoIntervalo, long decrementoIntervalo, long tiempoEjecucion) {
        Thread hiloIncremento = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < tiempoEjecucion) {
                    incrementarContadorConLock(conn, incrementoCantidad, "incremento");
                    Thread.sleep(incrementoIntervalo);
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread hiloDecremento = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < tiempoEjecucion) {
                    decrementarContadorConLock(conn, decrementoCantidad, "decremento");
                    Thread.sleep(decrementoIntervalo);
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        hiloIncremento.start();
        hiloDecremento.start();

        try {
            hiloIncremento.join();
            hiloDecremento.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TransaccionManager.imprimirContadorFinal();
    }

    private void incrementarContadorConLock(Connection conn, int cantidad, String tipo) {
        try {
            conn.setAutoCommit(false);
            conn.prepareStatement("LOCK TABLES movimiento WRITE").execute();

            PreparedStatement ps = conn.prepareStatement("UPDATE movimiento SET contador = contador + ? WHERE id = 1");
            ps.setInt(1, cantidad);
            ps.executeUpdate();

            conn.commit();
            conn.prepareStatement("UNLOCK TABLES").execute();
            System.out.println("> [" + tipo + "] - [" + LocalTime.now() + "] - [Completada]");
        } catch (SQLException e) {
            System.out.println("> [" + tipo + "] - [" + LocalTime.now() + "] - [Error]");
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void decrementarContadorConLock(Connection conn, int cantidad, String tipo) {
        try {
            conn.setAutoCommit(false);
            conn.prepareStatement("LOCK TABLES movimiento WRITE").execute();

            PreparedStatement ps = conn.prepareStatement("UPDATE movimiento SET contador = contador - ? WHERE id = 1");
            ps.setInt(1, cantidad);
            ps.executeUpdate();

            conn.commit();
            conn.prepareStatement("UNLOCK TABLES").execute();
            System.out.println("> [" + tipo + "] - [" + LocalTime.now() + "] - [Completada]");
        } catch (SQLException e) {
            System.out.println("> [" + tipo + "] - [" + LocalTime.now() + "] - [Error]");
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
