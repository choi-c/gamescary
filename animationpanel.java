import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class animationpanel {
    public static String[][] map(){
        String map[][];
        
    }
    // Properties
    BufferedImage imgFloor = null, imgSupport = null, imgTable = null, imgChair = null;

    // Methods

    // Constructor
    try{
        imgFloor = ImageIO.read(new File(""));
    } catch(IOException e){
        System.out.println("Unable to load image");
    }
}
