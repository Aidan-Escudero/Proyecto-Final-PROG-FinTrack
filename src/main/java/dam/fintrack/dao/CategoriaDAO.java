package dam.fintrack.dao;

import dam.fintrack.model.entidades.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaDAO {

    public ArrayList<Categoria> listarTodas () throws SQLException {
        ConexionDB db = new ConexionDB();
        db.abrirCon();

        ArrayList<Categoria> lista = new ArrayList<>();

        String sql = "SELECT id, nombre FROM categoria";

        Statement st = db.getCon().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Categoria categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre")
            );
            lista.add(categoria);
        }
        db.cerrarCon();
        return lista;
    }
}
