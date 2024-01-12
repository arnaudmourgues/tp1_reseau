package com.example.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    private Socket s;

    public ClientTCP(String host, int port) throws Exception {
        try {
            s = new Socket(host, port);
        } catch (Exception e) {
            throw new Exception("Erreur de connexion au serveur");
        }
    }

    public void check() throws Exception {
        String chaine = getIds();

        BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream sortieSocket = new PrintStream(s.getOutputStream());

        chaine = "CHK " + chaine;

        sortieSocket.println(chaine);

        chaine = entreeSocket.readLine();
        System.out.println(chaine);

        if(!chaine.equals("FIN")) check();

    }

    private String getIds() throws IOException {
        String chaine = "";
        // Scanner sur System.in
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez votre couple login/mot de passe comme suit : <<login password>>." + "\n");
        chaine = scanner.nextLine();
        return chaine;
    }
}
