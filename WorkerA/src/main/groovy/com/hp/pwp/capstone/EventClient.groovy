package com.hp.pwp.capstone

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class EventClient
{
    private InputPDF pdf;
    private WebSocketClient client;
    private URI uri;
    private Session session;
    private Boolean isConnected;
    private WebSocket socket;

    public EventClient(InputPDF ipdf) {
        pdf = ipdf;
        uri = URI.create("ws://localhost:8080/workManager/socket/");
        client = new WebSocketClient();
        // The socket that receives events
        socket = new WebSocket(pdf, this);
}

    public void connect()
    {
        if (isConnected) {
            session.close();
        }
        isConnected = false;
        while(!isConnected) {
            try
            {
                client.start();
                // Attempt Connect
                Future<Session> fut = client.connect(socket,uri);
                // Wait for Connect
                session = fut.get();
                // If connection has succeeded set isConnected to true.
                isConnected = true;
            }
            catch (Throwable t)
            {
                System.out.println("WebSocket Connection Failed");
                System.out.println("Waiting 5 Seconds to Attempt Reconnect");
                sleep(5000);
            }
        }
    }
    public void ready() {
        try 
        {
            // Send a message
            session.getRemote().sendString("Ready For Work");
        }
        catch (Throwable t) 
        {
            t.printStackTrace(System.err);
        }
    }
}
