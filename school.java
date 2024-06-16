import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.Timer.*;
import java.awt.event.*;

public class school extends JPanel{
    // Properties
    /**
     * These images make up the visual aspects of the game
     * All of these correspond to a certain part of the game map and are read on the array
     */
    BufferedImage hider1,hider2,chair,floor,gym,locker,pillar,principal,seeker1,seeker2,desk,wall,ice,flashlight,visibility = null;
    /**
     * This 2D array is used to load the map
     * the map is used to print the map, and to control interactions between the player and map
     * it reads from the map csv file
     */
    String[][] strMap = new String[51][51];
    /**
     * these integers are the X coordinates of the user and the other user on the map
     */
    int intPX = 5,intPX2;
    /**
     * these integers are the Y coordinates of the user and the other user on the map
     */
    int intPY = 32,intPY2;
    /**
     * this string tells the code whether the user is the host or the client
     */
    String strPH = "";
    /**
     * this is the Super Socket Master Object, used for network communication
     */
    SuperSocketMaster ssm;
    /** 
     * these integers are the x and y coordinates of the power ups, that are randomly generated
     * the integers are used to show where the power ups are on the map
     * flx and fly are used for the flashlight and ix and iy are used for the ice
     */
    int flx = 1, fly = 1, ix = 1, iy = 1;
    /**
     * this string is used to store data on what character other player chose and what block they're standing on
     */
    String strBlock, strOChar;
    /**
     * this string is used to store data to show what you chose
     */
    String strSelect = null;
    /**
     * these booleans control whether the flashlight or ice have already been used
     * they prevent power-ups from being used over and over
     * they also help users from having the same power ups at the same time
     */
    Boolean blnVisibility = false, blnPi = false, blnPfl = false, blnPflTaken = false,blnPiTaken = false;
    /**
     * this boolean is only used to stop the player from moving when the ice is active
     */
    Boolean canMove = true;
    /**
     * this integer is used to tell the other player whether the flashlight and ice have been taken
     */
    int intPflTaken = 0, intPiTaken = 0;

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

    /**
     * paints gameplay onto the JFrame
     * csv map file is turned into an array, which is read when printing the the map
     * portions of the map are printed based on the player x and y position
     * also prints game mechanics: power-ups and hiding
     */
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

        // regularly prints out a 7x7 grid that updates based on the location of the player
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
                            //blnPfl = gameplay.characterBuffs();
                            //blnPi = gameplay.characterBuffs();
                            if(blnPfl == false && blnPflTaken == false){
                                if(flx == intCol && fly == intRow){
                                    g.drawImage(flashlight, intX, intY, 80, 80, null);
                                }
                            }
                            if(blnPi == false && blnPiTaken == false){
                                if(ix == intCol && iy == intRow){
                                    g.drawImage(ice, intX, intY, 80, 80, null);
                                }
                            }
                            if(intPX2 == intCol && intPY2 == intRow){
                                if(strOChar.equals("seeker1")){
                                    g.drawImage(seeker1, intX, intY, 80, 80, null);
                                }else if(strOChar.equals("seeker2")){
                                    g.drawImage(seeker2, intX, intY, 80, 80, null);
                                }else if(strOChar.equals("hider1")){
                                    if(strMap[intPY2][intPX2].equals("l")){
                                        g.drawImage(locker, intX, intY, 80, 80, null);
                                    }else if(strMap[intPY2][intPX2].equals("d")){
                                        g.drawImage(desk, intX, intY, 80, 80, null);
                                    }else{
                                        g.drawImage(hider1, intX, intY, 80, 80, null);    
                                    }
                                }else if(strOChar.equals("hider2")){
                                    if(strMap[intPY2][intPX2].equals("l")){
                                        g.drawImage(locker, intX, intY, 80, 80, null);
                                    }else if(strMap[intPY2][intPX2].equals("d")){
                                        g.drawImage(desk, intX, intY, 80, 80, null);
                                    }else{
                                        g.drawImage(hider2, intX, intY, 80, 80, null);    
                                    }
                                }
                            }
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

        //visibility stuff
        if(blnVisibility == false && !strSelect.equals("seeker1")){
            g.drawImage(visibility,0, 0, 720, 720, null);
        }

        // Hiding in tables and lockers
        if(strSelect.equals("hider1") || strSelect.equals("hider2")){
            if(strMap[intPY][intPX].equals("l")){
                g.drawImage(locker, 320, 320, 80, 80, null);
            }else if(strMap[intPY][intPX].equals("d")){
                g.drawImage(desk, 320, 320, 80, 80, null);
            }
        }

        // Power-ups
        if(intPX == flx && intPY == fly  && blnPflTaken == false){    
            blnPfl = true;
            blnPflTaken = true;
            blnVisibility = true;
            g.drawImage(floor, 320, 320, 80, 80, null);
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
        }else if(intPX == ix && intPY == iy && blnPiTaken == false){
            blnPi = true;
            blnPiTaken = true;
            g.drawImage(floor, 320, 320, 80, 80, null);
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
            // freezing player when 
            canMove = false;
        }

        // Principal
        for(int i = -1; i <= 2; i++){
            for(int j = -1; j <= 2; j++){
                if(strMap[intPY + i][intPX + j].equals("p")){
                    blnPfl = false;
                    blnVisibility = false;
                }
            }
        }
    }


    // Constructor
    /**
     * Constructor for the gameplay layout. Loads all the necessary images that will be printed as a part of the map.
     */
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
            visibility = ImageIO.read(new File("visibility.png"));

        }catch(IOException e){
            System.out.println("Unable to load image");
        }
    }
}