import javax.swing.*;
import java.awt.*;

public class Server
{
    public static void main(String[] args) {

        Frame frame =new Frame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Frame extends JFrame
{

    private	JTextArea textArea;

    public Frame(){

        setBounds(1200,300,280,350);

        setTitle("Server");

        JPanel jpanel= new JPanel();

        jpanel.setLayout(new BorderLayout());

        textArea =new JTextArea();

        jpanel.add(textArea,BorderLayout.CENTER);

        add(jpanel);

        setVisible(true);

    }


}
