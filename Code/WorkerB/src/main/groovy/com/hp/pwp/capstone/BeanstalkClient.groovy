package com.hp.pwp.capstone

import com.surftools.BeanstalkClientImpl.ClientImpl
import com.surftools.BeanstalkClientImpl.JobImpl
import com.surftools.BeanstalkClient.BeanstalkException


class BeanstalkClient{
	//private ClientImpl connection = new ClientImpl("0.0.0.0", 11300);

	private String pod_id;
	private ClientImpl connection;
	private JobImpl currentJob;     //can we only be working on one job at a time?
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
		int timeToRun = 100;
		byte[] data = json.getBytes();

		connection.put(priority, delaySeconds, timeToRun, data);
	}	
	//functionhere for testing purposes
	public void useTube(String s){
		connection.useTube(s);
	}
	//pull a new job off the the to_worker_b queue
	public String recieve_new_work(){
		connection.watch(worker_b);

		JobImpl job = connection.reserve();
		String s = new String(job.data);
		connection.delete(job.jobId);
		return s;
	}
	//put a new job on the riak queue
	public void send_to_work_manager(String json){
		connection.useTube(riak);
		sendWork(json);
	}
	//put a job on the status queue
	public void send_status(String json){
		connection.usetTube(status);
		sendWork(json);
	}



}
