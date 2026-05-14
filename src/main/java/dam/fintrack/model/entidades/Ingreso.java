package dam.fintrack.model.entidades;

import java.time.LocalDate;

public class Ingreso extends Transaccion {

    public Ingreso(int id, String descripcion, double importe, LocalDate fecha, Categoria categoria) {
        super(id, descripcion, importe, fecha, categoria);
    }

    @Override
    public String getTipo() {
        return "INGRESO";
    }
}
