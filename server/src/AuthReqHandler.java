package server.src;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class AuthReqHandler implements HttpHandler {
    private final Map<String, String> users;

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
    
    public AuthReqHandler(){ // temp dictionary for comparing user data to authenticate
        users = new HashMap<String, String>();
        users.put("test@gmail.com", "test1234");
    }
    // public String res;
    public int statusCode = 204;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res = "Default";
        String httpMethod = exchange.getRequestMethod();
        int statusCode = 200;
        addCorsHeaders(exchange);

        if (httpMethod.equalsIgnoreCase("OPTIONS")) {
            // Handle preflight CORS request
            exchange.sendResponseHeaders(204, -1); // No Content
            return;
        }

        try {
            if(httpMethod.equals("GET")){
                res = handleGet(exchange);
            }
            else if(httpMethod.equals("POST")){
                res = handePost(exchange);
            }
            else{
                //Some default case for the other httpMethods: Ex: PATCH, CONNECT, TRACE, etc
                throw new Exception("Not a Supported HTTP Method");
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
    // Utility to parse URL-encoded query strings
    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String email = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String pass = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(email, pass);
            }
        }
        return params;
    }
    public String handePost(HttpExchange httpExchange) throws IOException{
        InputStream rawMessage = httpExchange.getRequestBody();
        String messageBody = new String(rawMessage.readAllBytes());
        //System.out.println("messageBody: "+ messageBody);
        Map<String, String> jsMap = parseQuery(messageBody);
        //System.out.println("jsMap: "+ jsMap);
        String userID = jsMap.get("userID");
        String passID = jsMap.get("passID");
        //System.out.println("userID: "+ userID);
        //System.out.println("passID: "+ passID);

        String response;
        if (users.containsKey(userID) && users.get(userID).equals(passID)){
            response = "Login Successful";
            statusCode = 200;
            httpExchange.sendResponseHeaders(statusCode, response.length());
        }
        else {
            response = "Invalid Credentials";
            statusCode = 401;
            httpExchange.sendResponseHeaders(statusCode, response.length());
        }
        try (OutputStream os = httpExchange.getResponseBody()){
            os.write(response.getBytes());
        }
        return "";
    }

    public String readInputStream(InputStream iStream) throws IOException{
        // BufferedReader bufReader = new BufferedReader(new Reader(iStream)\);
        return "";
    }
}


