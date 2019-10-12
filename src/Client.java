import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
        submitButton.addActionListener(new SendTextListener());

        add(submitButton);
    }

    private class SendTextListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Socket socket = new Socket("192.168.1.2", 5555);
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(textField.getText());
                outputStream.close();

            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

}