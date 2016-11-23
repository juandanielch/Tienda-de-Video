package proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inicio extends JFrame implements ActionListener
{
	private JPanel panel;
	private Boton btnIngresar;
	private Boton btnRegistrarse;
	private Image imagen;
	private Connection conexion;
	
	public Inicio()
	{
		super("VideoClub");
		hazConexion();
		hazInterfaz();
		hazEscuchadores();
	}

	private void hazConexion()
	{
		conexion=ConexionDB.GetConnection();
	}

	private void hazInterfaz() 
	{
		setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		panel=new JPanelConFondo();
		panel.setBounds(200,80,300,100);
		add(panel);
		btnIngresar=new Boton("INGRESAR");
		btnRegistrarse=new Boton("REGISTRARSE");
		btnIngresar.setBounds(250, 200, 200, 50);
		btnRegistrarse.setBounds(250, 300, 200, 50);
		add(btnIngresar);
		add(btnRegistrarse);
		setSize(700,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void hazEscuchadores() 
	{
		btnIngresar.addActionListener(this);
		btnRegistrarse.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==btnIngresar)
		{
			dispose();
			new Ingreso(conexion);
			return;
		}
		if (e.getSource()==btnRegistrarse)
		{
			dispose();
			new Registro(conexion);
		}
	}
	
	public static void main(String[] args) 
	{
		new Inicio();  
	}
}
