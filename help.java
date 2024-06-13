import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class help extends JPanel{
    // Properties
    BufferedImage help1,help2,help3, help4, help5 = null;
    int intPage = 1;

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
        if(intPage == 1){
            g.drawImage(help1, 0,0,null);
        }else if(intPage == 2){
            g.drawImage(help2,0,0,null);
        }else if(intPage == 3){
            g.drawImage(help3,0,0,null);
        }else if(intPage == 4){
            g.drawImage(help4,0,0,null);
        }else if(intPage == 5){
            g.drawImage(help5,0,0,null);
        }
    }

    // Constructor
    public help(){
        try{
            help1 = ImageIO.read(new File("help1.png"));
            help2 = ImageIO.read(new File("help2.png"));
            help3 = ImageIO.read(new File("help3.png"));
            help4 = ImageIO.read(new File("help4.png"));
            help5 = ImageIO.read(new File("help5.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}
