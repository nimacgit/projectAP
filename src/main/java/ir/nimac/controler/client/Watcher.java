package ir.nimac.controler.client;

import ir.nimac.model.map.Map;

import java.util.Scanner;

public class Watcher {

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        System.out.println("host:");
        String host = inp.next();
        System.out.println("ip:");
        int port = inp.nextInt();
        Map map = new Map();
        Client client = new Client("temp",host, port, 0, 0, map, 2);

    }


}
