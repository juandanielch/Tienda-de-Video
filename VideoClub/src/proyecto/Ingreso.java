package proyecto;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;

public class Ingreso extends JFrame implements ActionListener 
{
	private Boton btnRegresar,btnIngresar;
	private JTextField txtUsuario,txtContraseña;
	private JLabel e1,e2;
	private Connection conexion;
	private int usuario;
	
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
		e2=new JLabel("Contraseña");
		txtUsuario=new JTextField();
		txtContraseña=new JTextField();
		btnRegresar=new Boton("Cancelar");
		btnIngresar=new Boton("Ingresar");
		e1.setBounds(50,50,100,20);
		e2.setBounds(50,100,100,20);
		txtUsuario.setBounds(150,45,200,30);
		txtContraseña.setBounds(150,95,200,30);
		btnIngresar.setBounds(80,150,120,40);
		btnRegresar.setBounds(230,150,120,40);
		
		add(e1);
		add(e2);
		add(txtUsuario);
		add(txtContraseña);
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
		txtContraseña.addActionListener(this);
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
		if (e.getSource()==btnIngresar||e.getSource()==txtContraseña)
		{
			if (txtUsuario.getText().trim().equals("")||txtContraseña.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "VideoClub", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			ResultSet rs=null;
			boolean hayFilas=false;
			try 
			{
				PreparedStatement cmd=conexion.prepareStatement("select id,usuario from usuarios where usuario = ? and clave = ?");
				cmd.setString(1, txtUsuario.getText());
				cmd.setString(2, txtContraseña.getText());
				rs=cmd.executeQuery();
				hayFilas=rs.first();
				if (!hayFilas)
				{
					JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña incorrecto(s)", "VideoClub", JOptionPane.ERROR_MESSAGE);
					return;
				}
				usuario=rs.getInt("id");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			new MenuPrincipal(conexion,usuario);
		}
	}

}
