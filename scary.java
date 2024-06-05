import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class scary implements ActionListener, KeyListener{
    // Properties
    JFrame frame;
    school panel;
    JTextArea chat = new JTextArea();
    start startPanel;
    characterselect characterPanel;
    JButton connectb,tagb;
    JTextField ip = null,port = null,name = null;
    JLabel seekers,hiders, selectName;
    JButton char1,char2,char3,char4, lockIn;
    String strName = null, strSelect;

    //SuperSocketMaster ssm;

    // Methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == connectb){
            if(!strName.equals(null)){
                if(!ip.getText().equals(null) && !port.getText().equals(null)){
                    System.out.println("connect as player");
                }else if (ip.getText().equals(null) && !port.getText().equals(null)){
                    System.out.println("connect as host");
                }else{
                    System.out.println("Enter ip, port, or name");
                }
                System.out.println(strName);
                frame.setContentPane(characterPanel);
                frame.validate();
            }
        }else if(evt.getSource() == name){
            strName = name.getText();
        }else if(evt.getSource() == char1){
            char1.setEnabled(false);
            char2.setEnabled(true);
            char3.setEnabled(true);
            char4.setEnabled(true);
            selectName.setBounds(75,0,200,100);
            strSelect.equals("seeker1");
            System.out.println("strSelect");
            frame.validate();
        }else if(evt.getSource() == char2){
            char1.setEnabled(true);
            char2.setEnabled(false);
            char3.setEnabled(true);
            char4.setEnabled(true);
            selectName.setBounds(380,0,200,100);
            strSelect.equals("seeker2");
            System.out.println("strSelect");
            frame.validate();
        }else if(evt.getSource() == char3){
            char1.setEnabled(true);
            char2.setEnabled(true);
            char3.setEnabled(false);
            char4.setEnabled(true);
            selectName.setBounds(700,0,200,100);
            strSelect.equals("hider1");
            System.out.println("strSelect");
            frame.validate();
        }else if(evt.getSource() == char4){
            char1.setEnabled(true);
            char2.setEnabled(true);
            char3.setEnabled(true);
            char4.setEnabled(false);
            selectName.setBounds(1020,0,200,100);
            strSelect.equals("hider2");
            System.out.println("strSelect");
            frame.validate();
        }else if(evt.getSource() == lockIn){
            System.out.println("playing as "+strSelect);
            frame.setContentPane(panel);
            frame.validate();
        }
    }
    public void keyPressed(KeyEvent evt){

    }
    public void keyReleased(KeyEvent evt){

    }
    public void keyTyped(KeyEvent evt){
        if(evt.getKeyChar() == 's' || evt.getKeyChar() == 'S'){
			
		}else if(evt.getKeyChar() == 'w' || evt.getKeyChar() == 'W'){

        }else if(evt.getKeyChar() == 'a' || evt.getKeyChar() == 'A'){
	
		}else if(evt.getKeyChar() == 'd' || evt.getKeyChar() == 'D'){

        }
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

        connectb = new JButton("Connect");
        connectb.setBounds(800,550,300,100);
        connectb.addActionListener(this);
        startPanel.add(connectb);
        
        // character select panel (only for players)
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