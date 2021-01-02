# java_rest_book_management

Starting:
1) To get all needed dependencies, in project folder run command: $ mvn install
2) To run the TomEE server, in project folder run command: $ mvn package tomee:run

Postman can be used to create POST and PATCH requests.

Put a book into the system, POST: http://localhost:8080/restapi/v1/books
Retreive book's information, GET: http://localhost:8080/restapi/v1/books/{barcode}
Retreive all books in the system, GET: http://localhost:8080/restapi/v1/books
Update a detail of a book, PATCH: http://localhost:8080/restapi/v1/books/{barcode}
Calculate the total price of specific books, GET: http://localhost:8080/restapi/v1/books/total_price/{barcode}

All data is saved in priject folder, /data/SS_h2_db.mv.db file.
File (DB table) can be viewed with DbVisualizer.