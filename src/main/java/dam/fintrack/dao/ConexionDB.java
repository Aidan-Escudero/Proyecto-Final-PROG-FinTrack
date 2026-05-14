package dam.fintrack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mariadb://localhost:3306/fintrack_db";
    private static final String USR = "root";
    private static final String PWD = "";

    private Connection con;

    public void abrirCon () throws SQLException {
        con = DriverManager.getConnection(URL, USR, PWD);
    }

    public void cerrarCon () throws SQLException {
        if (con != null && !con.isClosed()) { // Si no está vacía y tampoco cerrada
            con.close(); // cierra la conexión
        }
    }

    public Connection getCon () {
        return con;
    }

}
