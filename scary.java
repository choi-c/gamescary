import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.*;

public class scary implements ActionListener{
    // Properties
    JFrame frame;
    JPanel panel;
    JTextArea chat = new JTextArea();
    JPanel startPanel;
    JPanel characterPanel;
    JButton connectb,tagb;
    JTextField ip,port,name;
    
    //SuperSocketMaster ssm;
    BufferedImage imgHome;

    // Methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == connectb){
            System.out.println(name.getText());
            if(ip.getText() != null && port.getText() != null){
                frame.setContentPane(panel);
                frame.validate();
            } else{
                System.out.println("Enter ip, port number and/or name to connect");
            }
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
        characterPanel = new JPanel();
        characterPanel.setLayout(null);
        characterPanel.setPreferredSize(new Dimension(1280, 720));

         

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