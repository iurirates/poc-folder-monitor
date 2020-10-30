# poc-folder-monitor

## Description
This project was built to monitor a directory and when new files are inserted it checks those with the extension .dat and performs the import, otherwise it discards the file without processing.

The idea of the records contained in the files is to import sellers, customers and sales with their items included.

###### Example registration for seller
The salesperson data has the format id 001 and the line will have the following format:
- 001çCPFçNameçSalary

###### Example of customer registration
The customer data has the format id 002 and the line will have the following format:
- 002çCNPJçNameçBusiness Area

###### Registration example for sale
Sales data has the format id 003. Within the sales line, there is the list
of items, which is surrounded by square brackets []. The line will have the following format:
- 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

## Directory Structure
The directory structure is all defined inside the application.yml file that is inside the "src / resources" directory and it is validated when the application starts, if the folders do not exist, it recreates in this hierarchy:

- HOME_DIR
	- IN
		- READ
		- DISCARD
	- OUT
	
The application observes the IN folder when a file is received in that folder, it validates if it has the extension .dat to parse the information after processing it is moved to the READ folder
if it is not in the valid extension it is moved to the DISCARD folder. At the end of all file processing, a file is generated in the OUT folder with the name containing the date it was processed plus the extension .done.dat

In this output file, the following information regarding the processing of all files is generated:
  - Number of customers in the input file
  - Salesperson quantity in the input file
  - Most expensive sale ID
  - The worst seller
 
## Technologies Used
- Java 11
- Spring Boot
- H2 Database running in memory with data structure defined in the schema.sql file that is inside the "src / resources" directory
- Spring JPA
- Lombok
- Maven
- Yaml
 
## How to Run the Project
To execute the project just enter the application directory and execute the command 'mvn clean install' that at the end of the initialization the application will be executing
 
## TODO
 - [ ] Implement Unit Tests
