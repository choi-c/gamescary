import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
* This class is the character selection interface
* Extension of a JPanel
*/

public class characterselect extends JPanel{
    // Properties
    /**
     * These images are used as icons, which show users which character they have selected
     */
    BufferedImage hider1,hider2,seeker1,seeker2 = null;

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
     * Paints the character icons in the character select screen
     * @param g the graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(seeker1, 75, 133, 200, 267, null);
        g.drawImage(seeker2, 380, 133, 200, 267, null);
        g.drawImage(hider1, 700, 133, 200, 267, null);
        g.drawImage(hider2, 1020, 133, 200, 267, null);
    }

    // Constructor
    /**
     * Constructor for the character select screen. Loads all the necessary images.
     */
    public characterselect(){
        super();
        try{
            hider1 = ImageIO.read(new File("hider1.png"));
            hider2 = ImageIO.read(new File("hider2.png"));
            seeker1 = ImageIO.read(new File("seeker1.png"));
            seeker2 = ImageIO.read(new File("seeker2.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}