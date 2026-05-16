package dam.fintrack.view;

import dam.fintrack.controller.TransaccionControlador;
import dam.fintrack.model.entidades.Transaccion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.AWTEventMulticaster.add;
import static javax.swing.text.StyleConstants.setBackground;

public class PanelDashboard extends JPanel{
    private TransaccionControlador controlador;
    private MainFrame mainFrame;

    private JLabel lblBalance;
    private JLabel lblIngresos;
    private JLabel lblGastos;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JButton btnNueva;

    public PanelDashboard () {
        this.controlador = new TransaccionControlador();
        this.mainFrame = new MainFrame();
        initComponents();
        refrescar();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground (new Color(244, 246, 250));

        add(crearTopBar(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(new Color(26, 31, 46));

        btnNueva.setBackground(new Color(59, 130, 246));
        btnNueva.setForeground(Color.WHITE);
        btnNueva.setBorderPainted(false);
        btnNueva.setFocusPainted(false);
        btnNueva.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnNueva.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNueva.addActionListener(e -> mainFrame.navegarA("FORMULARIO"));

        top.add(titulo, BorderLayout.WEST);
        top.add(btnNueva, BorderLayout.EAST);
        return top;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setBackground(new Color(244, 246, 250));
        centro.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 20));

        centro.add(crearTarjetas(), BorderLayout.NORTH);
        centro.add(crearTarjetas(), BorderLayout.CENTER);

        return centro;
    }

    private JPanel crearTarjetas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 12, 0));
        panel.setBackground(new Color(255, 246, 250));

        lblBalance = new JLabel("0,00 $", SwingConstants.CENTER);
        lblIngresos = new JLabel("0,00 $", SwingConstants.CENTER);
        lblGastos = new JLabel("0,00 $", SwingConstants.CENTER);

        panel.add(crearTarjeta("Balance total",     lblBalance, new Color(37, 99, 235)));
        panel.add(crearTarjeta("Ingresos del mes",  lblIngresos, new Color(22, 163, 74)));
        panel.add(crearTarjeta("Gastos del mes",    lblGastos, new Color(220, 38, 38)));

        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel lblValor, Color colorValor) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        lblTitulo.setForeground(new Color(148, 163, 184));

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblValor.setForeground(colorValor);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Descripción", "Categoría", "Fecha", "Tipo", "Importe"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable (int row, int col) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(248, 250, 252));
        tabla.getTableHeader().setForeground(new Color(100, 116, 139));
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(241, 245, 249));
        tabla.setSelectionBackground(new Color(239, 246, 255));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        return scroll;
    }

    private JPanel crearBotones() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(244, 246, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 20, 12, 20));

        btnEliminar = new JButton("Eliminar seleccionada");
        btnEliminar.setForeground(new Color(220, 246, 250));
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(e -> eliminar());

        panel.add(btnEliminar);
        return panel;
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una transaccion primero.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this, "¿Eliminar esta transacción?", "Confirmar", JOptionPane.YES_NO_OPTION
        );
        if (confirmacion == JOptionPane.YES_OPTION) {
            // El id se va guardando en una lista paralela al refrescar
            int id = idTransacciones.get(fila);
            boolean exito = controlador.eliminarTransaccion(id);
            if (exito) {
                refrescar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ArrayList<Integer> idTransacciones = new ArrayList<>();

    public void refrescar() {
        modeloTabla.setRowCount(0);
        idTransacciones.clear();

        ArrayList<Transaccion> lista = controlador.obtenerTodas();

        double ingresos = controlador.calcularTotalIngresos(lista);
        double gastos   = controlador.calcularTotalGastos(lista);
        double balance  = controlador.calcularBalance(lista);

        lblBalance.setText(String.format("%.2f $", balance));
        lblIngresos.setText(String.format("%.2f $", ingresos));
        lblGastos.setText(String.format("%.2f $", gastos));

        for (Transaccion t : lista) {
            modeloTabla.addRow(new Object[] {
                    t.getDescripcion(),
                    t.getCategoria().getNombre(),
                    t.getFecha(),
                    t.getTipo(),
                    String.format("%.2f $", t.getImporte())
            });
            idTransacciones.add(t.getId());
        }
    }
}
