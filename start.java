import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class start extends JPanel{
    // Properties
    BufferedImage start = null;

    // Methods
    public BufferedImage loadImage(String strFileName){
        InputStream imageclass = null;
        imageclass = this.getClass().getResourceAsStream(strFileName);
        if (imageclass == null){
            return null;
        }else{
            try{
                return ImageIO.read(imageclass);
            }catch(IOException e){
                return null;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(start, 0, 0, null);
    }

    // Constructor
    public start(){
        super();
        try{
            start = ImageIO.read(new File("home.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}