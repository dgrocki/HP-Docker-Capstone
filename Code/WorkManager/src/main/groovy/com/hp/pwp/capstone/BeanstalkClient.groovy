package com.hp.pwp.capstone

import com.surftools.BeanstalkClientImpl.ClientImpl
import com.surftools.BeanstalkClientImpl.JobImpl
import com.surftools.BeanstalkClient.BeanstalkException


class BeanstalkClient{
	//private ClientImpl connection = new ClientImpl("0.0.0.0", 11300);
	private String pod_id;
	private ClientImpl connection;
	private JobImpl currentJob;	//can we only be working on one job at a time?
	private String riak;
	private String worker_b;
	private String status;

	public BeanstalkClient(){
		pod_id = System.getenv("POD_NAME");
		println pod_id;
		connection  = new ClientImpl("beanstalk", 11300); 
		riak = "riak" + pod_id;		
		worker_b = "to_worker_b" + pod_id;
		status = "status" + pod_id;
	}

	public List<String> listTubes(){
				
		return connection.listTubes();
	}

	public void sendWork(String json){
		long priority = 0;
		int delaySeconds = 0;
		int timeToRun = 10;
		byte[] data = json.getBytes();

		connection.put(priority, delaySeconds, timeToRun, data);
	}	
	//functionhere for testing purposes
	public void useTube(String s){
		connection.useTube(s);
	}
	//pull a new job off the new_work queue
	public String recieve_new_work(){
		connection.watch("new_work");
		JobImpl job = connection.reserve();
		String s = new String(job.data);
		connection.delete(job.jobId);
		return s;
	}

        // Send work to workerA
        public void send_to_workerA(String json) {
            connection.useTube("new_work");
            sendWork(json);
        }

	//put a new job on the to_workerB queue
	public void send_to_workerB(String json){
		connection.useTube(worker_b);
		sendWork(json);
		
		
	}
	//pull a job off of the riak queue
	//returns a string of json data and the job is deleted
	public String recieve_riak_work(){

		connection.watch(riak);
		JobImpl job = connection.reserve();
		String s = new String(job.data);
		connection.delete(job.jobId);
		return s;

	}
	//put a job on the status queue
	public void send_status(String json){
		connection.usetTube(status);
		sendWork(json);
	}

        public boolean peek_jobs() {
                String resp = connection.peek()

                if(resp == "NOT_FOUND\r\n") {
                    return false;
                } else {
                    return true;
                }
        }



}
