package com.example.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ManagerTCP {
    private Socket s;

    public ManagerTCP(String host, int port) throws Exception {
        try {
            s = new Socket(host, port);
        } catch (Exception e) {
            throw new Exception("Erreur de connexion au serveur");
        }
    }

    public void check() throws Exception {
        String opere = "";
        int operation = getOperation();
        switch (operation) {
            case 1:
                opere = "ADD";
                break;
            case 2:
                opere = "CHK";
                break;
            case 3:
                opere = "MOD";
                break;
            case 4:
                opere = "DEL";
                break;
            case 0:
                opere = "FIN";
                break;
            default:
                System.out.println("ERROR bad_request");
                check();
                break;
        }
        String chaine = getRequest();
        if (opere.equals("FIN")) chaine = opere;
        else chaine = opere + " " + chaine;
        BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream sortieSocket = new PrintStream(s.getOutputStream());

        sortieSocket.println(chaine);

        chaine = entreeSocket.readLine();
        System.out.println(chaine);

        check();
    }

    private String getRequest() throws IOException {
        String chaine = "";
        // Scanner sur System.in
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez votre couple comme suit : <<login password>>." + "\n");
        chaine = scanner.nextLine();
        return chaine;
    }

    private int getOperation() throws IOException {
        String chaine = "";
        // Scanner sur System.in
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------------+");
        System.out.println("| Tapez votre demande :           |");
        System.out.println("| 1 - creer une paire             |");
        System.out.println("| 2 - tester une paire            |");
        System.out.println("| 3 - mettre à jour une paire     |");
        System.out.println("| 4 - supprimer une paire         |");
        System.out.println("| 0 - arreter                     |");
        System.out.println("+---------------------------------+");
        chaine = scanner.nextLine();
        try {
            return Integer.parseInt(chaine);
        } catch (Exception e) {
            System.out.println("ERROR bad_request");
            return getOperation();
        }
    }
}
