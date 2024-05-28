package common;

import server.ClientHandler;

public class user{
    private boolean isOnline = false;//shows that a user is online in a client a using it or no
    private ClientHandler clientHandler;
    private String name;
    private String lastName;
    private String id;
    private String pelak = "default";// at first user does not have plate
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setPelak(String pelak) {
        this.pelak = pelak;
    }
    public String getPelak() {
        return pelak;
    }
    public boolean isOnline() {
        return isOnline;
    }
    public void setOnline(boolean online) {
        isOnline = online;
    }
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}