package proyecto;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.*;
import java.util.*;

public class Registro extends JFrame implements ActionListener
{
	private Boton btnRegistrarse,btnLimpiar,btnRegresar;
	private JPanelConFondo panel;
	private JTextField txtNombre,txtApePaterno,txtApeMaterno,txtUsuario,txtContraseña;
	private JLabel e1,e2,e3,e4,e5,e6;
	private Connection conexion;
	private Vector<String> membresias;
	private JComboBox cbMembresias;
	
	public Registro(Connection conexion) 
	{
		super("Registrarse");
		this.conexion=conexion;
		hazInterfaz();
		hazEsuchadores();
	}

	public void cargar()
	{
		membresias=new Vector<String>(); 
		ResultSet rs=null;
		Statement cmd=null;
		
		try 
		{
			cmd=conexion.createStatement();
			rs=cmd.executeQuery("select nombre from membresias order by id");
			while(rs.next())
			{
				membresias.add(rs.getString("nombre"));
			}
			cbMembresias=new JComboBox(membresias.toArray());
			/*for(int i=0;i<membresias.size();i++)
			{
				System.out.println(membresias.elementAt(i));
				cbMembresias.add(new JMenuItem(membresias.elementAt(i)));	
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void hazInterfaz() 
	{
		setLayout(null);
		cargar();
		txtNombre=new JTextField();
		txtApePaterno=new JTextField();
		txtApeMaterno=new JTextField();
		txtUsuario=new JTextField();
		txtContraseña=new JTextField();
		e1=new JLabel("Nombre");
		e2=new JLabel("Apellido Paterno");
		e3=new JLabel("Apellido Materno");
		e4=new JLabel("Usuario");
		e5=new JLabel("Contraseña");
		e6=new JLabel("Membresia");
		btnRegistrarse=new Boton("Guardar");
		btnLimpiar=new Boton("Limpiar");
		btnRegresar=new Boton("Atrás");
		panel=new JPanelConFondo();
		panel.setBounds(100,50,300,100);
		e1.setBounds(70,200,100,80);
		e2.setBounds(70,250,100,80);
		e3.setBounds(70,300,100,80);
		e4.setBounds(70,350,100,80);
		e5.setBounds(70,400,100,80);
		e6.setBounds(70,450,100,80);
		txtNombre.setBounds(200,225,200,30);
		txtApePaterno.setBounds(200,275,200,30);
		txtApeMaterno.setBounds(200,325,200,30);
		txtUsuario.setBounds(200,375,200,30);
		txtContraseña.setBounds(200,425,200,30);
		cbMembresias.setBounds(200,475,200,30);
		btnRegresar.setBounds(70,550,120,50);
		btnLimpiar.setBounds(200,550,120,50);
		btnRegistrarse.setBounds(330,550,120,50);
		add(panel);
		add(e1);
		add(e2);
		add(e3);
		add(e4);
		add(e5);
		add(e6);
		add(txtNombre);
		add(txtApePaterno);
		add(txtApeMaterno);
		add(txtUsuario);
		add(txtContraseña);
		add(btnRegresar);
		add(btnLimpiar);
		add(btnRegistrarse);
		add(cbMembresias);
		getContentPane().setBackground(Color.WHITE);
		setSize(500,700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private void hazEsuchadores() 
	{
		btnRegistrarse.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnRegresar.addActionListener(this);
		txtNombre.addActionListener(this);
		txtApePaterno.addActionListener(this);
		txtApeMaterno.addActionListener(this);
		txtUsuario.addActionListener(this);
		txtContraseña.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==txtNombre||e.getSource()==txtApePaterno||e.getSource()==txtApeMaterno||e.getSource()==txtUsuario||e.getSource()==txtContraseña)
		{
			JTextField j;
			j=(JTextField)e.getSource();
			j.nextFocus();
			return;
		}
		if (e.getSource()==txtContraseña)
		{
			guardar();
			return;
		}
		if (e.getSource()==btnRegistrarse)
		{
			guardar();
			return;
		}
		if (e.getSource()==btnLimpiar)
		{
			txtNombre.setText("");
			txtApePaterno.setText("");
			txtApeMaterno.setText("");
			txtUsuario.setText("");
			txtContraseña.setText("");
			return;
		}
		if (e.getSource()==btnRegresar)
		{
			dispose();
			new Inicio();
		}
	}

	private void guardar() 
	{
		Cliente cte=new Cliente();
		cte.setNombre(txtNombre.getText().trim());
		cte.setApePaterno(txtApePaterno.getText().trim());
		cte.setApeMaterno(txtApeMaterno.getText().trim());
		cte.setUsuario(txtUsuario.getText().trim());
		cte.setContraseña(txtContraseña.getText().trim());
		cte.setMembresia(cbMembresias.getSelectedIndex()+1);
		PreparedStatement cmd;
		ResultSet rs;
		try {
			cmd=conexion.prepareStatement("select cod_cte from clientes where usuario = ?");
			cmd.setString(1, cte.getUsuario());
			rs=cmd.executeQuery();
			if(rs.first())
			{
				JOptionPane.showMessageDialog(null, "Este usuario ya está registrado, utiliza otro nombre de usuario", "VideoClub", JOptionPane.ERROR_MESSAGE);
				return;
			}
			cmd=conexion.prepareStatement("insert into clientes(nombre,ap_paterno,ap_materno,membresia,usuario,contraseña) values(?,?,?,?,?,?)");
			cmd.setString(1, cte.getNombre());
			cmd.setString(2, cte.getApePaterno());
			cmd.setString(3, cte.getApeMaterno());
			cmd.setInt(4, cte.getMembresia());
			cmd.setString(5, cte.getUsuario());
			cmd.setString(6, cte.getContraseña());
			cmd.executeUpdate();
			JOptionPane.showMessageDialog(null, "Registro Exitoso", "VideoClub", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
