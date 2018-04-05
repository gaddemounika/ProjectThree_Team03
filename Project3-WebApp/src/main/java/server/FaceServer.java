package server;

import java.io.IOException;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint(value = "/faces") 
public class FaceServer {

	ServerGui observer;
	Gson gson;
    int port;

    public static void main(String[] args) {
        Server server = new Server("localhost", 8025, "/ws", null, FaceServer.class);
 
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

	// public FaceServer(ServerGui observer, int port) {
	// 	this.observer = observer;
	// 	this.gson = new Gson();
 //        this.port = port;
	// }

 //    public void start() {
 //        Server server = new Server("localhost", this.port, "/faces", null, FaceServer.class);
 //        try {
 //            server.start();
 //            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 //            System.out.print("Please press a key to stop the server.");
 //            reader.readLine();
 //        } catch (Exception e) {
 //            throw new RuntimeException(e);
 //        } finally {
 //            server.stop();
 //        }
 //    }

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection"); 
        // try {
        //     session.getBasicRemote().sendText("Connection Established");
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        // }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        session.getBasicRemote().sendText(message);
    	// FaceData request = gson.fromJson(message, FaceData.class); 
     //    System.out.println("Message from " + session.getId() + ": " + message);
     //    FaceData responseObj = this.observer.respondTo(request);
     //    String response = gson.toJson(responseObj, FaceData.class);
     //    try {
     //        session.getBasicRemote().sendText(response);
     //    } catch (IOException ex) {
     //        ex.printStackTrace();
     //    }
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " + session.getId() + " has ended");
    }
}