package com.learning.net_retrofit_java;

import com.google.gson.annotations.SerializedName;

public class Meta{

	@SerializedName("pagination")
	private Pagination pagination;

	public Pagination getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"pagination = '" + pagination + '\'' + 
			"}";
		}
}