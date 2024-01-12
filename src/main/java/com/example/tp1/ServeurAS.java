package com.example.tp1;

import java.net.Socket;

public class ServeurAS {
    private ServeurUDP su;
    private ServeurTCP st;
    private ServeurManagerTCP sm;

    public ServeurAS(int port) throws Exception {
        su = new ServeurUDP(port);
        st = new ServeurTCP(port);
        sm = new ServeurManagerTCP(port);
    }

    public void run() throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                su.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                st.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                sm.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        System.out.println("Serveur UDP lancé sur le port : " + su.getPort());
        t2.start();
        System.out.println("Serveur TCP lancé sur le port : " + st.getPort());
        t3.start();
        System.out.println("Serveur Manager TCP lancé sur le port : " + sm.getPort());
    }
}
