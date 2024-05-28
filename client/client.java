package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8888);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());//gives information to socket
        DataInputStream dis = new DataInputStream(socket.getInputStream());//takes information from socket
        Scanner in = new Scanner(System.in);
         while (true){
            String state = in.next();//state is the request that a user writes in the client console
            dos.writeUTF(state);//gives user request to server
            if (state.equals("NewUser")||state.equals("Login")){
                if (state.equals("NewUser")){
                    String name = in.next();
                    dos.writeUTF(name);
                    String lastName = in.next();
                    dos.writeUTF(lastName);
                }
                boolean isUsernameOk = false;//if this field equals false user cant login in to the server
                while (!isUsernameOk) {
                    String username = in.nextLine();
                    dos.writeUTF(username);
                    String userValidation = dis.readUTF();//gives username to sever to check it
                    if (userValidation.startsWith("OK")) {
                        System.out.println("you are connected");
                        isUsernameOk = true;
                    } else{
                        if (state.equals("NewUser")){
                            System.out.println(userValidation);
                            state = in.next();
                            dos.writeUTF(state);
                        }
                        else if (state.equals("Login")){
                            System.out.println(userValidation);
                            state = in.next();
                            dos.writeUTF(state);
                        }
                    }

                }
                while (true) {
                    Thread messageListener = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                String message = dis.readUTF();//takes request answer to print it in the console
                                System.out.println(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    messageListener.start();

                    String work = in.next();//always an offer takes from user
                    if (work.equals("Transferplate")){
                        String target = in.next();
                        dos.writeUTF(target);
                    }
                    else if (work.equals("Logout")){
                        System.out.println("you are logout");
                        dos.writeUTF("Logout");
                        break;
                    }
                    else
                        dos.writeUTF(work);//offer send to server for answering to it
                }
            }
        }
    }
}