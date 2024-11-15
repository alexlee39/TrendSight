package server.src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.naming.Context;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/* NOTE: Uses HTTP instead of HTTPS which makes the request handling not secure 
 * --> Generally should use https for security reasons
 */

public class Server {
    //Static vars --> Vars that belong to class instead of an object/any instance of the class
    private static final int SERVER_PORT = 5000;
    private static final String SERVER_NAME = "localhost";
    
    public static void main(String[] args) throws IOException {
        System.out.println(SERVER_PORT);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        //When creating server --> Backlog/2nd Param == 0, means default backlog used -->
        //More info: https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html#create-java.net.InetSocketAddress-int-
        HttpServer server =  HttpServer.create(new InetSocketAddress(SERVER_NAME, SERVER_PORT), 0); 

        HttpHandler AuthReqHandler = new AuthReqHandler();
        server.createContext("/auth", AuthReqHandler);
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started!");
    }
}
