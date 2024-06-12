import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class school extends JPanel{
    // Properties
    BufferedImage hider1,hider2,chair,floor,gym,locker,pillar,principal,seeker1,seeker2,desk,wall,ice,flashlight = null;
    String[][] strMap = new String[51][51];
    int playerX = 5;
    int playerY = 32;
    int intPX = 5;
    int intPY = 32;
    int flx = 1, fly = 1, ix = 1, iy = 1;
    String strBlock;
    //int intChoice = gameplay.chrChoice();
    String strSelect = null;

    

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
			for(intCol = 0; intCol < 51; intCol++){
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
			for(intCol = 0; intCol < 51; intCol++){
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
            strBlock = strMap[intPX][intPY];
		}

        //randomized powerups
        //flashlight
        if(!strMap[flx][fly].equals("f")){
            flx = (int)(Math.random()*38+1);
            fly = (int)(Math.random()*51+1);
        }else{
            System.out.println(flx+","+fly);
            g.drawImage(flashlight, flx*80, fly*80, 80, 80, null);
        }
        //ice
        if(strMap[ix][iy].equals("f")){
            //g.drawImage(ice, ix, iy, 80, 80, null);
        }else{
            ix = (int)(Math.random()*38+1);
            iy = (int)(Math.random()*51+1);
        }

        // character image changes depending on character selected
        if(strSelect.equals("seeker1")){
            g.drawImage(seeker1, 320, 320, 80, 80, null);
        }else if(strSelect.equals("hider1")){
            g.drawImage(hider1, 320, 320, 80, 80, null);
        }else if(strSelect.equals("seeker2")){
            g.drawImage(seeker2, 320, 320, 80, 80, null);
        }else if(strSelect.equals("hider2")){
            g.drawImage(hider2, 320, 320, 80, 80, null);
        }else{
            System.out.println("Character not selected error"); 
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
            desk = ImageIO.read(new File("table.png"));
            wall = ImageIO.read(new File("wall.png"));
            ice = ImageIO.read(new File("ice.png"));
            flashlight = ImageIO.read(new File("flashlight.png"));


        }catch(IOException e){
            System.out.println("Unable to load image");
        }

    }
}