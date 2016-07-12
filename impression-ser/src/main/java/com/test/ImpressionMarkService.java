package com.test;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.util.Map;

import com.test.RequestQueue;
 
@Path("/document")
public class ImpressionMarkService {
	
 
	@GET
	@Produces("application/json")
	@Path("/{param}/{title}")
	public Response generateRequest(@PathParam("param") String docType, @PathParam("title") String title) throws Exception {
 
		//String output = "Jersey say example: " + docType + " " + title;
 
		//RequestQueue que = new RequestQueue();
		long ticket = RequestQueue.pushRequest(docType, title);
		/*return Response.status(200).entity(ticket).build();*/
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ticket);
		return Response.status(200).entity(""+json).build();
		
		//return Response.ok(ticket).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
 
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/{ticket}")
	public Response getDocStatus(@PathParam("ticket") long ticket) throws Exception {
 
		
		// return a JSON object of document from here
		Map<Long, DocumentRequest>requestStatusMap = RequestQueue.getRequestStatusMap();
		if ((requestStatusMap != null) && (requestStatusMap.isEmpty() != true)) {	// change this for handling null pointer exception
			DocumentRequest docRequestObj = requestStatusMap.get(ticket);
			boolean status = docRequestObj.isStatus();
			// check if document has been impression marked or not yet
			if (status == true)
			{
				// return full document JSON object here
				Map<String, Document>map = DocumentRepository.getDocumentRepoMap();
				Document doc = map.get(docRequestObj.getTitle());
				// convert object to JSON format now
				try {
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String json = ow.writeValueAsString(doc);
					//return Response.status(200).entity(""+json).build();
					return Response.ok(""+json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
				}
				catch (Exception e){
					e.printStackTrace();
					//return Response.status(200).entity("exception in converting to JSON").build();
					return Response.ok("exception in converting to JSON").header("Access-Control-Allow-Origin", "http://localhost:28088").build();
				}
			}
			else
			{
				// return empty json object here
				//return Response.status(200).entity("Document "+"'"+docRequestObj.getDocType()+"'"+ " Having title "+"'"+docRequestObj.getTitle()+"'"+" not yet impression marked").build();
				return Response.ok("Document "+"'"+docRequestObj.getDocType()+"'"+ " Having title "+"'"+docRequestObj.getTitle()+"'"+" not yet impression marked").header("Access-Control-Allow-Origin", "http://localhost:28088").build();
			}	
		}
		else {
			// the document with the given ticket has been already processed and removed from the queue
			return Response.ok("the document with the given ticket "+ticket+" has been already processed and removed from the queue").header("Access-Control-Allow-Origin", "http://localhost:28088").build();
		}
 
	}
	
	
	
	@OPTIONS
	@Path("/document")
	public Response getOptions() {
	  return Response.ok()
	    .header("Access-Control-Allow-Origin", "*")
	    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
	    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}
	
}






