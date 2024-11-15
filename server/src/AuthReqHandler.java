package server.src;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AuthReqHandler implements HttpHandler {

    // public String res;
    public int statusCode = 204;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res = "Default";
        String httpMethod = exchange.getRequestMethod();
        int statusCode = 200;
        // URI uri = exchange.getRequestURI();
        try {
            if(httpMethod.equals("GET")){
                res = handleGet(exchange);
            }
            else if(httpMethod.equals("POST")){
    
            }
            else if(httpMethod.equals("PUT")){
    
            }
            else if(httpMethod.equals("DELETE")){
    
            }
            else{
                //Some default case for the other httpMethods: Ex: PATCH, CONNECT, TRACE, etc
                throw new Exception("Not Valid Req Method for Server");
            }   
        } catch (Exception e) {
            System.out.println("Error with the request!");
            res = e.toString();
            e.printStackTrace();
            statusCode = 500;
            System.out.println("Internal Server Error" + e.getMessage());
        }

        exchange.sendResponseHeaders(statusCode, res.length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(res.getBytes());
        outStream.close();
    }
    
    public String handleGet(HttpExchange httpExchange){
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        String queryData = query.substring(query.indexOf("=") + 1);
        //URI: http:/localhost:5000/?=
        return "";
    }
    public String handePost(HttpExchange httpExchange){
        InputStream iStream = httpExchange.getRequestBody();
        // String reqBody = iStream.
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        
        String queryData = query.substring(query.indexOf("=") + 1);

        return "";
    }

    public String readInputStream(InputStream iStream) throws IOException{
        // BufferedReader bufReader = new BufferedReader(new Reader(iStream)\);
        return "";
    }
}
