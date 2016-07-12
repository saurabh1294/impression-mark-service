-> Dependencies for running this project will be taken care of by installing maven plugin

Configure maven for eclipse from the below link


http://maven.apache.org/guides/getting-started/index.html






-> Import the project in eclipse and do a maven clean install and then run server

-> use apache HTTP port 28088 in eclipse




-> To run manually open browser and type

http://localhost:28088/impression-ser/rest/document/book/The Dark Code

this will return a ticket


-> use this ticket and in another browser, type

http://localhost:28088/impression-ser/rest/document/ticket


where ticket is the one which is returned by the previous web service call


The call to 
http://localhost:28088/impression-ser/rest/document/ticket
goes to a polling web service. Keep on refreshing browser for sometime and then
you will get json output once the document has been impression marked.








->Another option is to copy ImpressionMark.html in web server root directory and
access it through a browser and give the neccessary inputs i.e document type and
title and hit submit request, this will return a ticket which will be shown in the
ticket field of the form. Then hit poll service, which will pick up the returned ticket
and start polling and will return the json document object once impression marking is done
To use this option:- 


1) if web service and web service front end, ImpressionMark.html are on
same local machine and if you want to test this service behaviour in chrome web browser then
launch chrome web browser by pressing 


Window Key + R and typing chrome.exe --disable-web-security. This to get past the same origin
policy of the web borwser which restrict calling web service located on same machine.

Once this is done, type:- localhost/ImpressionMark.html in browser address bar and hit enter.
The web service front end appears. Now you can give the necessary inputs and check the output

