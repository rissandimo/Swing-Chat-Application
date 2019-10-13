import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args) {

        Frame frame =new Frame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

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
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                clientInformation = (ClientInformation) objectInputStream.readObject();
                System.out.println("Client ip address: " + clientInformation.getClientIpAddress());
                textArea.append(
                        clientInformation.getClientName() + ": " +
                        clientInformation.getClientMessage() + "\n");


            //Relay message to client
                Socket clientSocketRelay = new Socket("192.168.1.1", 9999);
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
