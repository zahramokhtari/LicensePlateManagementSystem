package server;

import common.pelak;
import common.user;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * this class do gets an input from server console and does its offer
 */
class work extends Thread{
    Scanner scanner = new Scanner(System.in);
    private pelak pelak; // for giving next plate we give this field at first that this class is beginning
    public work(pelak pelak){
        this.pelak = pelak;
    }
    @Override
    public void run(){
        while (true){
            String work = scanner.nextLine();
            if (work.equals("OnlineUsers")){//this part returns a user if user state is online
                int state = 0;
                for (int i = 0; i < server.user.size(); i++) {
                    if (server.user.get(i).isOnline()){
                        state++;
                        System.out.print(server.user.get(i).getId()+" : ");
                        if (server.user.get(i).getPelak().equals("default")){
                            System.out.println("this id has no plate");
                        }
                        else System.out.println("plate is "+server.user.get(i).getPelak());
                    }
                }
                if (state==0){
                    System.out.println("no online user");
                }
            }
            else if (work.equals("RegisteredUsers")){ // this part returns all registered users
                if (server.user.size()==0){
                    System.out.println("there is no registered user");
                }
                else {
                    for (int i = 0; i < server.user.size(); i++) {
                        System.out.print(server.user.get(i).getId()+" : ");
                        if (server.user.get(i).getPelak().equals("default")){
                            System.out.println("this id has no plate");
                        }
                        else System.out.println("plate is "+server.user.get(i).getPelak());
                    }
                }
            }
            else if (work.equals("next")){//this part returns the plate that will be gave to next user that wants a plate
                System.out.println(pelak.getNext());
            }
        }

    }
}
public class server {
    public static Vector<user> user = new Vector<>();//this field is a list of all users that has registered in system
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        pelak pelak = new pelak();

        work work = new work(pelak);
        work.start();//for offer in the server console

        while ( true ) {
            Socket socket = serverSocket.accept();
            ClientHandler temp = new ClientHandler(socket,pelak);
            ( new Thread( temp ) ).start();
        }

    }
}