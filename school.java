import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class school extends JPanel{
    // Properties
    BufferedImage hider1,hider2,chair,floor,gym,locker,pillar,principal,seeker1,seeker2,table,wall = null;
    String[][] strMap = new String[20][20];
    int playerX = 2;
    int playerY = 29;

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
        g.drawImage(seeker1, 75, 133, 200, 267, null);
    }
    public BufferedImage letterImage(String letter){
        if(letter.equals("c")){
            return chair;
        }else if(letter.equals("l")){
            return locker;
        }else if(letter.equals("s")){
            return pillar;
        }else if(letter.equals("p")){
            return principal;
        }else if(letter.equals("t")){
            return table;
        }else if(letter.equals("l")){
            return wall;
        }
    }

    // Constructor
    public school(){
        super();
        try{
            hider1 = ImageIO.read(new File("hider1.png"));
            hider2 = ImageIO.read(new File("hider2.png"));
            chair = ImageIO.read(new File("chair.png"));
            floor = ImageIO.read(new File("floor.png"));
            gym = ImageIO.read(new File("gym.png"));
            locker = ImageIO.read(new File("locker.png"));
            pillar = ImageIO.read(new File("pillar.png"));
            principal = ImageIO.read(new File("principal.png"));
            seeker1 = ImageIO.read(new File("seeker1.png"));
            seeker2 = ImageIO.read(new File("seeker2.png"));
            table = ImageIO.read(new File("table.png"));
            wall = ImageIO.read(new File("wall.png"));

        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}