package server.src;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/* URI Documentation: https://developer.mozilla.org/en-US/docs/Web/URI#syntax_of_uniform_resource_identifiers_uris
 * URI Format: http(s) + Authority/Port +   Path +                Query +                   Fragment(bookmark/ids)  
 * Ex:         http://   www.example.com:80 /path/to/myfile.html  ?key1=value1&key2=value2  #SomewhereInTheDocument
 *             http://www.example.com:80/path/to/myfile.html?key1=value1&key2=value2#SomewhereInTheDocument
 */
public class AuthReqHandler implements HttpHandler {

    // public String res;
    public int statusCode = 204;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res = "Default";
        String httpMethod = exchange.getRequestMethod();
        int statusCode = 200;
        try {
            if(httpMethod.equals("POST")){
    
            }
            else if(httpMethod.equals("PUT")){
    
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
    
    public String handePost(HttpExchange httpExchange){
        InputStream iStream = httpExchange.getRequestBody();
        // String reqBody = iStream.
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        
        String queryData = query.substring(query.indexOf("=") + 1);

        return "";
    }

    public String handlePut(HttpExchange httpExchange){
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
