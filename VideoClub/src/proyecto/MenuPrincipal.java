package proyecto;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.sql.*;

public class MenuPrincipal extends JFrame implements ActionListener,WindowListener 
{
	private Boton btnRentas,btnReportes,btnAtras;
	private Connection conexion;
	private int usuario;
	
	public MenuPrincipal(Connection conexion,int usuario)
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
		btnRentas=new Boton("Rentas");
		btnReportes=new Boton("Reporte de rentas");
		btnAtras=new Boton("Cancelar");
		configuracion();
		btnRentas.setBounds(50,50,150,50);
		btnReportes.setBounds(50,120,150,50);
		btnAtras.setBounds(50,190,150,50);
		add(btnRentas);
		add(btnReportes);
		add(btnAtras);
		setSize(250,300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void configuracion()
	{
		PreparedStatement cmd;
		ResultSet rs;
		String tipo="";
		try {
			cmd=conexion.prepareStatement("select tipo from usuarios where id = ?");
			cmd.setInt(1,usuario);
			rs=cmd.executeQuery();
			rs.first();
			tipo=rs.getString("tipo");
			System.out.println(tipo);
			if (tipo.equals("C"))
			{
				btnReportes.setEnabled(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void hazEscuchadores() 
	{
		btnRentas.addActionListener(this);
		btnReportes.addActionListener(this);
		btnAtras.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==btnRentas)
		{
			dispose();
			new Rentas(conexion,usuario);
			return;
		}
		if (e.getSource()==btnReportes)
		{
			dispose();
			new Reportes(conexion,usuario);
			return;
		}
		if (e.getSource()==btnAtras)
		{
			dispose();
			new Inicio();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}
