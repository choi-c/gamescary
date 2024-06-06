import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class school extends JPanel{
    // Properties
    BufferedImage hider1,hider2,chair,floor,gym,locker,pillar,principal,seeker1,seeker2,desk,wall = null;
    String[][] strMap = new String[38][48];
    int playerX = 5;
    int playerY = 34;
    int intPX = 5;
    int intPY = 32;

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

    // painting the map
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        BufferedReader fileMap = null;
        int intRow;
		int intCol;
        String strLine = null;
        String strSplit[];

        intPY = 32; // max: 30
        intPX = 5; // max: 40

        // Loading map array
        try{
            fileMap = new BufferedReader(new FileReader("school.csv"));
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }

        // Reading from school.csv array
		for(intRow = 0; intRow < 38; intRow++){
            try{
                strLine = fileMap.readLine();
            }catch(IOException e){
                System.out.println("File not found");
            }
			for(intCol = 0; intCol < 48; intCol++){
				strSplit = strLine.split(",");
				strMap[intRow][intCol] = strSplit[intCol];
			}
		}
        try{
		    fileMap.close();
        }catch(IOException e){
            System.out.println("File not found");
        }

        int intX = 0;
		int intY = 0;
		for(intRow = 0; intRow < 38; intRow++){
            // track if rows have visible cells
            boolean rowVisibleCells = false;
			for(intCol = 0; intCol < 48; intCol++){
                if (intRow >= 0 && intRow < strMap.length && intCol >= 0 && intCol < strMap[0].length) {
                    if(intRow >= intPY - 4 && intRow <= intPY + 4 && intCol >= intPX - 4 && intCol <= intPX + 4){
                        // mark the rows with visible cells
                        rowVisibleCells = true;
                        //System.out.println(strMap[intRow][intCol]);
                        //if (strMap[intRow][intCol] != null) {
                            if(strMap[intRow][intCol].equals("l")){
                                g.drawImage(locker, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("c")){
                                g.drawImage(chair, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("d")){
                                g.drawImage(desk, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("p")){
                                g.drawImage(principal, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("s")){
                                g.drawImage(pillar, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("f")){
                                g.drawImage(floor, intX, intY, 80, 80, null);
                            }else if(strMap[intRow][intCol].equals("w")){
                                g.drawImage(wall, intX, intY, 80, 80, null);
                            }
                        //}
                        intX = intX + 80;
                        //System.out.println(intX+","+intY);
                    }
                }
			}
            // if the cells are visible, move onto the nexxt row
            if(rowVisibleCells){
                intY += 80;
            }
			intX = 0;
		}
        // iamge of player does not change
        g.drawImage(hider1, 320, 320, 80, 80, null);
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
            desk = ImageIO.read(new File("table.png"));
            wall = ImageIO.read(new File("wall.png"));

        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}