package com.example.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP {
    private ServerSocket ssg;
    private ListeAuth la;

    public ServeurTCP(int port) throws IOException {
        ssg = new ServerSocket(port);
        la = new ListeAuth("authentif");
    }

    public void run() throws IOException {
        Socket sss = ssg.accept();

        BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));
        // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
        PrintStream sortieSocket = new PrintStream(sss.getOutputStream());

        String chaine = "";

        while (!chaine.equals("FIN")) {
            // lecture d'une chaine envoyée à travers la connexion socket
            chaine = entreeSocket.readLine();

            //check if the request is well-formed using regex
            if (!chaine.matches("CHK [a-zA-Z0-9]{1,2047} [a-zA-Z0-9]{1,2047}")) {
                sortieSocket.println("ERROR request_malformed");
            } else {
                String login = chaine.split(" ")[1];
                String passwd = chaine.split(" ")[2];
                if (check(login, passwd)) {
                    sortieSocket.println("GOOD");
                } else {
                    sortieSocket.println("BAD");
                }
            }
        }
        sss.close();
    }

    private boolean check(String login, String passwd) {
        return la.tester(login, passwd);
    }

    public int getPort() {
        return ssg.getLocalPort();
    }
}
