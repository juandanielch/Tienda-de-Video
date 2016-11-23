package proyecto;
public class Video 
{
	String nombre;
	double precio;
	int tipo,id;	
	
	public Video(int id,String nombre,double precio,int tipo)
	{
		this.id=id;
		this.nombre=nombre;
		this.precio=precio;
		this.tipo=tipo;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public int getId()
	{
		return id;
	}

	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setPrecio(double precio)
	{
		this.precio=precio;
	}
	
	public double getPrecio()
	{
		return precio;
	}
	
	public void setTipo(int tipo)
	{
		this.tipo=tipo;
	}
	
	public int getTipo()
	{
		return tipo;
	}
}
