## JAVA Rest book management by S. Šilakauskas

Starting:  
1) To get all needed dependencies, in project folder run command: **`mvn install`**  
2) To run the TomEE server, in project folder run command: **`mvn package tomee:run`**  

**Postman** can be used to create POST and PATCH requests.  

Put a book into the system, **POST**: http://localhost:8080/restapi/v1/books  
Retreive book's information, **GET**: http://localhost:8080/restapi/v1/books/{barcode}  
Retreive all books in the system, **GET**: http://localhost:8080/restapi/v1/books  
Update a detail of a book, **PATCH**: http://localhost:8080/restapi/v1/books/{barcode}  
Delete a book, **DELETE**: http://localhost:8080/restapi/v1/books/{barcode}  
Calculate the total price of specific books, **GET**: http://localhost:8080/restapi/v1/books/total_price/{barcode}  

POST request body example (json):
`{  
	"author": "Steven Hawking",  
	"name": "A Brief History of Time",  
	"quantity": 4,  
	"type": "science journal",  
	"scienceIndex": 8,  
	"unitPrice": 39.33  
}`  
If a `type` of `science journal` is submitted, then attribute `scienceIndex` has to be provided in range between 1 and 10.  
If a `type` of `antique book` is submitted, then attribute `releaseYear` has to be provided as well and has to be <= 1900.  
For a usual book a `type` attribute has to be set to `book`.
All other attributes are mandatory.

All data is saved in project folder, **/data/SS_h2_db.mv.db** file.  
File (DB table) can be viewed with DbVisualizer.  

Used:  
o IntelliJ IDEA 2020.3.1  
o JDK 1.8.0_271  
o Maven 3.6.3  
o JAX-RS (Java EE 7)  
o H2 Database engine 1.4  
o Apache TomEE Maven 7.0.5  
