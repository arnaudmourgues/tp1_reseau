package com.example.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurManagerTCP {
    private ServerSocket ssg;
    private ListeAuth la;

    public ServeurManagerTCP(int port) throws IOException {
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
            if (!chaine.matches("(CHK|ADD|DEL|MOD) [a-zA-Z0-9]{1,2047} [a-zA-Z0-9]{1,2047}")) {
                sortieSocket.println("ERROR request_malformed");
            } else {
                String operation = chaine.split(" ")[0];
                String login = chaine.split(" ")[1];
                String passwd = chaine.split(" ")[2];
                switch (operation) {
                    case "ADD":
                        if (add(login, passwd)) {
                            sortieSocket.println("DONE");
                        } else {
                            sortieSocket.println("ERROR login_already_exists");
                        }
                        break;
                    case "CHK":
                        if (check(login, passwd)) {
                            sortieSocket.println("GOOD");
                        } else {
                            sortieSocket.println("BAD");
                        }
                        break;
                    case "MOD":
                        if (update(login, passwd)) {
                            sortieSocket.println("DONE");
                        } else {
                            sortieSocket.println("ERROR login_unknown");
                        }
                        break;
                    case "DEL":
                        if (delete(login, passwd)) {
                            sortieSocket.println("DONE");
                        } else {
                            sortieSocket.println("ERROR login_unknown");
                        }
                        break;
                    default:
                        sortieSocket.println("ERROR operation_unknown");
                        break;
                }
            }
        }
        sss.close();
    }

    private boolean delete(String login, String passwd) {
        return la.supprimer(login, passwd);
    }

    private boolean update(String login, String passwd) {
        return la.mettreAJour(login, passwd);
    }

    private boolean check(String login, String passwd) {
        return la.tester(login, passwd);
    }

    private boolean add(String login, String passwd) {
        return la.creer(login, passwd);
    }

    public int getPort() {
        return ssg.getLocalPort();
    }
}
