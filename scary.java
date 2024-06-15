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
    JFrame frame;
    school panel;
    help helpPanel;
    JTextArea chat = new JTextArea();
    JScrollPane scrollChat = new JScrollPane(chat);
    start startPanel;
    characterselect characterPanel;
    JButton connectb,tagb, helpb;
    JTextField ip = null,port = null,name = null, msg = new JTextField("Send Message");
    JLabel seekers,hiders, selectName;
    JButton char1,char2,char3,char4, lockIn,send;
    JButton next, continueb, back;
    public String strName = null, strMsg = null, strPH = "";
    boolean blnTyped = false;
    boolean aOn, sOn, dOn, wOn;
    int intStep = 1;
    String[][] strMap = new String[51][51];
    ImageIcon imgS1,imgS2,imgH1,imgH2;
    JLabel s1,s2,h1,h2;
    String[] strSplit;
    int intCount;
    String strMsgType;
    String strHX,strHY,strSX,strSY;
    Timer tmenu = new Timer(1000/60, this), tcoords = new Timer(1000/60,this);

    SuperSocketMaster ssm;

    // Methods
    /**
    * Tests for events from buttons and text fields used in gameplay, networking and menu options
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
            panel.strSelect = "seeker1";
            panel.intPX = 24;
            panel.intPY = 13;
            frame.validate();
        }else if(evt.getSource() == char2){
            panel.strSelect = "seeker2";
            panel.intPX = 24;
            panel.intPY = 13;
            frame.validate();
        }else if(evt.getSource() == char3){
            panel.strSelect = "hider1";
            panel.intPX = 5;
            panel.intPY = 32;
            frame.validate();
        }else if(evt.getSource() == char4){
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
            if(panel.blnPflTaken){
                panel.intPflTaken = 1;
            }
            if(panel.blnPiTaken){
                panel.intPiTaken = 1;
            }
            if(strPH.equals("host")){
                while(!strMap[panel.fly][panel.flx].equals("f")){
                    panel.flx = (int)(Math.random()*49+1);
                    panel.fly = (int)(Math.random()*36+1);
                }
                // Ice Image
                while(!strMap[panel.iy][panel.ix].equals("f")){
                    panel.ix = (int)(Math.random()*49+1);
                    panel.iy = (int)(Math.random()*36+1);
                }
            }else{
                
            }
            ssm.sendText("game,"+panel.strSelect+","+panel.intPX+","+panel.intPY+","+panel.intPflTaken+","+panel.intPiTaken+","+panel.flx+","+panel.fly+","+panel.ix+","+panel.iy);
        }
        if(evt.getSource() == ssm){
            String[] strNMsg = ssm.readText().split(",");
            if(strNMsg[0].equals("chrSelect")){
                String[] strPlayerChoice = ssm.readText().split(",");
                panel.strOChar = strPlayerChoice[1];
                /*
                if(strNMsg[1].equals("host")){
                    if(strNMsg[2].equals("seeker1")){
                        char1.setEnabled(false);
                    }else if(strNMsg[2].equals("seeker2")){
                        char2.setEnabled(false);
                    }else if(strNMsg[2].equals("hider1")){
                        char3.setEnabled(false);
                    }else if(strNMsg[2].equals("hider2")){
                        char4.setEnabled(false);
                    }
                }else if(strNMsg[1].equals("client")){
                    if(strNMsg[2].equals("seeker1")){
                        char1.setEnabled(false);
                    }else if(strNMsg[2].equals("seeker2")){
                        char2.setEnabled(false);
                    }else if(strNMsg[2].equals("hider1")){
                        char3.setEnabled(false);
                    }else if(strNMsg[2].equals("hider2")){
                        char4.setEnabled(false);
                    }
                }
                     */
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
            }
        }
    }
    
    /**
    * Returns the result of which character the player chose
    */
    public String getSelect(){
        return panel.strSelect;
    }

    /**
    * Checks for when keys are pressed and uses it to facilitate movements in the game
    * @param evt the event with data on what caused it, to differentiate between events
    */
    public void keyPressed(KeyEvent evt){
        if(evt.getKeyChar() == 's' && sOn && !panel.strMap[panel.intPY + 1][panel.intPX].equals("w") && !panel.strMap[panel.intPY + 1][panel.intPX].equals("s") && !panel.strMap[panel.intPY + 1][panel.intPX].equals("c")){
            panel.intPY += 1;
            sOn = false;
            System.out.println(panel.ix+","+panel.iy);
            System.out.println(panel.intPY);
        }else if(evt.getKeyChar() == 'w' && wOn && !panel.strMap[panel.intPY - 1][panel.intPX].equals("w") && !panel.strMap[panel.intPY - 1][panel.intPX].equals("s") && !panel.strMap[panel.intPY - 1][panel.intPX].equals("c")){
            panel.intPY -= 1;
            wOn = false;
            System.out.println(panel.ix+","+panel.iy);
            System.out.println(panel.intPY);
        }else if(evt.getKeyChar() == 'a' && aOn && !panel.strMap[panel.intPY][panel.intPX - 1].equals("w") && !panel.strMap[panel.intPY][panel.intPX - 1].equals("s") && !panel.strMap[panel.intPY][panel.intPX - 1].equals("c")){
            panel.intPX -= 1;
            System.out.println(panel.intPX);
            aOn = false;
            System.out.println(panel.ix+","+panel.iy);
        }else if(evt.getKeyChar() == 'd' && dOn && !panel.strMap[panel.intPY][panel.intPX + 1].equals("w") && !panel.strMap[panel.intPY][panel.intPX + 1].equals("s") && !panel.strMap[panel.intPY][panel.intPX + 1].equals("c")){
            panel.intPX += 1;
            dOn = false;
            System.out.println(panel.intPX);
            System.out.println(panel.ix+","+panel.iy);
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

        frame.requestFocus();
        frame.addKeyListener(this);
/*
        imgS1 = new ImageIcon("hider1.png");
        s1 = new JLabel(imgS1);
        s1.setBounds(320, 320, 80, 80);
        panel.add(s1);
*/
        tagb = new JButton("Tag");
        tagb.setBounds(770,0,100,50);
        tagb.addActionListener(this);
        panel.add(tagb);
      
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