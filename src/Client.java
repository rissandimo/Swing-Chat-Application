import javax.swing.*;

public class Client
{

    public static void main(String[] args) {

        ClientFrame clientFrame=new ClientFrame();

        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}


class ClientFrame extends JFrame{

    public ClientFrame(){

        setBounds(600,300,280,350);

        setTitle("Client");

        ClientPanel panel=new ClientPanel();

        add(panel);

        setVisible(true);
    }

}

class ClientPanel extends JPanel
{

    private JTextField textField;

    private JButton submitButton;


    public ClientPanel(){

        JLabel textLabel=new JLabel("Client");

        add(textLabel);

        textField =new JTextField(20);

        add(textField);

        submitButton =new JButton("Send");

        add(submitButton);
    }

}