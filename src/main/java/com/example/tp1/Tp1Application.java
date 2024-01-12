package com.example.tp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Tp1Application {

    public static void main(String[] args) throws Exception {
        ServeurAS sas = new ServeurAS(28414);
        sas.run();
        //ClientTCP ctcp = new ClientTCP("localhost", 28414);
        //ClientUDP cudp = new ClientUDP("localhost", 28414);
        ManagerTCP mtcp = new ManagerTCP("localhost", 28415);

        //ctcp.check();
        //cudp.check();
        mtcp.check();
    }

}
