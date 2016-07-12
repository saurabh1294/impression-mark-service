package com.test;
import java.util.*;	// using for hashmap

public class DocumentRepository {
	
	public static Map<String, Document> map = new HashMap<String, Document>();
	private static DocumentRepository repo= new DocumentRepository();
	
	private DocumentRepository() {
		
	}
	public static DocumentRepository getRepository(){
		return repo;
	}
	
	public static Map <String, Document>getDocumentRepoMap() {
		return map;
	}
	
	String documents[][] = {
			{"book", "The Dark Code", "Bruce Wayne", "Science"},
			{"book", "How to make money", "Dr. Evil", "Business"},
			{"journal", "Journal of human flight routes", "Clark kent"}
			// add more here
	};
	
	
	
	public void generateDocumentMap() {
		int i = 0;
		
		
		
		for (i = 0; i < documents.length; i++) {
			//ticket = generateDocumentTicket();
			
			//System.out.print(documents[i][j] + " ");
			if (documents[i][0].equals("book")) {
				Book book = new Book();
				book.content = documents[i][0];
				book.title = documents[i][1];
				book.author = documents[i][2];
				book.topic = documents[i][3];
				map.put(book.title, book);
				continue;
			}
			else if(documents[i][0].equals("journal")) {
				Journal journal = new Journal();
				journal.content = documents[i][0];
				journal.title = documents[i][1];
				journal.author = documents[i][2];
				map.put(journal.title, journal);
				continue;
			}	
			
			
			
		}
		
	}
	
	
	
	public Document getDocumentObjectByTicket(long ticket) {
		//convert this object in JSON object and return
		return map.get(ticket);
	}
	
	public long searchInMap(String docType, String title) {
		System.out.println("**** In searchInMap*****");
		
		
		
		//for(long ticket: map.keySet()) {
			//if ((map.get(ticket)).content.equals(docType) && ((map.get(ticket).title).equals(title)))
				//return ticket;
			//System.out.println("Ticket = "+ticket);
		//}
		
		return -1;	// document not found in map
	}
}


