package com.example.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientUDP {
    private DatagramSocket s;
    private byte[] tampon = new byte[1024];
    InetAddress adress;
    private int portDestination;

    public ClientUDP(String host, int portDestination) throws Exception {
        adress = InetAddress.getByName(host);
        s = new DatagramSocket();
        this.portDestination = portDestination;
    }

    public void check() throws Exception {
        String chaine = getIds();
        chaine = "CHK " + chaine;
        //préparation du paquet à envoyer
        DatagramPacket paquet = new DatagramPacket(chaine.getBytes(), chaine.getBytes().length, adress, portDestination);
        s.send(paquet);

        //recevoir la réponse
        paquet = new DatagramPacket(tampon, tampon.length);
        s.receive(paquet);

        //on récupère la réponse dans une chaine
        chaine = new String(paquet.getData(), 0, paquet.getLength());

        System.out.println(chaine);

        if(!chaine.equals("FIN")) check();
    }
        private String getIds () throws IOException {
            String chaine = "";
            // Scanner sur System.in
            Scanner scanner = new Scanner(System.in);
            System.out.println("Tapez votre couple login/mot de passe comme suit : <<login password>>." + "\n");
            chaine = scanner.nextLine();
            return chaine;
        }
    }
