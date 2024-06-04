import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;

public class scary implements ActionListener{
    // Properties
    JFrame frame;
    JPanel panel;
    JTextArea chat = new JTextArea();
    JPanel startPanel;
    characterselect characterPanel;
    JButton connectb,tagb;
    JTextField ip,port,name;
    JLabel yourSide,teamSide;
    BufferedImage yourPlayer1,yourPlayer2,teamPlayer1,teamPlayer2;
    JButton yourChoice1,yourChoice2,teamChoice1,teamChoice2, lockIn;
    
    //SuperSocketMaster ssm;
    BufferedImage imgHome;

    // Methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == connectb){
            System.out.println(name.getText());
            if(ip.getText() != null && port.getText() != null){
                frame.setContentPane(characterPanel);
                frame.validate();
            } else{
                System.out.println("Enter ip, port number and/or name to connect");
            }
        }
        else if(evt.getSource() == lockIn){
            frame.setContentPane(panel);
            frame.validate();
        } 
    }

    // Constructor
    public scary(){
        frame = new JFrame("Scary Hide & seek");

        // setting up start screen
        startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setPreferredSize(new Dimension(1280,720));

        // loading home screen image
        try{
            imgHome = ImageIO.read(new File("home.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }
        

        // adding text fields for ip, port number and user name
        ip = new JTextField();
        ip.setBounds(50,300,200,100);
        ip.setText(null);
        startPanel.add(ip);
        port = new JTextField();
        port.setBounds(50,450,200,100);
        port.setText(null);
        startPanel.add(port);
        name = new JTextField(null);
        name.setBounds(50,600,200,100);
        //name.setText(null);
        startPanel.add(name);

        connectb = new JButton("Connect");
        connectb.setBounds(300,300,300,100);
        connectb.addActionListener(this);
        startPanel.add(connectb);
        
        // character select panel (only for players)
        characterPanel = new characterselect();
        characterPanel.setLayout(null);
        characterPanel.setPreferredSize(new Dimension(1280, 720));

        yourSide = new JLabel("Your Character");
        yourSide.setSize(640,100);
        yourSide.setLocation(275,0);
        characterPanel.add(yourSide);

        teamSide = new JLabel("Other Player's Character");
        teamSide.setSize(640,100);
        teamSide.setLocation(883,0);
        characterPanel.add(teamSide);

        yourChoice1 = new JButton("1");
        yourChoice1.setSize(160,50);
        yourChoice1.setLocation(80,500);
        yourChoice1.addActionListener(this);
        characterPanel.add(yourChoice1);

        yourChoice2 = new JButton("2");
        yourChoice2.setSize(160,50);
        yourChoice2.setLocation(400,500);
        yourChoice2.addActionListener(this);
        characterPanel.add(yourChoice2);

        teamChoice1 = new JButton("1");
        teamChoice1.setSize(160,50);
        teamChoice1.setLocation(720, 500);
        teamChoice1.addActionListener(this);
        characterPanel.add(teamChoice1);

        teamChoice2 = new JButton("2");
        teamChoice2.setSize(160,50);
        teamChoice2.setLocation(1040,500);
        teamChoice2.addActionListener(this);
        characterPanel.add(teamChoice2);

        lockIn = new JButton("LOCK IN");
        lockIn.setSize(160,50);
        lockIn.setLocation(560, 600);
        lockIn.addActionListener(this);
        characterPanel.add(lockIn);
        characterPanel.repaint();

        try{
            yourPlayer1 = ImageIO.read(new File("hider1.png"));
        }catch(IOException e){
            System.out.println("Unable to load image");
        }

        // The actual game
        panel = new JPanel();
        panel.setLayout(null);

        chat.setBounds(920,0,720,1280);
        panel.add(chat);

        tagb = new JButton("Tag");
        //tagb.setBounds()
      
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