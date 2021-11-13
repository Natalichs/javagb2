package Chat.Server;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        try(ServerSocket serverSocket = new ServerSocket(8889)){
            System.out.println("Server wait for connection...");
            socket = serverSocket.accept();
            System.out.println("Client connected");
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Socket finalSocket = socket;
            Thread thread1 = new Thread(() -> {
                while (true){
                    Scanner scanner = new Scanner(System.in);
                    String textFromServer = scanner.nextLine();
                    if(textFromServer.equals("/end")){
                        try {
                            dataOutputStream.writeUTF(textFromServer);
                            break;
                        }catch(Exception e){

                        }
                    }
                    try {
                        dataOutputStream.writeUTF("ServerEcho: " + textFromServer);
                        System.out.println("Server: "+ textFromServer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    dataOutputStream.writeUTF("Connection broken from server");
                    System.out.println("Connection broken from server");
                    try{
                        dataOutputStream.close();
                    }catch (Exception e){
                    }
                    try{
                        dataInputStream.close();
                    }catch (Exception e){
                    }
                    try{
                        finalSocket.close();
                    }catch (Exception e){
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });



            Thread thread2 = new Thread(() -> {
                while (true) {
                    try {
                        String message = dataInputStream.readUTF();
                        if (message.equals("/end")) {
                            dataOutputStream.writeUTF(message);
                            break;
                        }
                        dataOutputStream.writeUTF("ClientEcho: " + message);
                        System.out.println("Client: "+ message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Connection broken from Client");
                try{
                    dataOutputStream.close();
                }catch (Exception e){
                }
                try{
                    dataInputStream.close();
                }catch (Exception e){
                }
                try{
                    finalSocket.close();
                }catch (Exception e){
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread1.join();

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}
