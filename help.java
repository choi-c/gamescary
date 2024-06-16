import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
* This class file loads the help screen after a button in the menu screen is clicked
* Extension of a JPanel
*/

public class help extends JPanel{
    // Properties

    /**
    * These images provide guidance to players on how to play the game
    * They are observed as a series of slideshows
    */
    BufferedImage help1,help2,help3, help4, help5 = null;

    /**
    * This integer tells the program which page of the help page to display
    * It changes when a button is clicked
    */
    int intPage = 1;

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
    /**
     * Constructor for the help screen. Loads all the necessary images.
     */
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
