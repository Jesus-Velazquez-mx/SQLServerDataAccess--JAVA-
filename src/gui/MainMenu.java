package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JButton btnOperaciones, btnConsultas, btnSalir, btnProcedimientos;

    public MainMenu() {
        super("Menú Principal");
        DigConfig digConf = new DigConfig(this, true);
        digConf.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        InitComponents();
    }

    private void InitComponents() {
        // Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 123, 255));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel titulo = new JLabel("Menú Principal", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelSuperior.add(titulo, BorderLayout.CENTER);
        
        // Crear un JLabel vacío para agregar un espacio antes de la descripción
        JLabel labelVacio = new JLabel(""); // JLabel sin texto
        labelVacio.setPreferredSize(new Dimension(10, 10)); // Establece un tamaño pequeño para que sea solo un espacio
        JLabel labelVacio2 = new JLabel(""); // JLabel sin texto
        labelVacio2.setPreferredSize(new Dimension(10, 10)); // Establece un tamaño pequeño para que sea solo un espacio

        // Crear el JLabel para la descripción
        JLabel descripcion = new JLabel("BIENVENIDO A SALUD Y VIDA. ¿QUÉ QUIERES HACER?");
        descripcion.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
        descripcion.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12)); // Fuente en negrita y cursiva
        descripcion.setForeground(Color.DARK_GRAY); // Color del texto

        // Panel para colocar el label vacío y la descripción debajo del menú
        JPanel panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BorderLayout());
        panelDescripcion.add(labelVacio, BorderLayout.NORTH); // Agregar el label vacío antes de la descripción
        panelDescripcion.add(labelVacio2, BorderLayout.SOUTH); 
        panelDescripcion.add(descripcion, BorderLayout.CENTER); // Agregar la descripción debajo del label vacío

 
        // Agregar el panel de descripción debajo del menú principal
        panelSuperior.add(panelDescripcion, BorderLayout.SOUTH);


        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setLayout(new GridLayout(2, 2, 20, 20));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnOperaciones = CrearBoton("Operaciones", "Ir a la gestión de registros");
        btnConsultas = CrearBoton("Consultas", "Ir a las consultas de datos");
        btnProcedimientos = CrearBoton("Procedimientos Almacenados", "Ir a los procedimientos almacenados");
        btnSalir = CrearBoton ("Salir", "Salir");

        panelCentral.add(btnOperaciones);
        panelCentral.add(btnConsultas);
        panelCentral.add(btnProcedimientos);
        panelCentral.add(btnSalir);

        // Panel inferior con información
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(245, 245, 245));
        panelInferior.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel soporte = new JLabel("Soporte Técnico: soporte@clinica.com", JLabel.CENTER);
        soporte.setForeground(Color.GRAY);
        soporte.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInferior.add(soporte, BorderLayout.CENTER);

        // Agregar paneles a la ventana
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton CrearBoton(String texto, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setToolTipText(tooltip);
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setBackground(new Color(0, 51, 102));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOperaciones) {
            JFrame menuPrincipal = this; 
            MainFrame mainFrame = new MainFrame(menuPrincipal); 
            mainFrame.setVisible(true);
            this.setVisible(false); 
        } else if (e.getSource() == btnConsultas) {
            JFrame menuPrincipal = this; 
            FrameTablaDeRegistros frameTabla = new FrameTablaDeRegistros(menuPrincipal); 
            frameTabla.setVisible(true);
            this.setVisible(false);
        }else if (e.getSource() == btnProcedimientos) {
        	   JFrame menuPrincipal = this; 
               FrameTablaDeProcedimientos frameTabla2 = new FrameTablaDeProcedimientos(menuPrincipal);
               frameTabla2.setVisible(true);
        }else if (e.getSource() == btnSalir) {
            System.exit(0); 
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
