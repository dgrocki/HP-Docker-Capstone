package com.hp.pwp.capstone
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class WorkSocket extends WebSocketAdapter
{
    private String quotes = "Computing science is no more about computers than astronomy is about telescopes. -Edsger W. Dijkstra |I don’t need to waste my time with a computer just because I am a computer scientist. -Edsger W. Dijkstra |The purpose of computing is insight, not numbers. -Richard Hamming |An algorithm must be seen to be believed. -Donal Knuth |Syntactic sugar causes cancer of the semicolon. -Alan J Perlis |If a program manipulates a large amount of data, it does so in a small number of ways. -Alan J Perlis |A good system can’t have a weak command language. -Alan J. Perlis |Representation is the essence of programming. -Fred Brooks |Elegance is not a dispensable luxury but a quality that decides between success and failure. -Edsger W. Dijkstra |There is no programming language, no matter how structured, that will prevent programmers from writing bad programs. -Lawrence Flon"
    private Session session;
    @Override
    public void onWebSocketConnect(Session sess)
    {
        super.onWebSocketConnect(sess);
        session = sess;
        System.out.println("Socket Connected: " + sess);
    }
    
    @Override
    public void onWebSocketText(String message)
    {
        super.onWebSocketText(message);
        System.out.println("Received TEXT message: " + message);
        session.getRemote().sendString(quotes);
    }

    
    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode,reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }
    
    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}