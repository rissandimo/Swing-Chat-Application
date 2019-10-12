import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            ServerSocket serverSocket = new ServerSocket(5555);
            ClientInformation clientInformation = null;

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                clientInformation = (ClientInformation) objectInputStream.readObject();
                textArea.append(clientInformation.getClientName() + ": " + clientInformation.getClientMessage() + "\n");
                clientSocket.close();
            }
        }
        catch (IOException | ClassNotFoundException exception)
        {
            exception.printStackTrace();
        }
    }
}
