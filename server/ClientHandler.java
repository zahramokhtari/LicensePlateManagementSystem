package server;

import common.user;
import common.pelak;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * this class manage user offers
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private pelak pelak;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isInUse = false;

    public ClientHandler(Socket socket, pelak pelak) throws Exception {
        this.socket = socket;
        this.pelak = pelak;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

        try {
            String MainWork = dis.readUTF();
            String name = "";
            String lastName = "";
            if (MainWork.equals("NewUser")||MainWork.equals("Login")) {
                if (MainWork.equals("NewUser")){
                    name = dis.readUTF();
                    lastName = dis.readUTF();
                }
                if (isInUse){
                    String okMessage = "sorry... another id is using this client!";
                    this.dos.writeUTF(okMessage);
                }
                else {
                    boolean isUsernameOk = false;
                    String id = "";
                    while (!isUsernameOk) {
                        id = this.dis.readUTF();
                        boolean exist = false;
                        for (int i = 0; i < server.user.size(); i++) {
                            if (server.user.get(i).getId().equals(id)){
                                exist = true;
                            }
                        }
                        if (exist) {
                            if (MainWork.equals("NewUser")){
                                String errorMessage = "err: UserName already exists... please enter another one: ";
                                this.dos.writeUTF(errorMessage);
                                MainWork = dis.readUTF();
                            }
                            else if (MainWork.equals("Login")){
                                /**
                                 * two users can not use one client
                                 */
                                if (this.isInUse){
                                    String okMessage = "sorry... another id is using this client";
                                    this.dos.writeUTF(okMessage);
                                    MainWork = dis.readUTF();
                                }
                                int state = 0;//state == 0 means user that wants to login is not online and using another client
                                //if state == 1 means user that wants to login is using another client
                                for (int i = 0; i < server.user.size(); i++) {
                                    if (server.user.get(i).getId().equals(id))
                                        if (server.user.get(i).isOnline()){
                                            String okMessage = "this id is online in another client";
                                            this.dos.writeUTF(okMessage);
                                            MainWork = dis.readUTF();
                                            state = 1;
                                        }
                                }
                                if (state == 0){
                                    String okMessage = "OK: Username was OK.";
                                    this.dos.writeUTF(okMessage);
                                    isUsernameOk = true;
                                    isInUse = true;
                                }
                            }
                        } else {
                            if (MainWork.equals("NewUser")){
                                String okMessage = "OK: Username was OK.";
                                this.dos.writeUTF(okMessage);
                                isUsernameOk = true;
                                isInUse = true;
                            }
                            else if (MainWork.equals("Login")){
                                String errorMessage = "err: No User With This id";
                                this.dos.writeUTF(errorMessage);
                                MainWork = dis.readUTF();
                            }
                        }
                    }

                    if (MainWork.equals("NewUser")||MainWork.equals("Login")){
                        /**
                         * makes an user with given information from server
                         */
                        if (MainWork.equals("NewUser")){
                            user user = new user();
                            user.setName(name);
                            user.setLastName(lastName);
                            user.setId(id);
                            user.setClientHandler(this);
                            user.setOnline(true);
                            server.user.add(user);
                        }
                        else if (MainWork.equals("Login")){
                            for (int i = 0; i < server.user.size(); i++) {
                                if (server.user.get(i).getId().equals(id)){
                                    server.user.get(i).setOnline(true);
                                }
                            }
                        }
                        System.out.println("User " + id + " Connected!");//is said to server
                    }

                    /**
                     * always is ready to take user that is using client , offers
                     */
                    while (true) {
                        String work = this.dis.readUTF();//takes offer
                        String Message;
                        /**
                         * offer is checked in different ifs to handle it
                         */
                        if (work.equals("GetPlate")){
                            for (int i = 0; i < server.user.size(); i++) {
                                if (server.user.get(i).getId().equals(id)){
                                    if (server.user.get(i).getPelak().equals("default")){
                                        boolean isOk = false;
                                        while (!isOk){
                                            int state = 0;
                                            String plate = pelak.getPelak();
                                            for (int j = 0; j < server.user.size(); j++) {
                                                if (server.user.get(j).getPelak().equals(plate)){
                                                    state = 1;
                                                }
                                            }
                                            if (state==0){
                                                server.user.get(i).setPelak(plate);
                                                Message = "Plate : " + plate;
                                                this.dos.writeUTF(Message);
                                                isOk = true;
                                            }
                                        }
                                    }
                                    else {
                                        Message = "you have one";
                                        this.dos.writeUTF(Message);
                                    }
                                }
                            }
                        }
                        else if (work.equals("Transferplate")){
                            String target = dis.readUTF();//gives target user
                            int state = 0;//just check that there is a user with target id or no , 0 means there is not such user
                            Message = "";
                            for (int i = 0; i < server.user.size(); i++) {
                                if (server.user.get(i).getId().equals(target)){
                                    if (!server.user.get(i).getPelak().isEmpty()){
                                        Message = "this id has a plate";
                                    }
                                    else {
                                        for (int j = 0; j < server.user.size(); j++) {
                                            if (server.user.get(j).getId().equals(id)){
                                                if (server.user.get(j).getPelak().equals("default")){
                                                    Message = "you have no plate";//a user that has not plate can not use this offer
                                                }
                                                else {
                                                    server.user.get(i).setPelak(server.user.get(j).getPelak());
                                                    server.user.get(j).setPelak("default");//user plate is removed
                                                }
                                            }
                                        }
                                        Message = "plate transferd";
                                    }
                                    state = 1;
                                }
                            }
                            if (state==0){
                                Message = "there is no user with this id";
                            }

                            this.dos.writeUTF(Message);
                        }
                        else if (work.equals("Login")||work.equals("NewUser")){
                            if(MainWork.equals("NewUser")||MainWork.equals("Login")){
                                Message = "another id is using this client";
                                this.dos.writeUTF(Message);
                            }
                        }
                        else if (work.equals("Logout")){
                            for (int i = 0; i < server.user.size(); i++) {
                                if (server.user.get(i).getId().equals(id)){
                                    server.user.get(i).setOnline(false);
                                    this.isInUse = false;
                                }
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}