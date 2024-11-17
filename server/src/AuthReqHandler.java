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
    // temp hashmap for storing userinfo (will swap to SQL calls to database eventually)
    private final static Map<String, String> userData_hashmap = new HashMap<String, String>();

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
    
    public AuthReqHandler(){ 
        userData_hashmap.put("test@gmail.com", "test1234"); // temp for testing login
    }
    // public String res;
    public int statusCode = 204;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res = "Default";
        String httpMethod = exchange.getRequestMethod();
        int statusCode = 200;
        addCorsHeaders(exchange); // allow communication between frnt-end and back-end
        if (httpMethod.equalsIgnoreCase("OPTIONS")) {
            // Handle preflight CORS request
            exchange.sendResponseHeaders(204, -1); // No Content
            return;
        }

        String context_path = exchange.getRequestURI().getPath();
        System.out.println("context_path: " + context_path);

        try {
            if (context_path.equals("/auth/login")){
                new LoginHandler().handle(exchange);
            }
            else if (context_path.equals("/auth/registration")){
                new RegistrationHandler().handle(exchange);
            }
            // if(httpMethod.equals("GET")){
            //     //res = handleGet(exchange);
            // }
            // else if(httpMethod.equals("POST")){
            //     //res = handePost(exchange);
            // }
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

    // a nested class for handling the registration context 
    public static class RegistrationHandler implements HttpHandler{
        @Override // not really sure
        public void handle(HttpExchange exchange) throws IOException{
            String http_method, http_messagebody, http_response;
            String data_email, data_password, data_username;
            Map<String, String> hashmap_messagedata;
            
            http_method = exchange.getRequestMethod();
            System.out.println("http_method for RegistrationHandler: " + http_method);

            if (http_method.equals("POST")){
                http_messagebody = new String(exchange.getRequestBody().readAllBytes());
                hashmap_messagedata = parseQuery(http_messagebody);
                System.out.println("hashmap_message data extracted from URI: " + hashmap_messagedata);

                data_email = hashmap_messagedata.get("email");
                data_password = hashmap_messagedata.get("pass");
                data_username = hashmap_messagedata.get("user");
                System.out.println("data_email: " + data_email);
                System.out.println("data_password: " + data_password);
                System.out.println("data_username: " + data_username);

                if (!userData_hashmap.containsKey(data_email)){
                    userData_hashmap.put(data_email, data_password);

                    http_response = "account created, data saved in back-end";
                    exchange.sendResponseHeaders(201, http_response.length());
                }
                else {
                    http_response = "a account with this email already exists, login";
                    exchange.sendResponseHeaders(400, http_response.length());
                }
                
                try (OutputStream os = exchange.getResponseBody()){
                    os.write(http_response.getBytes());
                    System.out.println(http_response);
                }

            }

        }
    }

    // a nested class for handling the login context 
    public static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            String http_method, http_messagebody, http_response;
            String data_email, data_password, data_username;
            Map<String, String> hashmap_messagedata;
            
            http_method = exchange.getRequestMethod();
            System.out.println("http_method for LoginHandler: " + http_method);

            if (http_method.equals("POST")){
                http_messagebody = new String(exchange.getRequestBody().readAllBytes());
                hashmap_messagedata = parseQuery(http_messagebody);
                System.out.println("hashmap_message data extracted from URI: " + hashmap_messagedata);

                data_email = hashmap_messagedata.get("email");
                data_password = hashmap_messagedata.get("pass");
                System.out.println("data_email: " + data_email);
                System.out.println("data_password: " + data_password);

                if (userData_hashmap.containsKey(data_email)){
                    if (userData_hashmap.get(data_email).equals(data_password)){
                        http_response = "email and password match, welcome: ";
                        exchange.sendResponseHeaders(200, http_response.length());
                    }
                    else {
                        http_response = "email does not match password, try again";
                        exchange.sendResponseHeaders(401, http_response.length());
                    }
                }
                else {
                    http_response = "email does not exist, please make a account and try again";
                    exchange.sendResponseHeaders(401, http_response.length());
                }
                
                try (OutputStream os = exchange.getResponseBody()){
                    os.write(http_response.getBytes());
                    System.out.println(http_response);
                }

            }

        }
    }
    
    // method to parse URL-encoded query strings to be used in the handling methods
    private static Map<String, String> parseQuery(String query) {
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
}

    // public String handleGet(HttpExchange httpExchange){
    //     URI uri = httpExchange.getRequestURI();
    //     String query = uri.getRawQuery();

    //     String queryData = query.substring(query.indexOf("=") + 1);
    //     //URI: http:/localhost:5000/?=
    //     return "";
    // }

    // public String readInputStream(InputStream iStream) throws IOException{
    //     // BufferedReader bufReader = new BufferedReader(new Reader(iStream)\);
    //     return "";
    // }

    // public String handePost(HttpExchange httpExchange) throws IOException{
    //     InputStream rawMessage = httpExchange.getRequestBody();
    //     String messageBody = new String(rawMessage.readAllBytes());
    //     //System.out.println("messageBody: "+ messageBody);
    //     Map<String, String> jsMap = parseQuery(messageBody);
    //     //System.out.println("jsMap: "+ jsMap);
    //     String userID = jsMap.get("userID");
    //     String passID = jsMap.get("passID");
    //     //System.out.println("userID: "+ userID);
    //     //System.out.println("passID: "+ passID);

    //     String response;
    //     if (userData_hashmap.containsKey(userID) && userData_hashmap.get(userID).equals(passID)){
    //         response = "Login Successful";
    //         statusCode = 200;
    //         httpExchange.sendResponseHeaders(statusCode, response.length());
    //     }
    //     else {
    //         response = "Invalid Credentials";
    //         statusCode = 401;
    //         httpExchange.sendResponseHeaders(statusCode, response.length());
    //     }
    //     try (OutputStream os = httpExchange.getResponseBody()){
    //         os.write(response.getBytes());
    //     }
    //     return "";
    // }

    