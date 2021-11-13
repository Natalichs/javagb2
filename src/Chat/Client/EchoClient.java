package Chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClient extends JFrame {
    private  final String SERVER_ADRESS = "localhost";
    private final int SERVER_PORT = 8889;

    private JTextArea textArea;
    private JTextField textField;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public EchoClient(){
        try{
            openConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        prepareUI();
    }

    private void openConnection() throws IOException {
        socket = new Socket(SERVER_ADRESS,SERVER_PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        String messageFromServer = dataInputStream.readUTF();
                        if(messageFromServer.equals("/end")){
                            break;
                        }
                        textArea.append(messageFromServer + "\n");
                    }
                    textArea.append("Connection broken");
                    textField.setEnabled(false);
                    closeConnection();

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    private void closeConnection(){
        try{
            dataOutputStream.close();
        }catch (Exception e){
        }
        try{
            dataInputStream.close();
        }catch (Exception e){
        }
        try{
            socket.close();
        }catch (Exception e){
        }
    }


    private  void sendMessage(){
        if (textField.getText().trim().isEmpty()){
            return;
        }
        try{
            dataOutputStream.writeUTF(textField.getText());
            textField.setText("");
            textField.grabFocus();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    private void prepareUI(){
        setBounds(200,200,500,500);
        setTitle("EchoClient");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton("Send");
        panel.add(button,BorderLayout.EAST);
        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);

        add(panel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoClient::new);
    }
}
