package com.test;

public class Journal extends Document {

	@Override
	public String toString(){
	
		return "Journal [content:" + this.content + ", title:" + this.title + ", " +
				"author:" + this.author + "]";
		
	}
	
}
