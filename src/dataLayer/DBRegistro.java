package dataLayer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;

public class DBRegistro extends DBConexion {

	public static Registro DBLeeRegistro(int id) throws Exception {
		Registro reg = new Registro();
		Connection con = GetConnection3();
		String Select = "SELECT * FROM PERSONAS WHERE codigo_persona = " + id;
		PreparedStatement st = con.prepareStatement(Select);
		ResultSet rs = st.executeQuery();
		if (!rs.wasNull()) {
			rs.next();
			reg.setId(rs.getInt(1));
			reg.setNombre(rs.getString(2));
			reg.setApellidoPaterno(rs.getString(3));
			reg.setApellidoMaterno(rs.getString(4));
			reg.setTelefono(rs.getString(5));
			reg.setCalle(rs.getString(6));
			reg.setColonia(rs.getString(7));
			reg.setNumero(rs.getInt(8));
			reg.setBorrado(rs.getInt(9) == 1);
		}
		con.close();
		rs.close();
		return reg;
	}


    public static void DBInsert(Registro reg) throws Exception {
        Connection con = GetConnection3();
        String Select = "INSERT INTO PERSONAS (codigo_persona, primer_nombre, apellido_paterno, apellido_materno, telefono, calle, colonia, numero, activo) "
                + "VALUES (" + reg.getId() + ", '" + reg.getNombre() + "', '" + reg.getApellidoPaterno() + "', '" 
                + reg.getApellidoMaterno() + "', '" + reg.getTelefono() + "', '" + reg.getCalle() + "', '"
                + reg.getColonia() + "', " + reg.getNumero() + ", " + (reg.isActivo() ? 0 : 1) + ")";
        PreparedStatement st = con.prepareStatement(Select);
        st.executeUpdate();
        con.close();
    }

    public static void DBUpdate(Registro reg) throws Exception {
        Connection con = GetConnection3();
        String Select = "UPDATE PERSONAS SET "
                + "primer_nombre = '" + reg.getNombre()
                + "', apellido_paterno = '" + reg.getApellidoPaterno()
                + "', apellido_materno = '" + reg.getApellidoMaterno()
                + "', telefono = '" + reg.getTelefono()
                + "', calle = '" + reg.getCalle()
                + "', colonia = '" + reg.getColonia()
                + "', numero = " + reg.getNumero()
                + ", activo = " + (reg.isActivo() ? 0 : 1)
                + " WHERE codigo_persona = " + reg.getId();
        PreparedStatement st = con.prepareStatement(Select);
        st.executeUpdate();
        con.close();
    }

    public static void DBDeleteFisico(int id) throws Exception {
        Connection con = GetConnection3();
        String Select = "DELETE FROM PERSONAS WHERE codigo_persona = " + id;
        PreparedStatement st = con.prepareStatement(Select);
        st.executeUpdate();
        con.close();
    }

    public static void DBDeleteLogico(int id, boolean activo) throws Exception {
        Connection con = GetConnection3();
        String Select = "UPDATE PERSONAS SET "
                + "activo = " + (activo ? 0 : 1)
                + " WHERE codigo_persona = " + id;
        PreparedStatement st = con.prepareStatement(Select);
        st.executeUpdate();
        con.close();
    }

    public static int SiguienteId() throws Exception {
        int folio = Integer.MAX_VALUE;
        Connection con = GetConnection3();
        String SELECT = "SELECT MAX(codigo_persona) FROM PERSONAS";
        PreparedStatement st = con.prepareStatement(SELECT);
        ResultSet rs = st.executeQuery();
        if (rs != null && rs.next()) {
            folio = rs.getInt(1) + 1;
        }
        con.close();
        return folio;
    }

    public static DefaultListModel<String> Listado() throws Exception {
        DefaultListModel<String> lista = new DefaultListModel<>();
        lista.clear();
        Connection con = GetConnection3();
        String SELECT = "SELECT codigo_persona, primer_nombre, activo FROM PERSONAS";
        PreparedStatement st = con.prepareStatement(SELECT);
        ResultSet rs = st.executeQuery();
        
        if (rs != null) {
            while (rs.next()) {
                int codigoPersona = rs.getInt("codigo_persona");
                String primerNombre = rs.getString("primer_nombre");
                boolean activo = rs.getBoolean("activo"); // true para activos, false para inactivos

                // Si está inactivo (activo == false o 0), agrega el asterisco
                String estado = !activo ? "*" : ""; 
                
                lista.addElement(codigoPersona + " " + primerNombre + " " + estado);
            }
        }
        con.close();
        return lista;
    }
    
