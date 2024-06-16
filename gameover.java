import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
 * This class is the extension of a JPanel, which prints out the game's result
 */

public class gameover extends JPanel {
    // Properties
    /**
     * These images will print depending on if the hider won or seeker won
     * If it is imgs, seeker won, and if it is imgh, hider won
     */
    BufferedImage imgs = null,imgh = null;
    /**
     * This Integer tells the program which screen to print
     */
    int intScreen = 0;

    // Method
    /**
     * Reads image and gets the input stream from inside of the JAR File
     * @param strFileName the name of the file being read
     * @return image information, or null if the image cannot be read
     */
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
    /**
     * Paints the character icons in the character select screen
     * @param g the graphics object
     */
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
    /**
     * Constructor for the game over screen. Loads all the necessary images.
     */
    public gameover(){
        try{
            imgs = ImageIO.read(new File("swin.png"));
            imgh = ImageIO.read(new File("hwin.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}
