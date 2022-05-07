package com.learning.net_retrofit_java;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserList{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<Data> data;

	@SerializedName("meta")
	private Meta meta;

	public int getCode(){
		return code;
	}

	public List<Data> getData(){
		return data;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"UserList{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}