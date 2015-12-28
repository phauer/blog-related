package de.philipphauer.h2;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2WebConsole {

    public static void start() {
        try {
            Server server = Server.createWebServer(new String[]{}).start();
            System.out.println("H2 can be accessed in port: "+server.getPort());
            System.out.println("URL: jdbc:h2:./db/repository");
            System.out.println("Empty user and pw");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
