package dam.fintrack.dao;

import dam.fintrack.model.entidades.Categoria;
import dam.fintrack.model.entidades.Gasto;
import dam.fintrack.model.entidades.Ingreso;
import dam.fintrack.model.entidades.Transaccion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransaccionDAO {

    public void insertar (Transaccion t) throws SQLException {

        ConexionDB cn = new ConexionDB();
        cn.abrirCon();

        String sql = "INSERT INTO transaccion (descripcion, importe, fecha, tipo, id_categoria) " +
                     " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = cn.getCon().prepareStatement(sql);
        ps.setString(1, t.getDescripcion());
        ps.setDouble(2, t.getImporte());
        ps.setDate(3, Date.valueOf(t.getFecha()));
        ps.setInt(4, t.getCategoria().getId());
        ps.executeUpdate();

        cn.cerrarCon();
    }

    public ArrayList<Transaccion> listarTodas () throws SQLException {
        ConexionDB db = new ConexionDB();
        db.abrirCon();

        ArrayList<Transaccion> lista = new ArrayList<>();

        String sql = "SELECT t.id, t.descripcion, t.importe, t.fecha, t.tipo, " +
                     "c.id AS cat_id, c.nombre AS cat_nombre " +
                     "FROM transaccion t " +
                     "JOIN categoria c ON t.id_categoria = c.id";

        Statement st = db.getCon().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Categoria categoria = new Categoria(
                    rs.getInt("cat_id"),
                    rs.getString("cat_nombre")
            );
            LocalDate fecha = rs.getDate("fecha").toLocalDate();

            Transaccion transaccion;
            if (rs.getString("tipo").equals("INGRESO")) {
                transaccion = new Ingreso(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        rs.getDouble("importe"),
                        fecha,
                        categoria
                );
            } else {
                transaccion = new Gasto(
                  rs.getInt("id"),
                  rs.getString("descripcion"),
                  rs.getDouble("importe"),
                  fecha,
                  categoria
                );
            }
            lista.add(transaccion);
        }
        db.cerrarCon();
        return lista;
    }

    public void eliminar (int id) throws SQLException {
        ConexionDB db = new ConexionDB();
        db.abrirCon();

        String sql = "DELETE FROM transaccion WHERE id = ?";

        PreparedStatement ps = db.getCon().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

        db.cerrarCon();
    }
}
