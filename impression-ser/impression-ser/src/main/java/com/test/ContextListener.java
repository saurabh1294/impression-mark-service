

package com.test;
import java.lang.Thread;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


class RequestProcessor extends Thread {
	public void run()
	{
		while(true)
		{
			System.out.println("*** In thread run() ***");
			try {
					TimeUnit.MILLISECONDS.sleep(25000);	// put a delay in milliseconds for processing request
					LinkedBlockingDeque<DocumentRequest> que = RequestQueue.getQue();
					//if (que != null) {
						boolean isEmpty = que.isEmpty();
						System.out.println("***isEmpty*** = "+isEmpty);
						if (isEmpty == false) {
							DocumentRequest docRequest = que.remove();
							docRequest.setStatus(true);
							Map<Long, DocumentRequest> map = RequestQueue.getRequestStatusMap();
							System.out.println("***** Removing doc object with ticket"+docRequest.getTicket()+" from queue");
							map.put(docRequest.getTicket(), docRequest);
						}
					//}
			}
			catch (Exception e) {
				System.out.println("Exception" + e.getMessage());
				e.printStackTrace();
			}
			
		}
			
	}
}

public class ContextListener implements ServletContextListener  {
	
	 private Thread myThread = null;

	 

		public void contextDestroyed(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
			try {
	        	if (myThread != null) {
	        		myThread.interrupt();
	        	}
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
			
		}

		public void contextInitialized(ServletContextEvent arg0){
			// TODO Auto-generated method stub
			 if ((myThread == null) || (!myThread.isAlive())) {
				 
				 	try {
		        	DocumentRepository repo = DocumentRepository.getRepository();
		    		// where to put this initialization ??
		    		repo.generateDocumentMap();
		    		System.out.println("*********** generating document map *************");
		            myThread = new RequestProcessor();
		            myThread.start();
				 	}
				 	catch(Exception e) {
				 		e.printStackTrace();
				 	}
		        }
			
		}
}
