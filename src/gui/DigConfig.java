package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dataLayer.Config;

public class DigConfig extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel lblTitulo, lblServidor, lblBaseDeDatos, lblUsuario, lblContra;
    private JTextField txtServidor, txtBaseDeDatos, txtUsuario;
    private JPasswordField txtContra;
    private JButton btnAceptar, btnCancelar;

    public DigConfig(JFrame marco, boolean nuevo) {
        super(marco, "Iniciar Sesión");
        setModal(true);
        setSize(450, 550); // Tamaño ajustado
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();

        if (nuevo) {
            CargaConfiguracion();
        }
    }

    private void initComponents() {
        // Configurar diseño principal
        setLayout(new BorderLayout());
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Título
        lblTitulo = new JLabel("Conexión a la Base de Datos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Servidor
        lblServidor = createLabel("Servidor:");
        txtServidor = createTextField();
        addComponent(panelCentral, lblServidor, txtServidor, gbc, 0);

        // Base de Datos
        lblBaseDeDatos = createLabel("Base de Datos:");
        txtBaseDeDatos = createTextField();
        addComponent(panelCentral, lblBaseDeDatos, txtBaseDeDatos, gbc, 1);

        // Usuario
        lblUsuario = createLabel("Usuario:");
        txtUsuario = createTextField();
        addComponent(panelCentral, lblUsuario, txtUsuario, gbc, 2);

        // Contraseña
        lblContra = createLabel("Contraseña:");
        txtContra = new JPasswordField(20);
        txtContra.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContra.addActionListener(e -> moveToNextField());
        addComponent(panelCentral, lblContra, txtContra, gbc, 3);

        // Añadir panel central
        add(panelCentral, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);

        btnAceptar = createButton("Aceptar", new Color(0, 102, 204), Color.WHITE);
        btnCancelar = createButton("Cancelar", Color.LIGHT_GRAY, Color.BLACK);

        btnAceptar.addActionListener(this);
        btnCancelar.addActionListener(this);

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Añadir panel de botones
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.addActionListener(e -> moveToNextField());
        return textField;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private void addComponent(JPanel panel, JComponent label, JComponent field, GridBagConstraints gbc, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(field, gbc);
    }

    private void moveToNextField() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == btnAceptar) {
            GrabaConfiguracion();
            dispose();
        }

        if (evento.getSource() == btnCancelar) {
            dispose();
        }
    }

    private void CargaConfiguracion() {
        Config.CargaConfiguracion();
        txtServidor.setText(Config.getServidor());
        txtBaseDeDatos.setText(Config.getDB());
        txtUsuario.setText(Config.getUsuarioDB());
        txtContra.setText(Config.getPasswordDB());
    }

    private void GrabaConfiguracion() {
        Config.setServidor(txtServidor.getText());
        Config.setDB(txtBaseDeDatos.getText());
        Config.setUsuarioDB(txtUsuario.getText());
        Config.setPasswordDB(new String(txtContra.getPassword()));
        Config.GrabaConfiguracion();
    }
}
