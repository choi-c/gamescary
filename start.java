import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class start extends JPanel{
    // Properties
    /**
     * This is the background image used for the start screen of the game
     */
    BufferedImage start = null;

    // Methods
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
      * Prints all the necessary images for the help screen
      * @param g the graphics object
      */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(start, 0, 0, null);
    }

    // Constructor
    /**
     * Constructor for the start panel of the game. Loads in the homescreen image.
     */
    public start(){
        super();
        try{
            start = ImageIO.read(new File("home.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}