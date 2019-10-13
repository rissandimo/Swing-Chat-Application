import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
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

class ClientPanel extends JPanel implements Runnable
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

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket serverClient = new ServerSocket(9999);
            Socket socket;
            ClientInformation clientInformationReceived;

            while(true)
            {
                socket = serverClient.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                clientInformationReceived = (ClientInformation) objectInputStream.readObject();
                textArea.append(
                        clientInformationReceived.getClientName() + ": " +
                        clientInformationReceived.getClientMessage() + "\n");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private class SendTextListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                System.out.println("Ip address of host:" + ipAddressField.getText());
                //Create connection to socket
                Socket socket = new Socket(ipAddressField.getText(), 5555);

                //Obtain client and message info
                ClientInformation clientInformation = new ClientInformation();
                clientInformation.setClientIpAddress(ipAddressField.getText());
                clientInformation.setClientName(nameField.getText());
                clientInformation.setClientMessage(messageField.getText());

                //Create output stream and send message to server
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
    //Ip used for Server to create server socket to Client to relay message back to client
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