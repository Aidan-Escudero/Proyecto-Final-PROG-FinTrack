package dam.fintrack.view;

import dam.fintrack.controller.TransaccionControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private TransaccionControlador controlador;
    private CardLayout cardLayout;
    private JPanel contenedor;

    private PanelDashboard panelDashboard;
    private PanelFormulario panelFormulario;
    private PanelResumen panelResumen;

    public MainFrame () {
        this.controlador = new TransaccionControlador();
        initComponents();
    }

    private void initComponents () {
        setTitle("FinTrack");
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearSidebar(), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(26, 31, 46));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Logo
        JLabel logo = new JLabel("  FinTrack");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 16));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(logo);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(45, 53, 71));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(8));

        // Para los botones del sidebar
        JButton btnDashboard   = crearNavBtn("    Dashboard",   "DASHBOARD");
        JButton btnTransaccion = crearNavBtn("  Transacciones", "FORMULARIO");
        JButton btnResumen     = crearNavBtn("  Resumen",   "RESUMEN");

        sidebar.add(btnDashboard);
        sidebar.add(btnTransaccion);
        sidebar.add(btnResumen);
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JButton crearNavBtn (String texto, String panel) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBackground(new Color (26, 31, 46));
        btn.setForeground(new Color(136, 146, 164));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addActionListener(e -> navegarA(panel));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered (java.awt.event.MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(136, 146, 164));
            }
        });

        return btn;
    }

    private JPanel crearContenido() {
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setBackground(new Color(244, 246, 250));

        panelDashboard  = new PanelDashboard(controlador, this);
        panelFormulario = new PanelFormulario(controlador, this);
        panelResumen    = new PanelResumen(controlador, this);

        contenedor.add(panelDashboard,  "DASHBOARD");
        contenedor.add(panelFormulario, "FORMULARIO");
        contenedor.add(panelResumen,    "RESUMEN");

        return contenedor;
    }

    public void navegarA (String panel) {
        cardLayout.show(contenedor, panel);

        // Refresca los datos al cambiar de pantalla
        if (panel.equals("DASHBOARD")) {
            panelDashboard.refrescar();
        }

        if (panel.equals("RESUMEN")) {
            panelResumen.refrescar();
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
