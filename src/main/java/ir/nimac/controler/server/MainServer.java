package ir.nimac.controler.server;

import ir.nimac.model.map.Map;
import ir.nimac.view.ServerFrame;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {
    public static void main(String args[]) {
        int port = 2020;
        Scanner inp = new Scanner(System.in);
        System.out.println("write the port");
        port = inp.nextInt();
        Server server = new Server(port);
    }
}
