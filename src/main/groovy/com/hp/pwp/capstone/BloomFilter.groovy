package com.hp.pwp.capstone

import java.security.MessageDigest
import java.nio

class Bloomfilter {
	private long arrSize=4000000;
	private boolean[] hashArray = [false]*arrSize;
	private long numItems=0;

	public void InsertString(string text){
		hashArray[hash1(text)%arrSize]=true;
		hashArray[hash2(text)%arrSize]=true;
		hashArray[hash3(text)%arrSize]=true;
		hashArray[hash4(text)%arrSize]=true;
		numItems++;
	}

	public boolean CheckString(string input){
		return hashArray[hash1(text)%arrSize] && hashArray[hash2(text)%arrSize] && hashArray[hash3(text)%arrSize] && hashArray[hash4(text)%arrSize];
	}

	public void PrintInfo(){
		probability = Math.Pow(1-Math.Pow(2.718,(-4*numItems/arrSize)),4);
		println("Hash functions (k):  4");
		println("Bits in filter (m):  "+arrSize.ToString());
		println("Items in filter (n): "+numItems.ToString());
		println("Prob False Positives: "+probability.ToString()); 
	}

	private long hashToLong(byte[] input){
		ByteBuffer bb = ByteBuffer.wrap(input);
		return bb.getLong();
	}

	private void hash1(string text){
		digest = MessageDigest.getInstance("SHA-512");
		salt = "lzebderlilguvcdxiorgroenwanvoptc";
		return hashConvert(digest.digest((text+salt).getBytes()));
	}
	private void hash2(string text){
		digest = MessageDigest.getInstance("SHA-512");
		salt = "ojjihxvhylvwznzvbhyzbtcymvjiffxz";
		return hashConvert(digest.digest((text+salt).getBytes()));
	}
	private void hash3(string text){
		digest = MessageDigest.getInstance("SHA-512");
		salt = "fvoawbxsibbqxrzyoeihsnytxpimkmwk";
		return hashConvert(digest.digest((text+salt).getBytes()));
	}
	private void hash4(string text){
		digest = MessageDigest.getInstance("SHA-512");
		salt = "rfrgblbwtotrbxhrksdkjrqckuzxwfzo";
		return hashConvert(digest.digest((text+salt).getBytes()));
	}
}