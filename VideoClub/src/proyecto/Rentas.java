package proyecto;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Rentas extends JFrame implements ActionListener
{
	private Connection conexion;
	private JPanel p;
	private JScrollPane scroll;
	private DefaultTableModel modelo;
	private JTable tablaVenta;
	private int usuario;
	private Boton btnAceptar,btnCancelar;
	private JPanelConFondo logo;
	private JTextField txtTotal;
	
	public Rentas(Connection conexion,int usuario)	
	{
		super("VideoClub");
		this.conexion=conexion;
		this.usuario=usuario;
		hazInterfaz();
		hazEscuchadores();
	}
	
	private void hazInterfaz() 
	{
		setLayout(null);
		logo=new JPanelConFondo();
		logo.setBounds(30,30,200,50);
		btnAceptar=new Boton("Aceptar");
		btnAceptar.setBounds(400,30,80,30);
		btnCancelar=new Boton("Cancelar");
		btnCancelar.setBounds(490,30,100,30);
		txtTotal=new JTextField("0");
		txtTotal.setBounds(600, 30, 80, 30);
		txtTotal.setEditable(false);
		modelo=new DefaultTableModel();
		modelo.addColumn("Código");
		modelo.addColumn("Película");
		modelo.addColumn("Tipo");
		modelo.addColumn("Precio");
		modelo.addColumn("Cantidad");
		tablaVenta=new JTable(modelo);
		scroll=new JScrollPane(tablaVenta);
		scroll.setBounds(50,100,600,500);
		new JPanelConFondo();
		cargar();
		add(logo);
		add(btnAceptar);
		add(btnCancelar);
		add(txtTotal);
		add(scroll);
		setSize(700,650);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
		
	private void hazEscuchadores() 
	{
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
	}
	
	private void cargar()
	{
		txtTotal.setText("0");
		Statement cmd;
		ResultSet rs;
		String nombre,tipo;
		int id;
		double precio;
		modelo=new DefaultTableModel();
		modelo.addColumn("Código");
		modelo.addColumn("Nombre");
		modelo.addColumn("Tipo");
		modelo.addColumn("Precio");
		modelo.addColumn("Cantidad");
		tablaVenta.setModel(modelo);
		try
		{
			cmd=conexion.createStatement();
			rs=cmd.executeQuery("select v.id,v.nombre,t.nombre as tipo,v.precio from videos v inner join tipos_video t on v.tipo=t.id");
			while(rs.next())
			{
				id=rs.getInt("id");
				nombre=rs.getString("nombre");
				tipo=rs.getString("tipo");
				precio=rs.getDouble("precio");
				modelo.addRow(new Object[]{id,nombre,tipo,precio,0});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cantidad;
		double precio;
		double total=0;
		if(e.getSource()==btnAceptar)
		{
			for(int i=0;i<modelo.getRowCount()-1;i++)
			{
				cantidad=Integer.parseInt(modelo.getValueAt(i, 4).toString());
				if(cantidad<=0)
					continue;
				precio=Double.parseDouble(modelo.getValueAt(i,3).toString()) ;
				total+=(cantidad*precio);
			}
			if (total==0)
				return;
			txtTotal.setText(total+"");
			int resp=JOptionPane.showConfirmDialog(this, "Desea liquidar la venta?", "VideoClub", JOptionPane.YES_NO_OPTION);
			System.out.println(resp);
			if (resp==0)
				guardar(total);
			return;
		}
		if(e.getSource()==btnCancelar)
		{
			dispose();
			new MenuPrincipal(conexion,usuario);
		}
	}
	
	public void guardar(double totalRenta)
	{
		double total=0,precio;
		int cantidad=0;
		int cliente = 0;
		int idRenta;
		int idVideo;
			
		PreparedStatement cmd;
		ResultSet rs;
		
		try 
		{
			cmd=conexion.prepareStatement("select cod_cte from clientes where usuario = ?");
			cmd.setInt(1, usuario);
			rs=cmd.executeQuery();
			rs.first();
			cliente=rs.getInt("cod_cte");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		try 
		{
			cmd=conexion.prepareStatement("insert into rentas(fecha,cod_cte,total) select concat(CURRENT_DATE(),' ',CURRENT_TIME()),?,?");
			cmd.setInt(1, cliente);
			cmd.setDouble(2, totalRenta);
			cmd.executeUpdate();
			cmd=conexion.prepareStatement("select max(id) from rentas");
			rs=cmd.executeQuery();
			rs.first();
			idRenta=rs.getInt(1);
			cmd=conexion.prepareStatement("insert into rentas_videos(id,video,cantidad,total) values(?,?,?,?)");
			for(int i=0;i<modelo.getRowCount();i++)
			{
				cantidad=Integer.parseInt(modelo.getValueAt(i, 4).toString());
				if(cantidad<=0)
					continue;
				idVideo=Integer.parseInt(modelo.getValueAt(i, 0).toString());
				precio=Double.parseDouble(modelo.getValueAt(i, 3).toString());
				total=cantidad*precio;
				cmd.setInt(1, idRenta);
				cmd.setInt(2, idVideo);
				cmd.setInt(3, cantidad);
				cmd.setDouble(4, total);
				cmd.executeUpdate();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(this, "La información se ha guardado correctamente.", "VideoClub", JOptionPane.INFORMATION_MESSAGE);
		cargar();
	}
}	