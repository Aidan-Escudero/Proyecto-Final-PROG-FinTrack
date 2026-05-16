package dam.fintrack.controller;

import dam.fintrack.dao.CategoriaDAO;
import dam.fintrack.dao.TransaccionDAO;
import dam.fintrack.model.entidades.Categoria;
import dam.fintrack.model.entidades.Gasto;
import dam.fintrack.model.entidades.Ingreso;
import dam.fintrack.model.entidades.Transaccion;
import dam.fintrack.model.exceptions.CampoVacioException;
import dam.fintrack.model.exceptions.ImporteInvalidoException;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransaccionControlador {

    private TransaccionDAO td;
    private CategoriaDAO cd;

    public TransaccionControlador() {
        this.td = new TransaccionDAO();
        this.cd = new CategoriaDAO();
    }

    public ArrayList<Transaccion> obtenerTodas () {
        try {
          return td.listarTodas();
        } catch (SQLException e) {
            System.out.println("Error al obtener transacciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Categoria> obtenerCategorias () {
        try {
            return cd.listarTodas();
        } catch (SQLException e) {
            System.out.println("Error al obtener las categorias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardarTransaccion (String descripcion, String importeTexto,
                                       String tipo, Categoria categoria) {

        try {
            if (descripcion.isBlank()) {
                throw new CampoVacioException("descripción");
            }

            double importe = Double.parseDouble(importeTexto);

            if (importe <= 0) {
                throw new ImporteInvalidoException("importe");
            }

            Transaccion t;
            if (tipo.equals("INGRESO")) {
                t = new Ingreso(0, descripcion, importe, LocalDate.now(), categoria);
            } else {
                t = new Gasto(0, descripcion, importe, LocalDate.now(), categoria);
            }

            td.insertar(t);
            return true;

        } catch (CampoVacioException e) {
            System.out.println("Error: " + e.getMessage());;
            return false;
        } catch (ImporteInvalidoException e) {
            System.out.println("Error: " + e.getMessage());;
            return false;
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());;
            return false;
        }
    }

    public boolean eliminarTransaccion (int id) {
        try {
            td.eliminar(id);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    public double calcularBalance (ArrayList<Transaccion> lista) {
        double balance = 0;
        for (Transaccion t : lista) {
            if (t.getTipo().equals("INGRESO")) {
                balance += t.getImporte();
            } else {
                balance -= t.getImporte();
            }
        }
        return balance;
    }

    public double calcularTotalIngresos (ArrayList<Transaccion> lista) {
        double total = 0;
        for (Transaccion t : lista) {
            if ((t.getTipo().equals("INGRESO"))) {
                total += t.getImporte();
            }
        }
        return total;
    }
}
