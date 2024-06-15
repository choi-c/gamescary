import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class gameover extends JPanel {
    // Properties
    BufferedImage imgs = null,imgh = null;
    int intScreen = 0;

    // Method
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
        //set gameoverPanel.intScreen to 1 when tagb
        if(intScreen == 1){
            g.drawImage(imgs, 0,0,null);
        }else if(intScreen == 2){
            g.drawImage(imgh,0,0,null);
        }
        
    }

    // Constructor
    public gameover(){
        try{
            imgs = ImageIO.read(new File("swin.png"));
            imgh = ImageIO.read(new File("hwin.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}
