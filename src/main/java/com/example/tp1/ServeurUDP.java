package com.example.tp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUDP {
    private DatagramSocket ssg;
    private byte[] tampon;
    private DatagramPacket paquet;
    private ListeAuth la;

    public ServeurUDP(int port) throws Exception {
        ssg = new DatagramSocket(port);
        la = new ListeAuth("authentif");
        tampon = new byte[1024];
    }

    public void run() throws Exception {
        while (true) {
            String chaine = "";
            paquet = new DatagramPacket(tampon, tampon.length);
            ssg.receive(paquet);
            chaine = new String(paquet.getData(), 0, paquet.getLength());
            if(chaine.equals("FIN")) break;
            if (!chaine.matches("CHK [a-zA-Z0-9]{1,2047} [a-zA-Z0-9]{1,2047}")) {
                chaine = "ERROR request_malformed";
            } else {
                String login = chaine.split(" ")[1];
                String passwd = chaine.split(" ")[2];
                if (check(login, passwd)) {
                    chaine = "GOOD";
                } else {
                    chaine = "BAD";
                }
            }
            paquet.setData(chaine.getBytes());
            ssg.send(paquet);
        }
        //close the socket
        ssg.close();
    }

    public boolean check(String login, String passwd) {
        return la.tester(login, passwd);
    }

    public int getPort() {
        return ssg.getLocalPort();
    }
}
