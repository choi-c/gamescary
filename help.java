import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class help extends JPanel{
    // Properties
    BufferedImage help1,help2,help3 = null;

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
    public help(){
        try{
            help1 = ImageIO.read(new File("help1.png"));
            help2 = ImageIO.read(new File("help2.png"));
            help3 = ImageIO.read(new File("help3.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}
