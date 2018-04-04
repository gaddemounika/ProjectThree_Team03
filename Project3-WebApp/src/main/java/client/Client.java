package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

import server.ServerConstants;
import utility.Constants;

/**
 * Client embeds a client service which communicates with a remote number
 * service.
 *
 * The class acts as a message queuing buffer for received values. Received
 * values can be retrieved from the buffer at the leisure of the individual
 * client software. I.E. client software can set their own frequency from which
 * to consume from the message queue.
 */
public class Client implements Runnable {

	private volatile Boolean isRunning = false;
	private volatile String channel;
	private SynchronousQueue<Boolean> shutdownSignal;
	private ConcurrentLinkedQueue<String> messageQueue;

	/**
	 * Default constructor. The value channel defaults to the value found in the
	 * ClientConstants class.
	 */
	public Client() {
		this(new Integer(ClientConstants.CHANNELS));
	}

	/**
	 * Client class constructor.
	 *
	 * @param channel
	 *            the channel on which this client should request the server.
	 */
	public Client(Integer channel) {
		this.channel = channel.toString() + "\n";
		this.shutdownSignal = new SynchronousQueue<Boolean>();
		this.messageQueue = new ConcurrentLinkedQueue<String>();
	}

	/**
	 * Starts the embeddable client. Blocks if called outside of the context of a
	 * Thread object.
	 */
	// @Override
	public void run() {
		Socket client = null;
		BufferedReader input = null;
		DataOutputStream output = null;
		int retries = 0;
		String resp = null;
		try {
			client = new Socket(Constants.HOSTNAME, ServerConstants.PORT_NUMBER);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new DataOutputStream(client.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + ClientConstants.HOSTNAME);
		} catch (IOException e) {
			System.err.println("Connection Exception " + ClientConstants.HOSTNAME + "error: " + e.toString());
		}
		this.isRunning = true;
		System.out.println("Connected");
		try {
			output.writeBytes(this.channel);
		} catch (Exception e) {
			System.err.println(e.toString());
			this.isRunning = false;
			return;
		}
		while (this.isRunning) {
			try {
				resp = input.readLine();
				if (resp.startsWith("ERROR")) {
					System.err.println(resp);
					retries++;
					if (retries >= ClientConstants.MAX_RETRIES) {
						System.err.println("Exceeded max retry attempts. Stopped reattempts.");
						return;
					}
				}
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
				retries++;
				if (retries >= ClientConstants.MAX_RETRIES) {
					System.err.println("Exceeded max retry attempts. Stopped reattempts.");
					return;
				}
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
				retries++;
				if (retries >= ClientConstants.MAX_RETRIES) {
					System.err.println("Exceeded max retry attempts. Stopped reattempts.");
					return;
				}
			}
			System.out.println("Enqueuing: " + resp);
			// ClientConsole.getInstance().print("Values Received from Server: " + resp);
			this.messageQueue.add(resp);
			// new CalculateValues(this.messageQueue);
			// Reset retries after successful exchange.
			retries = 0;
		}
		try {
			output.writeBytes("stop\n");
			client.close();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		try {
			this.shutdownSignal.put(new Boolean(true));
		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}

	/**
	 * Convenience method for starting this client as a thread.
	 */
	public void start() {
		new Thread(this).start();
	}

	/**
	 * Stops a running server. Safe to call, even if the server was already stopped.
	 *
	 * @throws IOException
	 */
	public void stop() throws IOException {
		boolean wasRunning = this.isRunning;
		this.isRunning = false;
		if (wasRunning) {
			try {
				this.shutdownSignal.take();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		System.out.println("Shutdown of number request service complete.");
	}

	/**
	 * Sets the client channel. If the client was running, it will be restarted with
	 * the updated value.
	 *
	 * @apram channel the channel update to.
	 */
	public void updateChannels(Integer channel) throws IOException {
		boolean wasRunning = this.isRunning;
		this.channel = channel.toString() + "\n";
		if (wasRunning) {
			this.stop();
			this.start();
		}
	}

	/**
	 * Returns whether the client is running.
	 *
	 */
	public boolean getIsRunning() {
		return this.isRunning;
	}

	/**
	 * Retrieves the next message in the message queuem which is an array of numbers
	 * sent from the server. The number of items in the array is equivalent to the
	 * channel value at the time that the message was requests. Returns an empty
	 * array if no message is ready.
	 *
	 */
	public List<Integer> next() {
		String message = this.messageQueue.poll();
		if (message == null) {
			return new ArrayList<Integer>(0);
		}
		String[] messages = message.split(" ");
		List<Integer> integers = new ArrayList<Integer>();
		for (int i = 0; i < messages.length; i++) {
			try {
				integers.add(Integer.parseInt(messages[i]));
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		return integers;
	}

}
