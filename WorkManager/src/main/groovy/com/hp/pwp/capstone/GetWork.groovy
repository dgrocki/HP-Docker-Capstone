package com.hp.pwp.capstone

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import groovy.json.JsonSlurper

@SuppressWarnings("serial")
public class GetWork extends HttpServlet 
{
    private BeanstalkClient servBeanStalk;
    private String output;

    public GetWork(BeanstalkClient beanstalk) 
    {
	println "Get Work started";
        servBeanStalk = beanstalk;
        output = "";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException 
    {
        def parser = new JsonSlurper()

        output = servBeanStalk.recieve_new_work();

	println "this is output";
	println output;	

        def json = parser.parseText(output)

        if(json.jobBool && json.path && json.outPath && json.pageLength && json.startPage && json.endPage && json.wid && json.jid && json.status) {
            println("==GET[WorkA]: JSON has everything I need :*)")
        } else {
            throw new IOException("The submitted JSON object does not have all the required fields :*(")
        }

        println( "==GET[WorkerA]: " + json )

        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println( output )
        } catch (Exception ex) {
            println("**GET[WorkA] ERR: " + ex)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
	    
           // out.close();
        }
    }
}
