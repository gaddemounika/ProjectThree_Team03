package client;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
 
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
 
import org.glassfish.tyrus.client.ClientManager;
 
@ClientEndpoint
public class FaceClient {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        client.connectToServer(FaceClient.class, new URI("ws://localhost:8025/ws/faces"));
    }

    @OnOpen
    public void onOpen(Session session) throws Exception {
        logger.info("Connected ... " + session.getId());
        session.getBasicRemote().sendText("start");
    }
 
    @OnMessage
    public String onMessage(String message, Session session) throws Exception {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        logger.info("Received ...." + message);
        String userInput = bufferRead.readLine();
        return userInput;
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }
 
}