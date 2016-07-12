package com.test;

import javax.ws.rs.GET;
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
	// This is a request generator service for a given document
	public Response generateRequest(@PathParam("param") String docType, @PathParam("title") String title) throws Exception {
		long ticket = RequestQueue.pushRequest(docType, title);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ticket);
		return Response.status(200).entity(""+json).build();
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/{ticket}")
	// This is a polling service
	public Response getDocStatus(@PathParam("ticket") long ticket) throws Exception {		
		// return a JSON object of document from here
		if (ticket == -1)	// invalid input case
		{
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString("Document type or title is invalid");
			return Response.ok(""+json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
		}
		
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
					return Response.ok(""+json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
				}
				catch (Exception e){
					e.printStackTrace();
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String json = ow.writeValueAsString("exception in converting to JSON");
					return Response.ok(""+json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
				}
			}
			else
			{
				String message="not yet impression marked";
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(message);
				return Response.ok(json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
			}	
		}
		else {
			// the document with the given ticket has been already processed and removed from the queue
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString("Ticket has been already processed and removed from the queue or is invalid");
			return Response.ok(json).header("Access-Control-Allow-Origin", "http://localhost:28088").build();
		}
 
	}
	
}






