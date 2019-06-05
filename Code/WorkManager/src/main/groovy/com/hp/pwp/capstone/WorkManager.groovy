package com.hp.pwp.capstone
import com.surftools.BeanstalkClientImpl.ClientImpl
import com.surftools.BeanstalkClientImpl.JobImpl
import com.surftools.BeanstalkClient.BeanstalkException
import static groovyx.gpars.actor.Actors.actor
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import org.eclipse.jetty.server.Server
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import groovy.json.JsonSlurper;


class WorkManager{

	public static void main(String [] args) {



		def riak = actor{
			BeanstalkClient beanstalk = new BeanstalkClient();		

			Riak riak_client = new Riak();
			while(1){
				String new_work = beanstalk.recieve_riak_work();
				def parser = new JsonSlurper();
				def data = parser.parseText(new_work);

				println data;	
				String s = data.outPath;

				println s;

				File file = new File(s);
				byte[] fileArray;
				fileArray = Files.readAllBytes(file.toPath());


				println "Storing in riak... ";
				riak_client.store(fileArray);
				


				println "Fetching from riak... ";
				byte[]fetch =  riak_client.fetch();
				//println fetch;
				String pod_id = System.getenv("POD_NAME");
				println pod_id;
				s = "/mnt/Out_" + pod_id + ".pdf";
				File file2 = new File(s);
				Files.write(file2.toPath(), fetch);

				if(fetch != fileArray){
					println "false";
					println "1" + fileArray.length;
					println "2" + fetch.length;
				}else{println "true";}
				
			}

		}

		def await_new_work = actor {
			BeanstalkClient beanstalk = new BeanstalkClient();		
			beanstalk.useTube("new_work");
			final Jetty jetty = new Jetty(8080, beanstalk);
			println "jetty made"
			jetty.start();
			println "jetty started"
			Thread.sleep(500);
			if (false == jetty.isStarted()) {
				throw new Exception("Cannot start jetty server");
			}

		}


		//setup all the threads
		[riak, await_new_work]*.join()

			return;
	}

}
