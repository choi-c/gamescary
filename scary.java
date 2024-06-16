import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread.*;

/**
 * This class houses all the user interfaces for our program
 * Networking, buttons, character selection, gameplay and sending messages
 * 
 * @author Carmen Choi, Michelle Zhang, Timothy Chhor
 * @version 1.1
 * @since 2024-06-14
 */

public class scary implements ActionListener, KeyListener{
    // Properties
    /**
     * The JFrame of the program
     */
    JFrame frame;
    /**
     * A panel made from the school.java file that is used for all main gameplay
     */
    school panel;
    /**
     * this panel hasa a series of images that teach players how to play the game
     */
    help helpPanel;
    /**
     *The text area is used to write messages to be sent between two players
     */
    JTextArea chat = new JTextArea();
    /**
     * This panel is used to display the results of the game, and who won and loss
     */
    gameover gameoverPanel;
    /**
     *This scroll panel is used with the chat to make it scrollable
     */
    JScrollPane scrollChat = new JScrollPane(chat);
    /**
     * This panel is the title screen of the game
     * There is a text area for ip address, port number and a name
     * There is a button to start the game
     * There is a well-drawn background image
     */
    start startPanel;
    /**
     * This panel is used by players to choose which character they want to play as
     */
    characterselect characterPanel;
    /**
     * These buttons are used for various functions of the game
     * connectb is used after the ip address, port number and name have been entered
     * tagb is used during gameplay, so that the seeker can catch the hider
     * helpb is used in the home screen to get to the help screen
     */
    JButton connectb,tagb, helpb;
    /**
     * these text fields are used for networking information
     * ip is used to enter the ip address of the host
     * port is used to enter the port that’s being connected to
     * name is used to enter the name you want to be seen 
     * msg is used to enter the message you want to send in the text box
     */
    JTextField ip = null,port = null,name = null, msg = new JTextField("Send Message");
    /**
     * These labels are show the user which icons are seekers and which icons are hiders
     * hiders is printed on the right side of the screen to show which players are hiders
     * seekers is printed on the left side of the screen to show which players are seekers
     * selectName is used to show which name is selected
     */
    JLabel seekers,hiders, selectName,timerLabel,timerCD;
    /**
     * These JButtons are used by users to select which character they want and send messages
     * JButtons char1, char2, char3 and char4 are choices
     * lockIn is used to confirm the character choice
     * send is used to confirm the message you chose and send it
     */
    JButton char1,char2,char3,char4, lockIn,send;
    /**
     * These JButtons are used to navigate the help screen, going back and forth
     * their names are intuitive and they do exactly as they are names
     */
    JButton next, continueb, back;
    /** These string names holds important information regarding the player
     * strName is the name of the player in the chat
     * strMsg is the message that they want to send
     * strPH is whether or not the user is the player or the host
     */
    public String strName = null, strMsg = null, strPH = "";
    /**
     * These booleans are true and false based on whether the host and client are connected
     * blnConnected is whether or ot the user is connected
     * blnOConnected is whether or not the other user is connected
     */
    boolean blnTyped = false, blnConnected = false, blnOConnected = false;
    /** 
     * These booleans are used with key inputs to move the player around the map
     * They show whether the key is on or off
     * The program uses that to see whether or not they should move the user
     */
    boolean aOn, sOn, dOn, wOn;
    /**
     * this integer is used to determine whether or not the seeker is tagging the hider
     * if the integer value is 0, then the seeker is not trying to tag the hider
     * if the integer value is 1, then the seeker is trying to tag the hider, but is unsuccessful
     * if the integer value is 2, then the seeker was successful and the game will end
     */
    int tagging = 0;
    /**
     * These integers are used with the timer to makeup a variety of game mechanics
     */
    int intTimer = 1,intCD = 1;
    /**
     * This 2D array is used to load the map
     * the map is used to print the map, and to control interactions between the player and map
     * it reads from the map csv file
     */
    String[][] strMap = new String[51][51];
    /**
     * this 1D array is used with strMap to load individual values in the csv file
     */
    String strTimer = "";
    /**
     * this 1D array is used with strMap to load individual values in the csv file
     */
    String[] strSplit;
    /**
     * These timers are used throughout the code, to send messages and time the game
     * tmenu is sends messages between users on character choice at 60Hz
     * tcoords sends game information, like coordinates, between users at 60Hz
     * tfreeze counts for 5 seconds, stopping players after they step on the ice trap during gameplay
     */
    Timer tmenu = new Timer(1000/60, this), tcoords = new Timer(1000/60,this), tfreeze = new Timer(5000,this), tgame = new Timer(1000,this);

    /**
     *  this is the Super Socket Master Object, used for network communication
     */
    SuperSocketMaster ssm;

