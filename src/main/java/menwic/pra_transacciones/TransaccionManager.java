package menwic.pra_transacciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransaccionManager {

    private static final String URL = "jdbc:mysql://localhost:3306/pra_transacciones";
    private static final String USER = "root";
    private static final String PASSWORD = "menwic467";

    public static void inicializarContador(int valorInicial) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("UPDATE movimiento SET contador = ? WHERE id = 1");
            ps.setInt(1, valorInicial);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirContadorFinal() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("SELECT contador FROM movimiento WHERE id = 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(" Valor final del \"contador\": " + rs.getInt("contador"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
