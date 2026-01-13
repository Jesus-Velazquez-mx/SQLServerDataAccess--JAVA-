package dataLayer;

public class Registro {
	
	private int id;
	private String nombre, apellidoPaterno, apellidoMaterno, telefono, calle, colonia;
	private int numero;
	private boolean activo;
	
	
	public Registro(int id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono,
			String calle, String colonia, int numero, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.calle = calle;
		this.colonia = colonia;
		this.numero = numero;
		this.activo = activo;
	}
	

	public Registro() {
		this(0, "", "", "", "", "", "", 0, true);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getColonia() {
		return colonia;
	}


	public void setColonia(String colonia) {
		this.colonia = colonia;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public boolean isActivo() {
		return activo;
	}

	public void setBorrado(boolean activo) {
		this.activo = activo;
	}


	@Override
	public String toString() {
		return id + "|" + nombre + "|" + apellidoPaterno + "|" + apellidoMaterno + "|" + telefono
				+ "|" + calle + "|" + colonia + "|" + numero + "|" + activo;
	}
	
	
	
	
	
}
