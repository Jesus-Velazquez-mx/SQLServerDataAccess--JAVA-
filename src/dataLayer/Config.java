package dataLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Config {
	
	private static String imagenes;
	private static String servidor;
	private static String DB;
	private static String usuarioDB;
	private static String passwordDB;

	public static boolean CargaConfiguracion() {
	    File Confi = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    boolean Existe = true;
	    String CfgFile = "Config.cfg";
	    try {
	        // Apertura del fichero y creacion de BufferedReader para poder
	        // hacer una lectura comoda (disponer del metodo readLine()).
	        Confi = new File(CfgFile);
	        if (Confi.exists()) {
	            fr = new FileReader(Confi);
	            br = new BufferedReader(fr);
	            String linea;
	            if ((linea = br.readLine()) != null)
	                if (linea.equals("Archivo de Configuracion")) {
	                    for (int i = 1; i <= 5; i++) {
	                        if ((linea = br.readLine()) != null) {
	                            switch (i) {
	                                case 1:
	                                    imagenes = linea;
	                                    break;
	                                case 2:
	                                    servidor = linea;
	                                    break;
	                                case 3:
	                                    DB = linea;
	                                    break;
	                                case 4:
	                                    usuarioDB = linea;
	                                    break;
	                                case 5:
	                                    passwordDB = linea;
	                                    break;
	                            }
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Formato del archivo de configuracion no valido",
	                                    "Error al cargar configuracion.", JOptionPane.ERROR_MESSAGE);
	                            Existe = false;
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Formato del archivo de configuracion no valido",
	                            "Error al cargar configuracion.", JOptionPane.ERROR_MESSAGE);
	                    Existe = false;
	                }
	        } else {
	            JOptionPane.showMessageDialog(null, "Archivo de configuracion no encontrado",
	                    "Error al cargar configuracion.", JOptionPane.ERROR_MESSAGE);
	            Existe = false;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e.toString(), "Error al cargar configuracion.",
	                JOptionPane.ERROR_MESSAGE);
	        Existe = false;
	    } finally {
	        // En el finally cerramos el fichero, para asegurarnos
	        // que se cierra tanto si todo va bien como si salta
	        // una excepcion.
	        try {
	            if (fr != null)
	                fr.close();
	        } catch (Exception e2) {
	            JOptionPane.showMessageDialog(null, "finally: " + e2.toString(),
	                    "Error al cargar configuracion.", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    return Existe;
	}


	public static void GrabaConfiguracion() {// String Path
		FileWriter fw = null;
		PrintWriter pw = null;
		String CfgFile = "";
		
		CfgFile = "Config.cfg";
		try {
			fw = new FileWriter(CfgFile);
			pw = new PrintWriter(fw);
			pw.println("Archivo de Configuracion");
			pw.println(imagenes);
			pw.println(servidor);
			pw.println(DB);
			pw.println(usuarioDB);
			pw.println(passwordDB);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error al grabar la configuracion.",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta una excepcion.
			try {
				if (fw != null)
					fw.close();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "finally: " + e2.toString(), 
						"Error al grabar la configuracion.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static String getImagenes() {
		return imagenes;
	}

	public static void setImagenes(String imagenes) {
		Config.imagenes = imagenes;
	}

	public static String getServidor() {
		return servidor;
	}

	public static void setServidor(String servidor) {
		Config.servidor = servidor;
	}

	public static String getDB() {
		return DB;
	}

	public static void setDB(String db) {
		Config.DB = db;
	}

	public static String getUsuarioDB() {
		return usuarioDB;
	}

	public static void setUsuarioDB(String usuarioDB) {
		Config.usuarioDB = usuarioDB;
	}

	public static String getPasswordDB() {
		return passwordDB;
	}

	public static void setPasswordDB(String passwordDB) {
		Config.passwordDB = passwordDB;
	}

   
}