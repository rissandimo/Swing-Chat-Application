import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The purpose of Client is to intent to connect to a Server and send messages, which in turn will relay the
 * message back to the client.
 * @author Omid Nassir
 */
public class Client
{
    public static void main(String[] args) {

        ClientFrame clientFrame=new ClientFrame();

        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/**
 * Constructs a JFrame and adds ClientPanel to it
 * Then it registers NotifyActiveStatus with window listener to notify Server that this client is online
 */
class ClientFrame extends JFrame{

    ClientFrame(){

        setBounds(600,300,270,370);

        setTitle("Client");

        ClientPanel panel=new ClientPanel();

        add(panel);

        setVisible(true);
    }
}

/**
 * JPanel which is a runnable and defines the various components for the JFrame.
 */
class ClientPanel extends JPanel implements Runnable
{
    private JTextField messageField;
    private JTextField nameField;
    private JTextField ipAddressField;
    private JTextArea textArea;

    ClientPanel()
    {
        String clientName = JOptionPane.showInputDialog("Please enter your name");

        //Name
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        nameField.setEditable(false);
        nameField.setText(clientName);
        add(nameLabel);
        add(nameField);

        //Ip Address
        JLabel ipAddressLabel = new JLabel("Host Ip Address");
        ipAddressField = new JTextField(10);
        add(ipAddressLabel);
        add(ipAddressField);

        //Text area
        textArea = new JTextArea(14,20);
        textArea.setEditable(false);
        add(textArea);

        //Message
        messageField = new JTextField(15);
        add(messageField);

        //Button
        JButton submitButton = new JButton("Send");
        submitButton.addActionListener(new SendTextListener());
        add(submitButton);

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Listens for and accepts incoming requests from the Server to display message relayed back to it
     */
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

    /**
     * Attempts to connect to Server upon 'send' and send messages
     */
    class SendTextListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Socket socket = new Socket(ipAddressField.getText(), 5555);

                //Encapsulate client data
                ClientInformation clientInformation = new ClientInformation();
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

/**
 * Encapsulates client information to be send via Object Output Stream
 */
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