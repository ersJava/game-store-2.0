# Game Store Invoice Service 2.0

<img src="https://github.com/ersJava/game-store-2.0/blob/master/Screen%20Shot%202019-10-02%20at%2010.19.49%20PM.png" alt="video game store mock up">


## How It Works

A simple service that allows CRUD operations for a video game store inventory system and creates an invoice for the order.

Please see YAML for API documentation.

## Project Details

This application is a simple database backed REST inventory management web service for a Video Game Store. The Game Store Invoice Service is a typical Spring Boot REST web service with controller, DAO (utilizing Jdbc template and prepared statements), unit test and service layer components. Exceptions are handled through Controller Advice and return proper HTTP status codes and data when exception occur and violations of business rules. 


### Business Rules

- Sales tax applies only to the cost of the items.
- Each item has it's own unique processing fee. Any orders over 10 will have an additional processing fee of $15.49 is applied to the order
- The order process logic properly updates the quantity so the order quantity must be less than or equal to the number of items on hand in the inventory.

### Security

- <b>Manager role</b>: can create new items, update inventory, make invoices for orders and do a search on invoices
- <b>Admin role</b>: can update inventory items and delete and search invoices
- <b>Staff role</b>: can update inventory items and search invoices

Anyone: can perform searches for items

### Technologies Used
* Java
* Spring Boot
* MySQL
* Mockito
* Spring Security
