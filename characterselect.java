import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class characterselect extends JPanel{
    // Properties
    BufferedImage yourPlayer1,yourPlayer2,teamPlayer1,teamPlayer2 = null;

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
        g.drawImage(yourPlayer1, 80, 133, 160, 267, null);
        g.drawImage(yourPlayer2, 400, 133, 160, 267, null);
        g.drawImage(teamPlayer1, 720, 133, 160, 267, null);
        g.drawImage(teamPlayer2, 1040, 133, 160, 267, null);
    }

    // Constructor
    public characterselect(){
        super();
        try{
            yourPlayer1 = ImageIO.read(new File("hider1.png"));
            yourPlayer2 = ImageIO.read(new File("hider2.png"));
            teamPlayer1 = ImageIO.read(new File("hider1.png"));
            teamPlayer2 = ImageIO.read(new File("hider2.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}