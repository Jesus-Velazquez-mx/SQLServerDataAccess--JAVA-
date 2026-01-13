package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import componentes.JTextTelefono;
import dataLayer.DBRegistro;
import dataLayer.Registro;

public class MainFrame extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private JButton btnAgregar, btnModificar, btnDelFisico, btnDelLogico, btnConsultar, btnSalir;
    private boolean regNuevo;
    private JList<String> lstLista;
    private JLabel lblStatus;
    private JLabel idLabel, nombreLabel, apellidoPaternoLabel, apellidoMaternoLabel, telefonoLabel, calleLabel, coloniaLabel, numeroLabel;
    private JTextField idField, nombreField, apellidoPaternoField, apellidoMaternoField, calleField, coloniaField, numeroField;
    private JTextTelefono telefonoField;

	private JFrame menuPrincipal;

    public MainFrame(JFrame menuPrincipal) {
        super("Gestión de Registros - Clínica");
        setSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        InitComponents();
        ConfiguraAtajos();
        ActivaTextFields(false);
        ActivaTextFields(false);
        OcultaBotones("100001");
        regNuevo = false;
        this.menuPrincipal = menuPrincipal;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                idField.requestFocus();
            }
        });        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrincipal.setVisible(true);
            }
        });
    }

    private void InitComponents() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 123, 255));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel titulo = new JLabel("Gestión de Registros de Pacientes", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(titulo, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(245, 245, 245));
        panelInferior.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel soporte = new JLabel("Soporte Técnico: soporte@clinica.com", JLabel.CENTER);
        soporte.setForeground(Color.GRAY);
        soporte.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInferior.add(soporte, BorderLayout.CENTER);

        JToolBar tbHerramientas = new JToolBar(JToolBar.VERTICAL);
        tbHerramientas.setFloatable(false);
        tbHerramientas.setBackground(new Color(240, 240, 240));

        btnAgregar = CrearBoton("Nuevo(F1)", "F1");
        btnModificar = CrearBoton("Modificar(F2)", "F2");
        btnDelFisico = CrearBoton("Eliminar Físico(F3)", "F3");
        btnDelLogico = CrearBoton("Borrar/Desborrar(F4)", "F4");
        btnConsultar = CrearBoton("Buscar(F5)", "F5");
        btnSalir = CrearBoton("Salir(F6)", "F6");

        tbHerramientas.add(btnAgregar);
        tbHerramientas.addSeparator();
        tbHerramientas.add(btnModificar);
        tbHerramientas.addSeparator();
        tbHerramientas.add(btnDelFisico);
        tbHerramientas.addSeparator();
        tbHerramientas.add(btnDelLogico);
        tbHerramientas.addSeparator();
        tbHerramientas.add(btnConsultar);
        tbHerramientas.addSeparator();
        tbHerramientas.add(btnSalir);

        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font etiquetaFuente = new Font("Arial", Font.BOLD, 14);
        Dimension campoDimensiones = new Dimension(300, 30);

        idLabel = CrearEtiqueta("ID:", etiquetaFuente);
        idField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 0, idLabel, idField);

        nombreLabel = CrearEtiqueta("Nombre:", etiquetaFuente);
        nombreField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 1, nombreLabel, nombreField);

        apellidoPaternoLabel = CrearEtiqueta("Apellido Paterno:", etiquetaFuente);
        apellidoPaternoField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 2, apellidoPaternoLabel, apellidoPaternoField);

        apellidoMaternoLabel = CrearEtiqueta("Apellido Materno:", etiquetaFuente);
        apellidoMaternoField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 3, apellidoMaternoLabel, apellidoMaternoField);

        telefonoLabel = CrearEtiqueta("Teléfono:", etiquetaFuente);
        telefonoField = CrearCampoTexto2(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 4, telefonoLabel, telefonoField);

        calleLabel = CrearEtiqueta("Calle:", etiquetaFuente);
        calleField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 5, calleLabel, calleField);

        coloniaLabel = CrearEtiqueta("Colonia:", etiquetaFuente);
        coloniaField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 6, coloniaLabel, coloniaField);

        numeroLabel = CrearEtiqueta("Número:", etiquetaFuente);
        numeroField = CrearCampoTexto(campoDimensiones);
        AgregarCampo(pnlCentro, gbc, 0, 7, numeroLabel, numeroField);
        
        lblStatus = CrearEtiqueta("", etiquetaFuente);
		
        AgregarCampo(pnlCentro, gbc, 0, 8, lblStatus);

        JPanel pnlOeste = new JPanel(new BorderLayout());
		lstLista = new JList<String>();
 
		lstLista.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!lstLista.isSelectionEmpty()) {
					try {
						Registro reg= DBRegistro.DBLeeRegistro(Integer.parseInt(
								lstLista.getSelectedValue().toString().substring(0, 
										lstLista.getSelectedValue().toString().indexOf(' '))));
						LlenaCampos(reg);
						OcultaBotones(!reg.isActivo()?"001111":"011111");
						btnConsultar.setText("Cancelar(F5)");
						idField.requestFocus();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, 
								"Registro no encontrado", "Aviso del sistema",JOptionPane.ERROR_MESSAGE);

					}
					lstLista.requestFocus();
				}
			}
		});
		lstLista.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!lstLista.isSelectionEmpty()) {
					try {
						Registro reg= DBRegistro.DBLeeRegistro(Integer.parseInt(
								lstLista.getSelectedValue().toString().substring(0, 
										lstLista.getSelectedValue().toString().indexOf(' '))));
						LlenaCampos(reg);
						OcultaBotones(!reg.isActivo()?"001111":"011111");
						btnConsultar.setText("Cancelar(F5)");
						idField.requestFocus();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, 
								"Registro no encontrado", "Aviso del sistema",JOptionPane.ERROR_MESSAGE);
					}
					lstLista.requestFocus();
				}
			}
		});
		LlenaListado(lstLista);
		
		JScrollPane jspLista = new JScrollPane();
		jspLista.setMinimumSize(new Dimension(150, 150));
		jspLista.setPreferredSize(new Dimension(200, 150));
		jspLista.setMaximumSize(new Dimension(250, 150));		
		jspLista.setViewportView(lstLista);
		
		pnlOeste.add(jspLista);
		
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.SOUTH);
        add(tbHerramientas, BorderLayout.EAST);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlOeste, BorderLayout.WEST);

        LlenaListado(lstLista);
    }

    private JButton CrearBoton(String texto, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setToolTipText(tooltip);
        boton.setBackground(new Color(0, 51, 102));
        boton.setForeground(Color.WHITE); 
        boton.setFont(new Font("Arial", Font.BOLD, 14)); 
        boton.setFocusPainted(false); 
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        boton.addActionListener(this);
        return boton;
    }

    private JLabel CrearEtiqueta(String texto, Font fuente) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        return etiqueta;
    }

    private JTextField CrearCampoTexto(Dimension dimensiones) {
        JTextField campo = new JTextField();
        campo.setPreferredSize(dimensiones);
        campo.addKeyListener(this);
        return campo;
    }
    private JTextTelefono CrearCampoTexto2(Dimension dimensiones) {
    	JTextTelefono campo = new JTextTelefono();
        campo.setPreferredSize(dimensiones);
        campo.addKeyListener(this);
        return campo;
    }


    private void AgregarCampo(JPanel panel, GridBagConstraints gbc, int x, int y, JLabel etiqueta, JTextField campo) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(etiqueta, gbc);
        gbc.gridx = x + 1;
        panel.add(campo, gbc);
    }
    
    private void AgregarCampo(JPanel panel, GridBagConstraints gbc, int x, int y, JLabel etiqueta) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(etiqueta, gbc);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnAgregar) {
			if (btnAgregar.getText().equalsIgnoreCase("Nuevo(F1)")) {
				ActivaTextFields(true);
		    	nombreField.requestFocus(); 
				btnAgregar.setText("Guardar(F1)");
				btnModificar.setText("Cancelar(F2)");
				OcultaBotones("110000");
				try {
					idField.setText(String.valueOf(DBRegistro.SiguienteId()));
				} catch(Exception error) {
					idField.setText("0");
				}
				idField.setEditable(false);
				regNuevo=true;
			}
			else {
				try {
				    if (regNuevo) {  
				        DBRegistro.DBInsert(new Registro(
				            Integer.parseInt(idField.getText()), 
				            nombreField.getText(),               
				            apellidoPaternoField.getText(),       
				            apellidoMaternoField.getText(),    
				            telefonoField.getText(),            
				            calleField.getText(),            
				            coloniaField.getText(),           
				            Integer.parseInt(numeroField.getText()),  
				            false                               
				        ));
				    } else {  
				        DBRegistro.DBUpdate(new Registro(
				            Integer.parseInt(idField.getText()),  
				            nombreField.getText(),                
				            apellidoPaternoField.getText(),      
				            apellidoMaternoField.getText(),    
				            telefonoField.getText(),         
				            calleField.getText(),               
				            coloniaField.getText(),             
				            Integer.parseInt(numeroField.getText()),  
				            false                                  
				        ));
				    }
				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, ex, "Error al agregar registro: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
					idField.requestFocus();
				}

				LlenaListado(lstLista);
				LimpiaTextFields();
				ActivaTextFields(false);
				idField.setEditable(true);
				idField.requestFocus();
				btnAgregar.setText("Nuevo(F1)");
				btnModificar.setText("Modificar(F2)");
				btnConsultar.setText("Buscar(F5)");
				OcultaBotones("100001");
				
				regNuevo=false;
			}


		}

		if (e.getSource()==btnModificar) {
			if (btnModificar.getText().equalsIgnoreCase("Cancelar(F2)")) {
				LimpiaTextFields();
				ActivaTextFields(false);
				idField.setEditable(true);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				        idField.requestFocus();
				    }
				});
				btnAgregar.setText("Nuevo(F1)");
				btnModificar.setText("Modificar(F2)");
				btnConsultar.setText("Buscar(F5)"); 
				lstLista.clearSelection();
				OcultaBotones("100001");
			}
			else {  
		    	nombreField.requestFocus(); 
				ActivaTextFields(true);
		    	nombreField.requestFocus(); 
				idField.setEditable(false);
				btnAgregar.setText("Guardar(F1)");
				btnModificar.setText("Cancelar(F2)");
				OcultaBotones("110000");
				regNuevo=false;
			}
		}

		if (e.getSource()==btnDelFisico) {
			try {
				DBRegistro.DBDeleteFisico(Integer.parseInt(idField.getText()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error al borrar registro: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
				idField.requestFocus();
			}
			LlenaListado(lstLista);
			LimpiaTextFields();
			ActivaTextFields(false);
			idField.setEditable(true);
			idField.requestFocus();
			btnAgregar.setText("Nuevo(F1)");
			btnModificar.setText("Modificar(F2)");
			btnConsultar.setText("Buscar(F5)");
			OcultaBotones("100001");
		}

		if (e.getSource()==btnDelLogico) {
			try {
				DBRegistro.DBDeleteLogico(Integer.parseInt(idField.getText()),
						lblStatus.getText().equalsIgnoreCase("Registro Activo"));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "Error al borrar registro: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
				idField.requestFocus();
			}
			LlenaListado(lstLista);
			LimpiaTextFields();
			ActivaTextFields(false);
			idField.setEditable(true);
			idField.requestFocus();
			btnAgregar.setText("Nuevo(F1)");
			btnModificar.setText("Modificar(F2)");
			btnConsultar.setText("Buscar(F5)");
			OcultaBotones("100001");
		}

		if (e.getSource()==btnConsultar) {
			if (btnConsultar.getText().equals("Buscar(F5)")) {
				try {
					Registro reg= DBRegistro.DBLeeRegistro(Integer.parseInt(idField.getText()));
					idField.transferFocus();
					LlenaCampos(reg);
					OcultaBotones(reg.isActivo()?"011111":"001111");
					btnConsultar.setText("Cancelar(F5)");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Registro no encontrado", "Aviso del sistema",JOptionPane.ERROR_MESSAGE);
					idField.requestFocus();
				}
			}
			else {
				LimpiaTextFields();
				OcultaBotones("100001");
				btnConsultar.setText("Buscar(F5)");
				idField.requestFocus();
			}
		}

		if (e.getSource()==btnSalir) {
			 menuPrincipal.setVisible(true); 
             dispose(); 
		}

		

	}
    public void LlenaCampos(Registro reg) {
	    idField.setText(String.valueOf(reg.getId()));
	    nombreField.setText(reg.getNombre());
	    apellidoPaternoField.setText(reg.getApellidoPaterno());
	    apellidoMaternoField.setText(reg.getApellidoMaterno());
	    telefonoField.setText(reg.getTelefono());
	    calleField.setText(reg.getCalle());
	    coloniaField.setText(reg.getColonia());
	    numeroField.setText(String.valueOf(reg.getNumero()));
		lblStatus.setText(!reg.isActivo()?"Registro Inactivo":"Registro Activo");


	
	}

	public void LimpiaTextFields() {
	    idField.setText("");
	    nombreField.setText("");
	    apellidoPaternoField.setText("");
	    apellidoMaternoField.setText("");
	    telefonoField.setText("");
	    calleField.setText("");
	    coloniaField.setText("");
	    numeroField.setText("");
	}


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Component nextField = getNextComponent((JTextField) e.getSource());
            if (nextField != null) {
                nextField.requestFocus();
            }
        }
    }
    private Component getNextComponent(JTextField currentField) {
        if (currentField == idField) {
            return nombreField;
        } else if (currentField == nombreField) {
            return apellidoPaternoField;
        } else if (currentField == apellidoPaternoField) {
            return apellidoMaternoField;
        } else if (currentField == apellidoMaternoField) {
            return telefonoField;
        } else if (currentField == telefonoField) {
            return calleField;
        } else if (currentField == calleField) {
            return coloniaField;
        } else if (currentField == coloniaField) {
            return numeroField;
        }
        return null;
    }

    @Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource()==idField) {
			if(idField.getText().toString().length()==0)
				OcultaBotones("100001");
			else
				OcultaBotones("100011");
		}
	}
    
    private void ActivaTextFields(boolean activar) {
		/*
		txtNombre.setEnabled(activar);
		txtDireccion.setEnabled(activar);
		txtTelefono.setEnabled(activar);
		txtSaldo.setEnabled(activar);
		*/
		nombreField.setEditable(activar);
		apellidoPaternoField.setEditable(activar);
		apellidoMaternoField.setEditable(activar);
		telefonoField.setEditable(activar);
		if (!activar) {
			telefonoField.setBackground (new Color (238, 238, 238));
		}else {
			telefonoField.setBackground (Color.white);

		}
		calleField.setEditable(activar);
		coloniaField.setEditable(activar);
		numeroField.setEditable(activar);
		
	}

	private void ActivaBotones(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
		btnAgregar.setEnabled(b1);
		btnModificar.setEnabled(b2);
		btnDelFisico.setEnabled(b3);
		btnDelLogico.setEnabled(b4);
		btnConsultar.setEnabled(b5);
		btnSalir.setEnabled(b6);
	}

	//"100011"
	@SuppressWarnings("unused")
	private void ActivaBotones(String cad) {
		ActivaBotones(cad.substring(0, 1).equals("1"),
				cad.substring(1, 2).equals("1"),
				cad.substring(2, 3).equals("1"),
				cad.substring(3, 4).equals("1"),
				cad.substring(4, 5).equals("1"),
				cad.substring(5, 6).equals("1"));
	}

	private void OcultaBotones(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
		btnAgregar.setVisible(b1);
		btnModificar.setVisible(b2);
		btnDelFisico.setVisible(b3);
		btnDelLogico.setVisible(b4);
		btnConsultar.setVisible(b5);
		btnSalir.setVisible(b6);
	}

	//"100011"
	private void OcultaBotones(String cad) {
		OcultaBotones(cad.substring(0, 1).equals("1"),
				cad.substring(1, 2).equals("1"),
				cad.substring(2, 3).equals("1"),
				cad.substring(3, 4).equals("1"),
				cad.substring(4, 5).equals("1"),
				cad.substring(5, 6).equals("1"));
	}
	
	public static void LlenaListado(JList <String> lista) {
		try	{
			lista.setModel(DBRegistro.Listado());
		}
		catch (Exception e)	{
			lista.setModel(new DefaultListModel<String>());	
		}	
	}
	
	private void ConfiguraAtajos() {
	    InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap actionMap = getRootPane().getActionMap();

	    inputMap.put(KeyStroke.getKeyStroke("F1"), "Agregar");
	    actionMap.put("Agregar", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnAgregar.doClick();
	        }
	    });

	    inputMap.put(KeyStroke.getKeyStroke("F2"), "Modificar");
	    actionMap.put("Modificar", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnModificar.doClick();
	        }
	    });

	    inputMap.put(KeyStroke.getKeyStroke("F3"), "EliminarFisico");
	    actionMap.put("EliminarFisico", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnDelFisico.doClick();
	        }
	    });

	    inputMap.put(KeyStroke.getKeyStroke("F4"), "EliminarLogico");
	    actionMap.put("EliminarLogico", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnDelLogico.doClick();
	        }
	    });

	    inputMap.put(KeyStroke.getKeyStroke("F5"), "Consultar");
	    actionMap.put("Consultar", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnConsultar.doClick();
	        }
	    });

	    inputMap.put(KeyStroke.getKeyStroke("F6"), "Salir");
	    actionMap.put("Salir", new AbstractAction() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            btnSalir.doClick();
	        }
	    });
	}

    
    
}