    // Methods
    /**
     *  Tests for events from buttons and text fields used in gameplay, networking and menu options
     * @param evt the event with data on what caused it, to differentiate between events
     */
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == helpb){
            frame.setContentPane(helpPanel);
            frame.validate();
        }
        if(evt.getSource() == next){
            helpPanel.intPage ++;
            System.out.println("Help page: "+helpPanel.intPage);
            helpPanel.repaint();
            if(helpPanel.intPage > 4){
                frame.setContentPane(startPanel);
                helpPanel.intPage = 1;
            }
            if(helpPanel.intPage == 4){
                next.setText("Continue to game");
            }else if(helpPanel.intPage != 4){
                next.setText("Next");
            }
        }else if(evt.getSource() == back){
            helpPanel.intPage --;
            helpPanel.repaint();
            if(helpPanel.intPage <= 0){
                frame.setContentPane(startPanel);
                helpPanel.intPage ++;
            }
        }
        if(evt.getSource() == connectb){
            if(!strName.equals(null)){
                if(!ip.getText().equals("") && !port.getText().equals("")){
                    strPH = "player";
                    panel.strPH = "player";
                    System.out.println("connect as player");
                    ssm = new SuperSocketMaster(ip.getText(), Integer.parseInt(port.getText()),this);
                    panel.ssm = ssm;
                    ssm.connect();
                    char1.setEnabled(false);
                    char2.setEnabled(false);
                }else if (ip.getText().equals("") && !port.getText().equals("")){
                    strPH = "host";
                    System.out.println("connect as host");
                    ssm = new SuperSocketMaster(Integer.parseInt(port.getText()),this);
                    ssm.connect();
                    char3.setEnabled(false);
                    char4.setEnabled(false);
                }else{
                    System.out.println("Enter ip, port, or name");
                }
                System.out.println(strName);
                frame.setContentPane(characterPanel);
                tmenu.start();
                frame.validate();
            }
        }else if(evt.getSource() == name){
            strName = name.getText();
        }else if(evt.getSource() == char1){
            char1.setEnabled(false);
            char2.setEnabled(true);
            panel.strSelect = "seeker1";
            panel.intPX = 24;
            panel.intPY = 13;
            frame.validate();
        }else if(evt.getSource() == char2){
            char1.setEnabled(true);
            char2.setEnabled(false);
            panel.strSelect = "seeker2";
            panel.intPX = 24;
            panel.intPY = 13;
            frame.validate();
        }else if(evt.getSource() == char3){
            char3.setEnabled(false);
            char4.setEnabled(true);
            panel.strSelect = "hider1";
            panel.intPX = 5;
            panel.intPY = 32;
            frame.validate();
        }else if(evt.getSource() == char4){
            char3.setEnabled(true);
            char4.setEnabled(false);
            panel.strSelect = "hider2";
            panel.intPX = 5;
            panel.intPY = 32;
            frame.validate();
        }else if(evt.getSource() == lockIn){
            System.out.println("playing as "+panel.strSelect);
            frame.setContentPane(panel);
            frame.requestFocus();
            frame.addKeyListener(this);
            tmenu.stop();
            tcoords.start();
            if(panel.strSelect.equals("seeker1") || panel.strSelect.equals("seeker2")){
                tagb = new JButton("Tag");
                tagb.setBounds(770,0,100,50);
                tagb.addActionListener(this);
                panel.add(tagb);
            }
            frame.validate();
        }

        if(evt.getSource() == msg){
            strMsg = msg.getText();
        }else if(evt.getSource() == send){
            ssm.sendText("chat," + strPH +","+ strName + ","+ strMsg);
            msg.setText("");
            chat.append(strName +": "+ strMsg + "\n");
            frame.requestFocus();
        }
        if(evt.getSource() == tmenu){
            ssm.sendText("chrSelect,"+panel.strSelect);
        }
        if(evt.getSource() == tcoords){
            intTimer ++;
            if(intTimer % 30 == 0){
                intCD ++;
                timerLabel.setText("Timer: "+intCD);
                if(intCD == 180){
                    gameoverPanel.intScreen = 2;
                    frame.setContentPane(gameoverPanel);
                }
            }
            if(tagging == 1){
                if(panel.strSelect.equals("seeker1")){
                    if(panel.intPY == panel.intPY2 && panel.intPX == panel.intPX2){
                        tagging = 2;
                        gameoverPanel.intScreen = 1;
                        frame.setContentPane(gameoverPanel);
                        frame.validate();
                    }
                }
                if(panel.strSelect.equals("seeker2")){
                    for(int i = -1; i <= 2; i++){
                        for(int j = -1; j <= 2; j++){
                            if(panel.intPY + i == panel.intPY2 && panel.intPX + j == panel.intPX2){
                                tagging = 2;
                                gameoverPanel.intScreen = 1;
                                frame.setContentPane(gameoverPanel);
                                frame.validate();
                            }
                        }
                    }
                }
            }
            if(panel.blnPflTaken){
                panel.intPflTaken = 1;
            }
            if(panel.blnPiTaken){
                panel.intPiTaken = 1;
            }
            if(strPH.equals("host")){
                while(!panel.strMap[panel.fly][panel.flx].equals("f")){
                    panel.flx = (int)(Math.random()*49+1);
                    panel.fly = (int)(Math.random()*36+1);
                }
                // Ice Image
                while(!panel.strMap[panel.iy][panel.ix].equals("f")){
                    panel.ix = (int)(Math.random()*49+1);
                    panel.iy = (int)(Math.random()*36+1);
                }
            }
            panel.repaint();
            ssm.sendText("game,"+panel.strSelect+","+panel.intPX+","+panel.intPY+","+panel.intPflTaken+","+panel.intPiTaken+","+panel.flx+","+panel.fly+","+panel.ix+","+panel.iy+","+tagging);
        }
        if(evt.getSource() == ssm){
            String[] strNMsg = ssm.readText().split(",");
            if(strNMsg[0].equals("chrSelect")){
                String[] strPlayerChoice = ssm.readText().split(",");
                panel.strOChar = strPlayerChoice[1];
            }else if(strNMsg[0].equals("chat")){
                chat.append(strNMsg[2] +": "+ strNMsg[3] + "\n");
            }else if(strNMsg[0].equals("game")){
                panel.strOChar = strNMsg[1];
                panel.intPX2 = Integer.parseInt(strNMsg[2]);
                panel.intPY2 = Integer.parseInt(strNMsg[3]);
                if(Integer.parseInt(strNMsg[4]) == 1){
                    panel.blnPflTaken = true;
                }
                if(Integer.parseInt(strNMsg[5]) == 1){
                    panel.blnPiTaken = true;
                }
                if(strPH == "player"){
                    panel.flx = Integer.parseInt(strNMsg[6]);
                    panel.fly = Integer.parseInt(strNMsg[7]);
                    panel.ix = Integer.parseInt(strNMsg[8]);
                    panel.iy = Integer.parseInt(strNMsg[9]);
                }
                if(strNMsg[10].equals("2")){
                    tagging = 2;
                    frame.setContentPane(gameoverPanel);
                    frame.validate();
                }
            }
        }if(evt.getSource() == tfreeze){
            panel.canMove = true;
            tfreeze.stop();
        }
        if(evt.getSource() == tagb){
            tagging = 1;
        }
    }

    /**
     * Checks for when keys are pressed and uses it to facilitate movements in the game
     * @param evt the event with data on what caused it, to differentiate between events
     */
    public void keyPressed(KeyEvent evt){
        if(panel.canMove && evt.getKeyChar() == 's' && sOn && !panel.strMap[panel.intPY + 1][panel.intPX].equals("w") && !panel.strMap[panel.intPY + 1][panel.intPX].equals("s") && !panel.strMap[panel.intPY + 1][panel.intPX].equals("c")){
            panel.intPY += 1;
            sOn = false;
            System.out.println(panel.ix+","+panel.iy);
            System.out.println(panel.intPY);
        }else if(panel.canMove && evt.getKeyChar() == 'w' && wOn && !panel.strMap[panel.intPY - 1][panel.intPX].equals("w") && !panel.strMap[panel.intPY - 1][panel.intPX].equals("s") && !panel.strMap[panel.intPY - 1][panel.intPX].equals("c")){
            panel.intPY -= 1;
            wOn = false;
            System.out.println(panel.ix+","+panel.iy);
            System.out.println(panel.intPY);
        }else if(panel.canMove && evt.getKeyChar() == 'a' && aOn && !panel.strMap[panel.intPY][panel.intPX - 1].equals("w") && !panel.strMap[panel.intPY][panel.intPX - 1].equals("s") && !panel.strMap[panel.intPY][panel.intPX - 1].equals("c")){
            panel.intPX -= 1;
            System.out.println(panel.intPX);
            aOn = false;
            System.out.println(panel.ix+","+panel.iy);
        }else if(panel.canMove && evt.getKeyChar() == 'd' && dOn && !panel.strMap[panel.intPY][panel.intPX + 1].equals("w") && !panel.strMap[panel.intPY][panel.intPX + 1].equals("s") && !panel.strMap[panel.intPY][panel.intPX + 1].equals("c")){
            panel.intPX += 1;
            dOn = false;
            System.out.println(panel.intPX);
            System.out.println(panel.ix+","+panel.iy);
        }
        if(!panel.canMove){
            tfreeze.start();
        }
        panel.repaint();
    }

    /**
     * Checks for when keys are released and uses it to facilitate movements in the game
     * @param evt the event with data on what caused it, to differentiate between events
     */
    public void keyReleased(KeyEvent evt){
        //System.out.println("A key was released");
        
        if(evt.getKeyChar() == 's'){
            sOn = true;
        }else if(evt.getKeyChar() == 'w'){
            wOn = true;
        }
        if(evt.getKeyChar() == 'a'){
            aOn = true;
        }else if(evt.getKeyChar() == 'd'){
            dOn = true;
        }
        

    }
    /**
     * Empty method
     * @param evt the event with data on what caused it, to differentiate between events
     */
    public void keyTyped(KeyEvent evt){
    }

    // Constructor
    /**
     * Constructor for the main game. All panels and JComponents are loaded here.
     */
    public scary(){
        frame = new JFrame("Scary Hide & seek");

        // setting up start screen
        startPanel = new start();
        startPanel.setLayout(null);
        startPanel.setPreferredSize(new Dimension(1280,720));
        
        // adding text fields for ip, port number and user name
        ip = new JTextField();
        ip.setBounds(400,305,200,80);
        ip.addActionListener(this);
        startPanel.add(ip);
        port = new JTextField();
        port.setBounds(400,435,200,80);
        port.addActionListener(this);
        startPanel.add(port);
        name = new JTextField(null);
        name.setBounds(400,560,200,80);
        name.addActionListener(this);
        startPanel.add(name);

        // adding help button to take to help menu
        helpb = new JButton("Help");
        helpb.setBounds(800,450,200,75);
        helpb.addActionListener(this);
        startPanel.add(helpb);

        // help screen panel
        helpPanel = new help();
        helpPanel.setLayout(null);
        helpPanel.setPreferredSize(new Dimension(1280, 720));

        // next button to continue to next page in help screen
        next = new JButton("Next");
        next.setBounds(640, 600, 200,75);
        next.addActionListener(this);
        helpPanel.add(next);

        // back button to return to previous help screen page
        back = new JButton("Back");
        back.setBounds(390,600,200,75);
        back.addActionListener(this);
        helpPanel.add(back);

        // connect button to continue to game
        connectb = new JButton("Connect");
        connectb.setBounds(800,550,300,100);
        connectb.addActionListener(this);
        startPanel.add(connectb);
        
        // character select panel
        characterPanel = new characterselect();
        characterPanel.setLayout(null);
        characterPanel.setPreferredSize(new Dimension(1280, 720));
        //selectName stuff doesnt work yet
        selectName = new JLabel(strName);
        selectName.setBounds(0,0,200,100);
        characterPanel.add(selectName);

        seekers = new JLabel("Seekers");
        seekers.setBounds(300,0,640,100);
        characterPanel.add(seekers);

        hiders = new JLabel("Hiders");
        hiders.setBounds(965,0,640,100);
        characterPanel.add(hiders);

        char1 = new JButton("Choose");
        char1.setBounds(80,500,160,50);
        char1.addActionListener(this);
        characterPanel.add(char1);

        char2 = new JButton("Choose");
        char2.setBounds(400,500,160,50);
        char2.addActionListener(this);
        characterPanel.add(char2);

        char3 = new JButton("Choose");
        char3.setBounds(720,500,160,50);
        char3.addActionListener(this);
        characterPanel.add(char3);

        char4 = new JButton("Choose");
        char4.setBounds(1040,500,160,50);
        char4.addActionListener(this);
        characterPanel.add(char4);

        lockIn = new JButton("LOCK IN");
        lockIn.setBounds(560,600,160,50);
        lockIn.addActionListener(this);
        characterPanel.add(lockIn);
        characterPanel.repaint();

        // The actual game
        panel = new school();
        panel.setLayout(null);
    
        chat.setBounds(920,0,360,565);
        panel.add(chat);

        msg.setBounds(920,570, 360,100);
        msg.addActionListener(this);
        panel.add(msg);

        send = new JButton("SEND");
        send.setBounds(920,670,360,50);
        send.addActionListener(this);
        panel.add(send);

        timerLabel = new JLabel("Time Remaining: 0");
        timerLabel.setBounds(750,100,150,50);
        panel.add(timerLabel);

        gameoverPanel = new gameover();
        gameoverPanel.setLayout(null);
        gameoverPanel.setPreferredSize(new Dimension(1280,720));

        frame.requestFocus();
        frame.addKeyListener(this);
        
        frame.setContentPane(startPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Main Method
    public static void main(String[] args){
        new scary();
    }
}