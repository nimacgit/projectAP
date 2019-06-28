package ir.nimac.controler.client;

import ir.nimac.controler.Core;
import ir.nimac.model.map.Map;
import ir.nimac.view.MainFrame;
import ir.nimac.view.MapPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;


public class Client {

    private final Object lock = new Object();
    private ServerHandler serverHandler;
    private String name;
    private Thread serverHandlerThread;

    public Client(String name, String host, int port, int width, int heigth, Map map, int mod) {
        map.getMe().setOnline(true);
        map.getMe().setClient(this);
        if(name.length() >= 5 && name.substring(0, 5).equals("watch")) {
            mod = 2;
            map.getMe().setWatcher(true);
        }
        this.name = name;
        serverHandler = new ServerHandler(name, host, port, width, heigth, this, map, mod);
        serverHandlerThread = new Thread(serverHandler);
        if(mod == 0) {
            serverHandlerThread.start();
        }
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public String getName() {
        return name;
    }

    public Thread getServerHandlerThread() {
        return serverHandlerThread;
    }
}