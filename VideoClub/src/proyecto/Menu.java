package proyecto;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.sql.*;

public class Menu extends JFrame implements ActionListener 
{
	private JPanelConFondo panel;
	private JPanel p1;
	private Vector<Video> videos;
	private Vector<Boton> btnsmas;
	private Vector<Boton> btnsmenos;
	private Vector<JTextField> txtsCant;
	private Connection conexion;
	private JPanel p;
	//private ImageIcon imagen=new ImageIcon("C://Users\TOSHIBA/Documents/Eclipse/VideoClub/imagenes/Peliculas");
	private JScrollPane scroll;
	
	public Menu(Connection conexion)	
	{
		super("Menú Principal");
		this.conexion=conexion;
		hazInterfaz();
		hazEscuchadores();
	}

	private void hazInterfaz() 
	{
		setLayout(null);
		p=new JPanel();
		scroll=new JScrollPane();
		p.setLayout(null);
		txtsCant=new Vector<JTextField>();
		btnsmas=new Vector<Boton>();
		btnsmenos=new Vector<Boton>();
		panel=new JPanelConFondo();
		videos=new Vector<Video>();
		cargarVideos();
		setSize(900,700);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void cargarVideos() 
	{
		Statement cmd;
		ResultSet rs;
		try {
			cmd=conexion.createStatement();
			rs=cmd.executeQuery("select id,nombre,precio,tipo from videos");
			while(rs.next())
			{
				videos.add(new Video(rs.getInt("id"),rs.getString("nombre"),rs.getDouble("precio"),rs.getInt("tipo")));
			}
			int ximg=20,yimg=100;
			int x=20,y=300;
			int c=0;
			
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<5;j++)
				{
				 JPanelConFondo img=new JPanelConFondo(new ImageIcon("C://Users/TOSHIBA/Documents/Eclipse/VideoClub/imagenes/Peliculas/"+(c+1)+".png"));
				 img.setBounds(ximg,yimg,150,200);
				 txtsCant.add(new JTextField());
				 txtsCant.elementAt(c).setText("0");
				 txtsCant.elementAt(c).setBounds(x,y,50,30);
				 txtsCant.elementAt(c).setEditable(false);
				 txtsCant.elementAt(c).setHorizontalAlignment(JTextField.CENTER);
				 btnsmas.add(new Boton("+"));
				 btnsmas.elementAt(c).setBounds(x+50,y,50,30);
				 btnsmenos.add(new Boton("-"));
				 btnsmenos.elementAt(c).setBounds(x+100,y,50,30);
				 add(img);
				 add(txtsCant.elementAt(c));
				 add(btnsmas.elementAt(c));
				 add(btnsmenos.elementAt(c));
				 c++;
				 ximg+=170;
				 x+=170;
				}
			ximg=20;
			yimg+=250;
			x=20;
			y+=250;
 
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void hazEscuchadores() 
	{
		for(int i=0;i<btnsmas.size();i++)
		{
			btnsmas.elementAt(i).addActionListener(this);
			btnsmenos.elementAt(i).addActionListener(this);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int index=-1;
		int i=-1;
		JButton btn=(JButton)e.getSource();
		
		if(btn.getText().equals("+"))
		{
			System.out.println(btnsmas.size());
			while(i<btnsmas.size())
			{
				i++;
				if(e.getSource()==btnsmas.elementAt(i));
				{
					System.out.println(i);
					index=i;
					break;
				}
				
			}
			txtsCant.elementAt(index).setText((Integer.parseInt(txtsCant.elementAt(index).getText())+1)+"");
			return;
		}
		i=-1;
		if(btn.getText().equals("-"))
		{
			btnsmenos.size();
			while(i<btnsmenos.size())
			{
				i++;
				if(e.getSource()==btnsmenos.elementAt(i));
				{
					index=i;
					break;
				}
			}
			JTextField ct=txtsCant.elementAt(index);
			if (Integer.parseInt(ct.getText())>0)
				ct.setText((Integer.parseInt(ct.getText())-1)+"");
		}
	}
}	