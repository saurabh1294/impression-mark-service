package com.test;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;



public class RequestQueue {
	public static Map<Long, DocumentRequest> requestStatusMap = new HashMap<Long, DocumentRequest>();;
	public static LinkedBlockingDeque<DocumentRequest> que;
	private RequestQueue() {
		//TODO
	}
	
	public static Map <Long, DocumentRequest> getRequestStatusMap(){
			return requestStatusMap;
	}
	
	public static LinkedBlockingDeque<DocumentRequest> getQue() {
		// singleton
		if (que == null)
			que = new LinkedBlockingDeque<DocumentRequest>();
		return que;
	}
	
	public static long generateDocumentTicket() {
		long epoch = System.currentTimeMillis();
		return epoch;	
	}
	
	public static long pushRequest(String docType, String title) throws Exception {
		
		// check if input is valid or not
		Map<String, Document>map = DocumentRepository.getDocumentRepoMap();
		Document document = map.get(title);
		String docTitle=null;
		String documentType=null;
		if(document != null) {	// check if object is valid
			docTitle = document.title;	// get the title
			documentType = document.content;
		}
		
		if ((document != null) && (docTitle.equals(title)) &&(documentType.equals(docType))) {
			long ticket = generateDocumentTicket();
			System.out.println("**** pushing request to queue*****");
			
			DocumentRequest docRequest = new DocumentRequest();
			docRequest.setDocType(docType);
			docRequest.setStatus(false);
			docRequest.setTicket(ticket);
			docRequest.setTitle(title);
			que = getQue();
			// push request to Blocking Dequeue first
			que.add(docRequest);
			// maintain a map of ticket for a request and its status
			requestStatusMap.put(ticket, docRequest);
			return ticket;
		}
		else return -1;
	}

}
