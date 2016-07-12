Dependencies for running this project will be taken care of by installing maven plugin

Configure maven for eclipse from the below link


http://maven.apache.org/guides/getting-started/index.html



Dependencies for running this project will be taken care of by installing maven plugin


*Problem statement*

*************************************************************************************************************************************
A global publishing company that publishes books and journals wants to develop a service to add XYZ inc. impression to their 
documents. Book publications include topics in business, science and media. Journals don’t include any specific topics. A document 
(books, journals) has a title, author and an impression-mark property. An empty impression-mark property indicates that the 
document has not been impression-marked yet.

The impression marking service has to be asynchronous. For a given content document the service should return a ticket, which 
can be used to poll the status of processing. If the impression marking is finished, then the document can be retrieved with the 
ticket. The impression-mark of a book or a journal is identified by setting the impression-mark property of the object. 
For a book the impression-mark includes the properties content, title, author and topic. The journal impression-mark includes 
the content, title and author. 


Examples for impression-marks:
{content:”book”, title:”The Dark Code”, author:”Bruce Wayne”, topic:”Science”}
{content:”book”, title:”How to make money”, author:”Dr. Evil”, topic:”Business”}
{content:”journal”, title:”Journal of human flight routes”, author:”Clark Kent”}
**************************************************************************************************************************************



Import the project in eclipse and do a maven clean install and then run server

use apache HTTP port 28088


To run manually open browser and type

http://localhost:28088/impression-ser/rest/document/book/The Dark Code

this will return a ticket


use this ticket and in another browser, type

http://localhost:28088/impression-ser/rest/document/ticket


where ticket is the one which is returned by the previous web service call


The call to 
http://localhost:28088/impression-ser/rest/document/ticket


goes to a polling web service. Keep on refreshing browser for sometime and then
you will get json output once the document has been impression marked








Another option is to copy ImpressionMark.html in web server root directory and
access it through a browser and give the neccessary inputs i.e document type and
title and hit submit request, this will return a ticket which will be shown in the
ticket field of the form. Then hit poll service, which will pick up the returned ticket
and start polling and will return the json document object once impression marking is done

