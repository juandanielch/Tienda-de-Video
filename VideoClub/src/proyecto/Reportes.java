package proyecto;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class Reportes extends JFrame implements ActionListener, ItemListener
{
	private Connection conexion;
	private JTable tablaClientes,tablaTiposVideo,tablaMembresias;
	private JTabbedPane pestañas;
	private JPanel panelClientes,panelTiposVideo,panelMembresias;
	private Boton btnClientes,btnTiposVideo,btnMembresias;
	private Boton btnAtras,btnAtras2,btnAtras3;
	private JScrollPane scrollClientes,scrollTiposVideo,scrollMembresias;
	private DefaultTableModel modClientes,modTiposVideo,modMembresias;
	private int usuario;
	private JComboBox cbAñoClientes,cbMesClientes,cbDiaClientes,cbAñoVideos,cbMesVideos,cbDiaVideos,cbAñoMembresias,cbMesMembresias,cbDiaMembresias;
	private String[] años;
	private String meses[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	private String[] dias;
	private JLabel e1Clientes,e2Clientes,e3Clientes,e1Videos,e2Videos,e3Videos,e1Membresias,e2Membresias,e3Membresias;
	private GregorianCalendar calendario;
	private int diaHoy,mesHoy,añoHoy;
	String dia,mes,año,fecha;
	
	public Reportes(Connection conexion,int usuario)
	{
		super("Reportes de rentas");
		this.conexion=conexion;
		this.usuario=usuario;
		hazInterfaz();
		hazEscuchadores();
	}

	private void hazEscuchadores() 
	{
		btnClientes.addActionListener(this);
		btnTiposVideo.addActionListener(this);
		btnMembresias.addActionListener(this);
		btnAtras.addActionListener(this);
		btnAtras2.addActionListener(this);
		btnAtras3.addActionListener(this);
		cbDiaClientes.addItemListener(this);
		cbMesClientes.addItemListener(this);
		cbAñoClientes.addItemListener(this);
		cbDiaVideos.addItemListener(this);
		cbMesVideos.addItemListener(this);
		cbAñoVideos.addItemListener(this);
		cbDiaMembresias.addItemListener(this);
		cbMesMembresias.addItemListener(this);
		cbAñoMembresias.addItemListener(this);
	}

	private void hazInterfaz() 
	{		
		pestañas=new JTabbedPane();
		//pestaña clientes
		panelClientes=new JPanel();
		panelClientes.setLayout(null);
		modClientes=new DefaultTableModel();
		modClientes.addColumn("Cliente");
		modClientes.addColumn("Importe");
		tablaClientes=new JTable(modClientes);
		scrollClientes=new JScrollPane(tablaClientes);
		btnClientes=new Boton("Refrescar");
		btnClientes.setBounds(370,120,100,35);
		btnAtras=new Boton("Cancelar");
		btnAtras.setBounds(370, 170, 100, 35);
		scrollClientes.setBounds(50, 100, 300, 200);
		panelClientes.add(btnClientes);
		panelClientes.add(btnAtras);
		panelClientes.add(scrollClientes);
		//pestaña tipos de video
		panelTiposVideo=new JPanel();
		panelTiposVideo.setLayout(null);
		btnTiposVideo=new Boton("Refrescar");
		btnTiposVideo.setBounds(370,120,100,35);
		btnAtras2=new Boton("Cancelar");
		btnAtras2.setBounds(370, 170, 100, 35);
		modTiposVideo=new DefaultTableModel();
		modTiposVideo.addColumn("Tipo");
		modTiposVideo.addColumn("Importe");
		tablaTiposVideo=new JTable(modTiposVideo);
		scrollTiposVideo=new JScrollPane(tablaTiposVideo);
		scrollTiposVideo.setBounds(50, 100, 300, 200);
		panelTiposVideo.add(btnTiposVideo);
		panelTiposVideo.add(btnAtras2);
		panelTiposVideo.add(scrollTiposVideo);
		//pestaña membresias
		panelMembresias=new JPanel();
		panelMembresias.setLayout(null);
		btnMembresias=new Boton("Refrescar");
		btnMembresias.setBounds(370,120,100,35);
		btnAtras3=new Boton("Cancelar");
		btnAtras3.setBounds(370, 170, 100, 35);
		modMembresias=new DefaultTableModel();
		modMembresias.addColumn("Membresia");
		modMembresias.addColumn("Importe");
		tablaMembresias=new JTable(modMembresias);
		scrollMembresias=new JScrollPane(tablaMembresias);
		scrollMembresias.setBounds(50, 100, 300, 200);
		panelMembresias.add(btnMembresias);
		panelMembresias.add(btnAtras3);
		panelMembresias.add(scrollMembresias);
		calendario=new GregorianCalendar();
		calendario.getTime();
		Date fecha=calendario.getTime();
		diaHoy=fecha.getDate();
		mesHoy=fecha.getMonth();
		añoHoy=fecha.getYear()+1900;
		int año=1980;
		años=new String[51];
		dias=new String[31];
		for(int i=0;i<31;i++)
			dias[i]=(i+1)+"";
		for(int i=0;i<años.length;i++)
		{
			años[i]=año+"";
			año++;
		}
		combosClientes();
		combosTiposVideo();
		combosMembresias();
		pestañas.add("Rentas por cliente", panelClientes);
		pestañas.add("Rentas por tipo de video", panelTiposVideo);
		pestañas.add("Rentas por membresia", panelMembresias);
		add(pestañas);
		setSize(500,400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource()==btnClientes)
		{
			if ((cbDiaClientes.getSelectedIndex()+1)<10)
				dia="0"+(cbDiaClientes.getSelectedIndex()+1);
			else
				dia=(cbDiaClientes.getSelectedIndex()+1)+"";
			if ((cbMesClientes.getSelectedIndex()+1)<10)
				mes="0"+(cbMesClientes.getSelectedIndex()+1);
			else
				mes=(cbMesClientes.getSelectedIndex()+1)+"";
			año=cbAñoClientes.getSelectedItem().toString();
			fecha=año+"-"+mes+"-"+dia+" 23:59:00";
			modClientes=new DefaultTableModel();
			modClientes.addColumn("Cliente");
			modClientes.addColumn("Importe");
			tablaClientes.setModel(modClientes);
			rentasClientes();
			return;
		}
		if(e.getSource()==btnTiposVideo)
		{
			if ((cbDiaVideos.getSelectedIndex()+1)<10)
				dia="0"+(cbDiaVideos.getSelectedIndex()+1);
			else
				dia=(cbDiaVideos.getSelectedIndex()+1)+"";
			if ((cbMesVideos.getSelectedIndex()+1)<10)
				mes="0"+(cbMesVideos.getSelectedIndex()+1);
			else
				mes=(cbMesVideos.getSelectedIndex()+1)+"";
			año=cbAñoVideos.getSelectedItem().toString();
			fecha=año+"-"+mes+"-"+dia+" 23:59:00";
			modTiposVideo=new DefaultTableModel();
			modTiposVideo.addColumn("Tipo");
			modTiposVideo.addColumn("Importe");
			tablaTiposVideo.setModel(modTiposVideo);
			rentasTiposVideo();
			return;
		}
		if(e.getSource()==btnMembresias)
		{
			if ((cbDiaMembresias.getSelectedIndex()+1)<10)
				dia="0"+(cbDiaMembresias.getSelectedIndex()+1);
			else
				dia=(cbDiaMembresias.getSelectedIndex()+1)+"";
			if ((cbMesMembresias.getSelectedIndex()+1)<10)
				mes="0"+(cbMesMembresias.getSelectedIndex()+1);
			else
				mes=(cbMesMembresias.getSelectedIndex()+1)+"";
			año=cbAñoMembresias.getSelectedItem().toString();
			fecha=año+"-"+mes+"-"+dia+" 23:59:00";
			modMembresias=new DefaultTableModel();
			modMembresias.addColumn("Membresia");
			modMembresias.addColumn("Importe");
			tablaMembresias.setModel(modMembresias);
			rentasMembresias();
			return;
		}
		if(e.getSource()==btnAtras||e.getSource()==btnAtras2||e.getSource()==btnAtras3)
		{
			dispose();
			new MenuPrincipal(conexion,usuario);
		}
	}

	private void rentasClientes() 
	{
		PreparedStatement cmd;
		ResultSet rs;
		String sql="select concat(c.nombre,' ',ap_paterno,' ',ap_materno) as nombre,coalesce(sum(r.total),0) as total from rentas r inner join clientes c on r.cod_cte=c.cod_cte where r.fecha<=?  group by c.nombre";
		String cliente;
		double importe;
		try {
			cmd=conexion.prepareStatement(sql);
			cmd.setString(1, fecha);
			rs=cmd.executeQuery();
			while(rs.next())
			{
				cliente=rs.getString("nombre");
				importe=rs.getDouble("total");
				modClientes.addRow(new Object[]{cliente,importe});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void rentasTiposVideo() 
	{
		PreparedStatement cmd;
		ResultSet rs;
		String sql="select t.nombre,coalesce(sum(rv.total),0) as total from rentas_videos rv inner join rentas r on r.id=rv.id  inner join videos v on rv.video=v.id inner join tipos_video t on v.tipo=t.id where r.fecha<=? group by t.nombre";
		String tipo;
		double importe;
		try 
		{
			cmd=conexion.prepareStatement(sql);
			cmd.setString(1, fecha);
			rs=cmd.executeQuery();
			while(rs.next())
			{
				tipo=rs.getString("nombre");
				importe=rs.getDouble("total");
				modTiposVideo.addRow(new Object[]{tipo,importe});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void rentasMembresias()
	{
		PreparedStatement cmd;
		ResultSet rs;
		String sql="select m.nombre,coalesce(sum(r.total),0) as total from rentas r inner join clientes c on r.cod_cte=c.cod_cte inner join membresias m on c.membresia=m.id where r.fecha<=? group by m.nombre";
		String membresia;
		double importe;
		try {
			cmd=conexion.prepareStatement(sql);
			cmd.setString(1, fecha);
			rs=cmd.executeQuery();
			while(rs.next())
			{
				membresia=rs.getString("nombre");
				importe=rs.getDouble("total");
				modMembresias.addRow(new Object[]{membresia,importe});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void combosClientes()
	{
		e1Clientes=new JLabel("Día");
		e1Clientes.setBounds(50, 50, 50, 20);
		e2Clientes=new JLabel("Mes");
		e2Clientes.setBounds(145, 50, 50, 20);
		e3Clientes=new JLabel("Año");
		e3Clientes.setBounds(295, 50, 50, 20);
		cbDiaClientes=new JComboBox(dias);
		cbDiaClientes.setBounds(80,50,50,20);
		cbMesClientes=new JComboBox(meses);
		cbMesClientes.setBounds(180,50,100,20);
		cbAñoClientes=new JComboBox(años);
		cbAñoClientes.setBounds(330,50,100,20);
		cbDiaClientes.setSelectedIndex(diaHoy-1);
		cbMesClientes.setSelectedIndex(mesHoy);
		cbAñoClientes.setSelectedIndex(añoHoy-1980);
		panelClientes.add(e1Clientes);
		panelClientes.add(e2Clientes);
		panelClientes.add(e3Clientes);
		panelClientes.add(cbDiaClientes);
		panelClientes.add(cbMesClientes);
		panelClientes.add(cbAñoClientes);
	}
	private void combosTiposVideo()
	{
		e1Videos=new JLabel("Día");
		e1Videos.setBounds(50, 50, 50, 20);
		e2Videos=new JLabel("Mes");
		e2Videos.setBounds(145, 50, 50, 20);
		e3Videos=new JLabel("Año");
		e3Videos.setBounds(295, 50, 50, 20);
		cbDiaVideos=new JComboBox(dias);
		cbDiaVideos.setBounds(80,50,50,20);
		cbMesVideos=new JComboBox(meses);
		cbMesVideos.setBounds(180,50,100,20);
		cbAñoVideos=new JComboBox(años);
		cbAñoVideos.setBounds(330,50,100,20);
		cbDiaVideos.setSelectedIndex(diaHoy-1);
		cbMesVideos.setSelectedIndex(mesHoy);
		cbAñoVideos.setSelectedIndex(añoHoy-1980);
		panelTiposVideo.add(e1Videos);
		panelTiposVideo.add(e2Videos);
		panelTiposVideo.add(e3Videos);
		panelTiposVideo.add(cbDiaVideos);
		panelTiposVideo.add(cbMesVideos);
		panelTiposVideo.add(cbAñoVideos);
	}
	private void combosMembresias()
	{
		e1Membresias=new JLabel("Día");
		e1Membresias.setBounds(50, 50, 50, 20);
		e2Membresias=new JLabel("Mes");
		e2Membresias.setBounds(145, 50, 50, 20);
		e3Membresias=new JLabel("Año");
		e3Membresias.setBounds(295, 50, 50, 20);
		cbDiaMembresias=new JComboBox(dias);
		cbDiaMembresias.setBounds(80,50,50,20);
		cbMesMembresias=new JComboBox(meses);
		cbMesMembresias.setBounds(180,50,100,20);
		cbAñoMembresias=new JComboBox(años);
		cbAñoMembresias.setBounds(330,50,100,20);
		cbDiaMembresias.setSelectedIndex(diaHoy-1);
		cbMesMembresias.setSelectedIndex(mesHoy);
		cbAñoMembresias.setSelectedIndex(añoHoy-1980);
		panelMembresias.add(e1Membresias);
		panelMembresias.add(e2Membresias);
		panelMembresias.add(e3Membresias);
		panelMembresias.add(cbDiaMembresias);
		panelMembresias.add(cbMesMembresias);
		panelMembresias.add(cbAñoMembresias);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getSource()==cbDiaClientes||e.getSource()==cbMesClientes||e.getSource()==cbAñoClientes)
		{
			modClientes=new DefaultTableModel();
			modClientes.addColumn("Cliente");
			modClientes.addColumn("Importe");
			tablaClientes.setModel(modClientes);
			return;
		}
		if (e.getSource()==cbDiaVideos||e.getSource()==cbMesVideos||e.getSource()==cbAñoVideos)
		{
			modTiposVideo=new DefaultTableModel();
			modTiposVideo.addColumn("Tipo");
			modTiposVideo.addColumn("Importe");
			tablaTiposVideo.setModel(modTiposVideo);
			return;
		}
		if (e.getSource()==cbDiaMembresias||e.getSource()==cbMesMembresias||e.getSource()==cbAñoMembresias)
		{
			modMembresias=new DefaultTableModel();
			modMembresias.addColumn("Tipo");
			modMembresias.addColumn("Importe");
			tablaMembresias.setModel(modMembresias);
		}
	}
}
