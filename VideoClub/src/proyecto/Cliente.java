package proyecto;

public class Cliente 
{
	private String nombre,ape_paterno,ape_materno,usuario,contrase�a;
	private int membresia;
	
	public Cliente()
	{
	}
	
	public Cliente(String nombre,String ape_paterno,String ape_materno,int membresia,String usuario,String contrase�a)
	{
		this.nombre=nombre;
		this.ape_paterno=ape_paterno;
		this.ape_materno=ape_materno;
		this.membresia=membresia;
		this.usuario=usuario;
		this.contrase�a=contrase�a;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setApePaterno(String ape_paterno)
	{
		this.ape_paterno=ape_paterno;
	}
	
	public String getApePaterno()
	{
		return ape_paterno;
	}
	
	public void setApeMaterno(String ape_materno)
	{
		this.ape_materno=ape_materno;
	}
	
	public String getApeMaterno()
	{
		return ape_materno;
	}
	
	public void setMembresia(int membresia)
	{
		this.membresia=membresia;
	}
	
	public int getMembresia()
	{
		return membresia;
	}
	
	public void setUsuario(String usuario)
	{
		this.usuario=usuario;
	}
	
	public String getUsuario()
	{
		return usuario;
	}
	
	public void setContrase�a(String contrase�a)
	{
		this.contrase�a=contrase�a;
	}
	
	public String getContrase�a()
	{
		return contrase�a;
	}
}
