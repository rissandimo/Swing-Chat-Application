import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
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

            while (true)
            {
                System.out.println("Waiting to receive a connection");
                Socket clientSocket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                String messageFromClient = inputStream.readUTF();
                textArea.append(messageFromClient + "\n");
                clientSocket.close();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
