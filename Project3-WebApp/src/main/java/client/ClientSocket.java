
package client;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.FaceData;
import utility.FaceExpressionData;

@ClientEndpoint
public class ClientSocket {

	private Session session;
	public boolean isConnected;
	FaceExpressionData faceExpressionData;
	FaceData faceData;
	private static ClientSocket instance = null;

	public static ClientSocket getInstance() {
		if (instance == null) {
			instance = new ClientSocket();
		}
		return instance;
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected to server");
		this.session = session;
		this.isConnected = true;

	}

	@OnMessage
	public void onText(String message, Session session)
			throws JSONException, JsonParseException, JsonMappingException, IOException {
		System.out.println("Message received from server:" + message);
		JSONObject obj = new JSONObject(message);
		JSONObject dataObj = obj.getJSONObject("data");
		ObjectMapper m = new ObjectMapper();
		if (obj.getString("action").equals("faceExpressionData"))
			this.faceExpressionData = m.readValue(dataObj.toString(), FaceExpressionData.class);

		if (obj.getString("action").equals("faceData"))
			this.faceData = m.readValue(dataObj.toString(), FaceData.class);

	}

	@OnClose
	public void onClose(CloseReason reason, Session session) {
		System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
		this.isConnected = false;
	}

	public boolean isConnected() {
		return this.isConnected;
	}

	public FaceExpressionData getFaceExpressionData() {
		return this.faceExpressionData;
	}

	public FaceData getFaceData() {
		return this.faceData;
	}

	public void sendMessage(String str) {
		try {
			session.getBasicRemote().sendText(str);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
