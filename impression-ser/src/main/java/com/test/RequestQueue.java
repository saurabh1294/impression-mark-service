package com.test;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;



public class RequestQueue {
	
	//public static Map<Long, DocumentRequest> requestStatusMap = new HashMap<Long, DocumentRequest>();
	public static Map<Long, DocumentRequest> requestStatusMap = new HashMap<Long, DocumentRequest>();;
	public static LinkedBlockingDeque<DocumentRequest> que;
	private RequestQueue() {
		//dequeue = new ArrayQueue<Character>();
	}
	
	public static Map <Long, DocumentRequest> getRequestStatusMap(){
		//requestStatusMap = new HashMap<Long, DocumentRequest>();
			return requestStatusMap;
	}
	
	public static LinkedBlockingDeque<DocumentRequest> getQue() {
		//que = new Deque<DocumentRequest>();
		//return que;
		
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
		//DocumentRepository docRepo = DocumentRepository.getRepository();
		long ticket = generateDocumentTicket();
		System.out.println("**** pushing request to queue*****");
		
		DocumentRequest docRequest = new DocumentRequest();
		docRequest.setDocType(docType);
		docRequest.setStatus(false);
		docRequest.setTicket(ticket);
		docRequest.setTitle(title);
		que = getQue();
		//long ticket = docRepo.searchInMap(docType, title);
		//String info = ticket + "/" + docType + "/"+title;
		//requestmap.put(docRequest.getTicket(), docRequest);	// push request to queue
		// push request to Blocking Deque first
		que.add(docRequest);
		// maintain a map of ticket for a request and its status
		requestStatusMap.put(ticket, docRequest);
		return ticket;
	}

}
