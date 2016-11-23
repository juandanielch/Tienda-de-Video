package proyecto;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;

public class Ingreso extends JFrame implements ActionListener 
{
	private Boton btnRegresar,btnIngresar;
	private JTextField txtUsuario,txtContrase�a;
	private JLabel e1,e2;
	private Connection conexion;
		
	public Ingreso(Connection conexion)
	{
		super("Ingresar");
		this.conexion=conexion;
		hazInterfaz();
		hazEsuchadores();
	}	

	private void hazConexion()
	{
		conexion=ConexionDB.GetConnection();
	}
	
	private void hazInterfaz() 
	{
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		e1=new JLabel("Usuario");
		e2=new JLabel("Contrase�a");
		txtUsuario=new JTextField();
		txtContrase�a=new JTextField();
		btnRegresar=new Boton("Atr�s");
		btnIngresar=new Boton("Ingresar");
		e1.setBounds(50,50,100,20);
		e2.setBounds(50,100,100,20);
		txtUsuario.setBounds(150,45,200,30);
		txtContrase�a.setBounds(150,95,200,30);
		btnRegresar.setBounds(80,150,120,40);
		btnIngresar.setBounds(230,150,120,40);
		add(e1);
		add(e2);
		add(txtUsuario);
		add(txtContrase�a);
		add(btnRegresar);
		add(btnIngresar);
		setSize(400,250);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void hazEsuchadores() 
	{
		btnRegresar.addActionListener(this);
		btnIngresar.addActionListener(this);
		txtUsuario.addActionListener(this);
		txtContrase�a.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==txtUsuario)
		{
			txtUsuario.nextFocus();
		}
		if (e.getSource()==btnRegresar)
		{
			dispose();
			new Inicio();
		}
		if (e.getSource()==btnIngresar||e.getSource()==txtContrase�a)
		{
			if (txtUsuario.getText().trim().equals("")||txtContrase�a.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "VideoClub", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			ResultSet rs=null;
			boolean hayFilas=false;
			try 
			{
				PreparedStatement cmd=conexion.prepareStatement("select usuario from clientes where usuario = ? and contrase�a = ?");
				cmd.setString(1, txtUsuario.getText());
				cmd.setString(2, txtContrase�a.getText());
				rs=cmd.executeQuery();
				hayFilas=rs.first();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (!hayFilas)
			{
				JOptionPane.showMessageDialog(null, "Usuario y/o Contrase�a incorrecto(s)", "VideoClub", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			dispose();
			new Menu(conexion);
		}
	}

}
