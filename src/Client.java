import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

        setBounds(600,300,280,375);

        setTitle("Client");

        ClientPanel panel=new ClientPanel();

        add(panel);

        setVisible(true);
    }
}

class ClientPanel extends JPanel
{
    private JLabel nameLabel, ipAddressLabel;
    private JTextField messageField, nameField, ipAddressField;
    private JTextArea textArea;

    private JButton submitButton;


    public ClientPanel()
    {
        nameLabel   = new JLabel("Name:");
        nameField = new JTextField(15);
        add(nameLabel);
        add(nameField);

        ipAddressLabel = new JLabel("Ip Address");
        ipAddressField = new JTextField(15);
        add(ipAddressLabel);
        add(ipAddressField);

        JLabel textLabel=new JLabel("Message");
        add(textLabel);

        textArea = new JTextArea(14,20);
        textArea.setEditable(false);
        add(textArea);

        messageField = new JTextField(15);
        add(messageField);

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

                ClientInformation clientInformation = new ClientInformation();
                clientInformation.setClientIpAddress(ipAddressField.getText());
                clientInformation.setClientName(nameField.getText());
                clientInformation.setClientMessage(messageField.getText());

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(clientInformation);
                socket.close();
            }
            catch (IOException e1)
            {
                System.out.println(e1.getMessage());
            }
        }
    }
}


class ClientInformation implements Serializable
{
    private String clientName, clientIpAddress, clientMessage;

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public String getClientIpAddress()
    {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress)
    {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientMessage()
    {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage)
    {
        this.clientMessage = clientMessage;
    }
}