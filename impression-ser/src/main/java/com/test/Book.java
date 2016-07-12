package com.test;

public class Book extends Document {
	
	public String topic;
	
	@Override
	public String toString(){
	
		return "Book [content:" + this.content + ", title:" + this.title + ", " +
				"author:" + this.author + "topic:"+ this.topic + "]";
		
	}

}
