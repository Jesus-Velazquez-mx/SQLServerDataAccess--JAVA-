package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dataLayer.DBRegistro;

public class FrameTablaDeProcedimientos extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList<String> lstLista;
    private JLabel lblDetalles; 

    public FrameTablaDeProcedimientos(JFrame menuPrincipal) {
        super("Gestión de Registros - Clínica");
        setSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        InitComponents(menuPrincipal);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrincipal.setVisible(true); 
            }
        });
    }

    private void InitComponents(JFrame menuPrincipal) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 123, 255));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel titulo = new JLabel("Manejo de Procedimientos Almacenados", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(titulo, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(245, 245, 245));
        panelInferior.setPreferredSize(new Dimension(getWidth(), 80));

        JLabel soporte = new JLabel("Soporte Técnico: soporte@clinica.com", JLabel.CENTER);
        soporte.setForeground(Color.GRAY);
        soporte.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton btnSalir = new JButton("Salir(F6)");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(0, 51, 102));
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal.setVisible(true); 
                dispose(); 
            }
        });
        
        JButton btnEjecutar = new JButton("Ejecutar(F1)");
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setBackground(new Color(0, 51, 102));
        btnEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String procedimientoSeleccionado = lstLista.getSelectedValue();

                if (procedimientoSeleccionado == null || procedimientoSeleccionado.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        FrameTablaDeProcedimientos.this,
                        "Por favor, selecciona un procedimiento de la lista.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                try {
                    DBRegistro.DBEjecutarProcedimiento(procedimientoSeleccionado);
                    JOptionPane.showMessageDialog(
                        FrameTablaDeProcedimientos.this,
                        "El procedimiento almacenado '" + procedimientoSeleccionado + "' se ejecutó correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        FrameTablaDeProcedimientos.this,
                        "Error al ejecutar el procedimiento almacenado:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        panelInferior.add(soporte, BorderLayout.CENTER);
        panelInferior.add(btnSalir, BorderLayout.EAST);
        panelInferior.add(btnEjecutar, BorderLayout.WEST);
        JPanel pnlCentro = new JPanel(new BorderLayout());

        lstLista = new JList<String>();
        lstLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !lstLista.isSelectionEmpty()) {
                    String procedimientoSeleccionado = lstLista.getSelectedValue();

                    String textoEjecucion = "EXEC " + procedimientoSeleccionado;

                    lblDetalles.setText(textoEjecucion);
                }
            }
        });


        LlenaListado(lstLista);

        JScrollPane jspLista = new JScrollPane(lstLista);
        jspLista.setMinimumSize(new Dimension(150, 150));
        jspLista.setPreferredSize(new Dimension(200, 150));
        jspLista.setMaximumSize(new Dimension(250, 150));

        lblDetalles = new JLabel("Selecciona un procedimiento almacenado.");
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        

        pnlCentro.add(jspLista, BorderLayout.CENTER);
        pnlCentro.add(lblDetalles, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.SOUTH);
        add(pnlCentro, BorderLayout.CENTER);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F6"), "SALIR");
        getRootPane().getActionMap().put("SALIR", new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                btnSalir.doClick(); 
            }
        });
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "EJECUTAR");
        getRootPane().getActionMap().put("EJECUTAR", new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                btnEjecutar.doClick(); 
            }
        });
    }

    public static void LlenaListado(JList<String> lista) {
        try {
            lista.setModel(DBRegistro.Listado3());
        } catch (Exception e) {
            lista.setModel(new DefaultListModel<String>());
        }
    }
}
