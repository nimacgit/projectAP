package ir.nimac.controler;

import ir.nimac.controler.client.SoundPlayer;

import java.util.Scanner;

public class SoundPlayerTest {

    @org.junit.Test
    public void getInstance() {
    }

    @org.junit.Test
    public void playSound() {
        SoundPlayer.getInstance().playSound("src/main/resources/yann.mp3");
        Scanner inp = new Scanner(System.in);
        int a = inp.nextInt();
    }

    @org.junit.Test
    public void playContinuesSound() {
        SoundPlayer.getInstance().playContinuessSound("src/main/resources/yann.mp3");
        Scanner inp = new Scanner(System.in);
        int a = inp.nextInt();
    }
}