    public static DefaultListModel<String> Listado2() throws Exception {
    	DefaultListModel<String> lista = new DefaultListModel<>();
    	lista.clear();
    	Connection con = GetConnection3();

    	// Seleccionamos todos los atributos de la tabla PERSONAS
    	String SELECT = "SELECT * FROM PERSONAS";
    	PreparedStatement st = con.prepareStatement(SELECT);
    	ResultSet rs = st.executeQuery();

    	if (rs != null) {
    	    while (rs.next()) {
    	        // Obtenemos todos los campos de la fila
    	        int codigoPersona = rs.getInt("codigo_persona");
    	        String primerNombre = rs.getString("primer_nombre");
    	        String apellidoPaterno = rs.getString("apellido_paterno");
    	        String apellidoMaterno = rs.getString("apellido_materno");
    	        String telefono = rs.getString("telefono");
    	        String calle = rs.getString("calle");
    	        String colonia = rs.getString("colonia");
    	        int numero = rs.getInt("numero");
    	        boolean activo = rs.getInt("activo") == 1; // 1 para activo, 0 para inactivo

    	        // Determinamos si la persona está activa o inactiva
    	        String estado = activo ? "Activo" : "Inactivo";

    	        // Formateamos la cadena con todos los atributos de manera más legible,
    	        // con espacios antes y después del '|'
    	        String listaElemento = String.format(
    	            "%d | %s %s %s | %s | %s, %s | %d | Estado: %s", 
    	            codigoPersona, 
    	            primerNombre, 
    	            apellidoPaterno, 
    	            apellidoMaterno, 
    	            telefono, 
    	            calle, 
    	            colonia, 
    	            numero, 
    	            estado
    	        ).replace(" |", " | ")  // Añadir espacio después del '|'
    	          .replace("| ", " | "); // Añadir espacio antes del '|'

    	        // Añadimos el elemento formateado a la lista
    	        lista.addElement(listaElemento);

    	        // Imprime una línea vacía para separar visualmente los registros
    	        System.out.println(); // Línea en blanco para separar los registros en la consola
    	    }
    	}
    	con.close();
    	return lista;

      
    }

    public static DefaultListModel<String> Listado3() throws Exception {
        DefaultListModel<String> lista = new DefaultListModel<>();
        lista.clear();
        Connection con = GetConnection3();

        // Consulta para obtener los nombres de los procedimientos almacenados
        String SELECT = "SELECT name AS ProcedureName FROM sys.procedures";

        PreparedStatement st = con.prepareStatement(SELECT);
        ResultSet rs = st.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                // Obtenemos el nombre del procedimiento
                String procedureName = rs.getString("ProcedureName");
                // Añadimos el nombre a la lista
                lista.addElement(procedureName);
            }
        }
        con.close();
        return lista;
    }
    
    public static void DBEjecutarProcedimiento(String nombreProcedimiento) throws Exception {
        // Validar que el nombre del procedimiento no esté vacío o nulo
        if (nombreProcedimiento == null || nombreProcedimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del procedimiento almacenado no puede estar vacío o nulo.");
        }

        Connection con = GetConnection3(); // Obtener la conexión
        try {
            // Crear el llamado al procedimiento almacenado
            String call = "{ CALL " + nombreProcedimiento + " }";
            CallableStatement cs = con.prepareCall(call);

            // Ejecutar el procedimiento almacenado
            cs.execute();

            // Cerrar el CallableStatement
            cs.close();
        } catch (Exception e) {
            // Relanzar la excepción con un mensaje personalizado
            throw new Exception("Error al ejecutar el procedimiento almacenado '" + nombreProcedimiento + "': " + e.getMessage(), e);
        } finally {
            // Asegurarse de cerrar la conexión
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
    }







}
