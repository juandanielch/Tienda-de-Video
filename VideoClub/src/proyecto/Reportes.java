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
	private JTabbedPane pesta�as;
	private JPanel panelClientes,panelTiposVideo,panelMembresias;
	private Boton btnClientes,btnTiposVideo,btnMembresias;
	private Boton btnAtras,btnAtras2,btnAtras3;
	private JScrollPane scrollClientes,scrollTiposVideo,scrollMembresias;
	private DefaultTableModel modClientes,modTiposVideo,modMembresias;
	private int usuario;
	private JComboBox cbA�oClientes,cbMesClientes,cbDiaClientes,cbA�oVideos,cbMesVideos,cbDiaVideos,cbA�oMembresias,cbMesMembresias,cbDiaMembresias;
	private String[] a�os;
	private String meses[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	private String[] dias;
	private JLabel e1Clientes,e2Clientes,e3Clientes,e1Videos,e2Videos,e3Videos,e1Membresias,e2Membresias,e3Membresias;
	private GregorianCalendar calendario;
	private int diaHoy,mesHoy,a�oHoy;
	String dia,mes,a�o,fecha;
	
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
		cbA�oClientes.addItemListener(this);
		cbDiaVideos.addItemListener(this);
		cbMesVideos.addItemListener(this);
		cbA�oVideos.addItemListener(this);
		cbDiaMembresias.addItemListener(this);
		cbMesMembresias.addItemListener(this);
		cbA�oMembresias.addItemListener(this);
	}

	private void hazInterfaz() 
	{		
		pesta�as=new JTabbedPane();
		//pesta�a clientes
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
		//pesta�a tipos de video
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
		//pesta�a membresias
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
		a�oHoy=fecha.getYear()+1900;
		int a�o=1980;
		a�os=new String[51];
		dias=new String[31];
		for(int i=0;i<31;i++)
			dias[i]=(i+1)+"";
		for(int i=0;i<a�os.length;i++)
		{
			a�os[i]=a�o+"";
			a�o++;
		}
		combosClientes();
		combosTiposVideo();
		combosMembresias();
		pesta�as.add("Rentas por cliente", panelClientes);
		pesta�as.add("Rentas por tipo de video", panelTiposVideo);
		pesta�as.add("Rentas por membresia", panelMembresias);
		add(pesta�as);
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
			a�o=cbA�oClientes.getSelectedItem().toString();
			fecha=a�o+"-"+mes+"-"+dia+" 23:59:00";
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
			a�o=cbA�oVideos.getSelectedItem().toString();
			fecha=a�o+"-"+mes+"-"+dia+" 23:59:00";
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
			a�o=cbA�oMembresias.getSelectedItem().toString();
			fecha=a�o+"-"+mes+"-"+dia+" 23:59:00";
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
		e1Clientes=new JLabel("D�a");
		e1Clientes.setBounds(50, 50, 50, 20);
		e2Clientes=new JLabel("Mes");
		e2Clientes.setBounds(145, 50, 50, 20);
		e3Clientes=new JLabel("A�o");
		e3Clientes.setBounds(295, 50, 50, 20);
		cbDiaClientes=new JComboBox(dias);
		cbDiaClientes.setBounds(80,50,50,20);
		cbMesClientes=new JComboBox(meses);
		cbMesClientes.setBounds(180,50,100,20);
		cbA�oClientes=new JComboBox(a�os);
		cbA�oClientes.setBounds(330,50,100,20);
		cbDiaClientes.setSelectedIndex(diaHoy-1);
		cbMesClientes.setSelectedIndex(mesHoy);
		cbA�oClientes.setSelectedIndex(a�oHoy-1980);
		panelClientes.add(e1Clientes);
		panelClientes.add(e2Clientes);
		panelClientes.add(e3Clientes);
		panelClientes.add(cbDiaClientes);
		panelClientes.add(cbMesClientes);
		panelClientes.add(cbA�oClientes);
	}
	private void combosTiposVideo()
	{
		e1Videos=new JLabel("D�a");
		e1Videos.setBounds(50, 50, 50, 20);
		e2Videos=new JLabel("Mes");
		e2Videos.setBounds(145, 50, 50, 20);
		e3Videos=new JLabel("A�o");
		e3Videos.setBounds(295, 50, 50, 20);
		cbDiaVideos=new JComboBox(dias);
		cbDiaVideos.setBounds(80,50,50,20);
		cbMesVideos=new JComboBox(meses);
		cbMesVideos.setBounds(180,50,100,20);
		cbA�oVideos=new JComboBox(a�os);
		cbA�oVideos.setBounds(330,50,100,20);
		cbDiaVideos.setSelectedIndex(diaHoy-1);
		cbMesVideos.setSelectedIndex(mesHoy);
		cbA�oVideos.setSelectedIndex(a�oHoy-1980);
		panelTiposVideo.add(e1Videos);
		panelTiposVideo.add(e2Videos);
		panelTiposVideo.add(e3Videos);
		panelTiposVideo.add(cbDiaVideos);
		panelTiposVideo.add(cbMesVideos);
		panelTiposVideo.add(cbA�oVideos);
	}
	private void combosMembresias()
	{
		e1Membresias=new JLabel("D�a");
		e1Membresias.setBounds(50, 50, 50, 20);
		e2Membresias=new JLabel("Mes");
		e2Membresias.setBounds(145, 50, 50, 20);
		e3Membresias=new JLabel("A�o");
		e3Membresias.setBounds(295, 50, 50, 20);
		cbDiaMembresias=new JComboBox(dias);
		cbDiaMembresias.setBounds(80,50,50,20);
		cbMesMembresias=new JComboBox(meses);
		cbMesMembresias.setBounds(180,50,100,20);
		cbA�oMembresias=new JComboBox(a�os);
		cbA�oMembresias.setBounds(330,50,100,20);
		cbDiaMembresias.setSelectedIndex(diaHoy-1);
		cbMesMembresias.setSelectedIndex(mesHoy);
		cbA�oMembresias.setSelectedIndex(a�oHoy-1980);
		panelMembresias.add(e1Membresias);
		panelMembresias.add(e2Membresias);
		panelMembresias.add(e3Membresias);
		panelMembresias.add(cbDiaMembresias);
		panelMembresias.add(cbMesMembresias);
		panelMembresias.add(cbA�oMembresias);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getSource()==cbDiaClientes||e.getSource()==cbMesClientes||e.getSource()==cbA�oClientes)
		{
			modClientes=new DefaultTableModel();
			modClientes.addColumn("Cliente");
			modClientes.addColumn("Importe");
			tablaClientes.setModel(modClientes);
			return;
		}
		if (e.getSource()==cbDiaVideos||e.getSource()==cbMesVideos||e.getSource()==cbA�oVideos)
		{
			modTiposVideo=new DefaultTableModel();
			modTiposVideo.addColumn("Tipo");
			modTiposVideo.addColumn("Importe");
			tablaTiposVideo.setModel(modTiposVideo);
			return;
		}
		if (e.getSource()==cbDiaMembresias||e.getSource()==cbMesMembresias||e.getSource()==cbA�oMembresias)
		{
			modMembresias=new DefaultTableModel();
			modMembresias.addColumn("Tipo");
			modMembresias.addColumn("Importe");
			tablaMembresias.setModel(modMembresias);
		}
	}
}
