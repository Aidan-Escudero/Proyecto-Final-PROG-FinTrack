package dam.fintrack.model.entidades;

import java.time.LocalDate;

public abstract class Transaccion {

    protected int id;
    protected String descripcion;
    protected double importe;
    protected LocalDate fecha;
    protected Categoria categoria;

    public Transaccion(int id, String descripcion, double importe, LocalDate fecha, Categoria categoria) {
        this.id = id;
        this.descripcion = descripcion;
        this.importe = importe;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    public abstract String getTipo ();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
