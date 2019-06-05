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
public class SubmitWork extends HttpServlet 
{
    private BeanstalkClient servBeanStalk;

    public SubmitWork(BeanstalkClient beanstalk) 
    {
        servBeanStalk = beanstalk;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException 
    {
        def parser = new JsonSlurper()
        StringBuffer jb = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = request.getReader();

            while((line = reader.readLine()) != null) {
                jb.append(line);
            }
            
            def json = parser.parseText(jb.toString())

            println("==POST[WorkA]: " + json )

            if(json.jobBool && json.path && json.outPath && json.pageLength && json.startPage && json.endPage && json.wid && json.jid && json.status) {
                println("==POST[WorkA]: JSON has everything I need :*)")
            } else {
                throw new IOException("The submitted JSON object does not have all the required fields :*(")
            }

            final long timeStamp = System.currentTimeMillis();
            
            servBeanStalk.send_to_workerA(jb.toString()); //submit work for worker A to finish

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            println( "**POST[WorkA] ERR: " + ex )

            PrintWriter out = response.getWriter();
            out.println( "ERR: " + ex )
 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            response.getWriter().close();
        }
    }
}
