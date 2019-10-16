import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The purpose of Server is to accept incoming requests from Clients and to relay messages received from them.
 */
public class Server
{
    public static void main(String[] args) {

        Frame frame =new Frame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/**
 * Frame is a runnable and it defines the various components for the JFrame.
 */
class Frame extends JFrame implements Runnable
{


    private JTextArea textArea;

    public Frame()
    {
        setBounds(1200, 300, 280, 350);

        setTitle("Server");

        JPanel jpanel = new JPanel();

        jpanel.setLayout(new BorderLayout());

        textArea = new JTextArea();

        jpanel.add(textArea, BorderLayout.CENTER);

        add(jpanel);

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }
    /**
     * Accept incoming requests from client, display them in text area and relay message back to them.
     */
    @Override
    public void run()
    {
        try
        {
            //Open connections
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("Sever waiting for incoming connections");
            ClientInformation clientInformation;

            //Accept connections and display message from client
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connection accepted");

                String clientIpAddress = clientSocket.getInetAddress().getHostAddress();

                //Create output stream
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                clientInformation = (ClientInformation) objectInputStream.readObject();
                textArea.append(
                        clientInformation.getClientName() + ": " +
                        clientInformation.getClientMessage() + "\n");


            //Relay message to client
                Socket clientSocketRelay = new Socket(clientIpAddress, 9999);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocketRelay.getOutputStream());
                objectOutputStream.writeObject(clientInformation);
                System.out.println("Relay sent to client");

            // Close sockets
                objectOutputStream.close();
                clientSocketRelay.close();
                clientSocket.close();
            }
        }
        catch (IOException | ClassNotFoundException exception)
        {
            exception.printStackTrace();
        }
    }
}
