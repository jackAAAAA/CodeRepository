package com.learning.net_retrofit_java;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private Object meta;

	public int getCode(){
		return code;
	}

	public Data getData(){
		return data;
	}

	public Object getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}