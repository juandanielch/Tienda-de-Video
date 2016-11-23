package proyecto;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
//...
 
public class JPanelConFondo extends JPanel 
{
    private ImageIcon imagen=new ImageIcon("C://Users/TOSHIBA/Documents/Eclipse/VideoClub/imagenes/logo.png");
    
    public JPanelConFondo()
    {
    }
    
    public JPanelConFondo(ImageIcon imagen)
    {
    	this.imagen=imagen; 
    }
    @Override
    public void paint(Graphics g) 
    {
        g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(),
                        this);
 
        setOpaque(false);
        super.paint(g);
    }

}
