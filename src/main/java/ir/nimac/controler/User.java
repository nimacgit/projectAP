package ir.nimac.controler;

import ir.nimac.controler.client.Client;
import ir.nimac.model.Alevel.SynchronizeDataOutPut;
import ir.nimac.model.Clevel.Player;

import java.io.DataOutputStream;
import java.net.Socket;

public class User {
    private boolean isOnline = false;
    private Player player;
    private Client client;
    private boolean isWatcher = false;

    public User(){

    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isWatcher() {
        return isWatcher;
    }

    public void setWatcher(boolean watcher) {
        isWatcher = watcher;
    }
}
