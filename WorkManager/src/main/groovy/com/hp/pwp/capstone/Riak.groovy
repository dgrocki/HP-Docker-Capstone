package com.hp.pwp.capstone
import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.cap.Quorum;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

class Riak{
	//private String riak_node = System.getenv("RIAKNODE");
	private RiakClient client = RiakClient.newClient("coordinator");	
	private Location location = new Location(new Namespace("Test Bucket"), "TestKey" );
	
	public byte[] fetch(){
		FetchValue fv = new FetchValue.Builder(location).build();
		FetchValue.Response response = client.execute(fv);

		RiakObject obj = response.getValue(RiakObject.class);
		return obj.getValue().unsafeGetValue();

	}

	public Boolean store(byte[] myData){
		
		
		RiakObject obj = new  RiakObject()
//charset ISO_8859_1
			.setContentType("text/plain")
			.setValue(new BinaryValue(myData));
		
		StoreValue sv = new StoreValue.Builder(obj).withLocation(location).build();
		StoreValue.Response svResponse = client.execute(sv);
		return svResponse;

	}















}
