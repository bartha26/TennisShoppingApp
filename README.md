# TennisShoppingApp
This is my first project In the 2nd year at the Technical University of Cluj-Napoca for the subject Object Oriented Programming
# Author
Bartha Tudor|2nd Year Computer Science | Technical University of Cluj-Napoca
# Description
The project is a desktop app.The project is built as a **tennis market** selling rackets,shorts,shirts and shoes.The clients cand make an **account** based on which they can make orders,see former orders and give reviews.The administrator has a special account based on which he can create a user,delete a user or update a user's password.
The application was built based on a [Maven Project](https://maven.apache.org/). The dependencies are:
* postgresql
* jdbc
* javafx

All the data is stored in a [PostrgreSQL](https://www.postgresql.org) [Database](https://en.wikipedia.org/wiki/Database) created by me.
The data is accessed through an **SQL Connection** realized by the jdbc.The project is complex from the **database** point of view because I implemented all CRUD operations on multiple tables.All the back end is done using [JAVA](https://en.wikipedia.org/wiki/Java_(programming_language)).

The front end id done using [JavaFx](https://openjfx.io/),[Scene Builder](https://gluonhq.com/products/scene-builder/) and [CSS](https://en.wikipedia.org/wiki/CSS).
# Main Classes
* class ColorProducts;
* class Company;
* class Item;
* class Order;
* class Products;
* class ProductSize;
* class ProductsOrder;
* class Racket;
* class Review;
* class Shirt;
* class Shoes;
* class ShoppingCart;
* class Shorts;
* class Users
* class ShoppingCartManager;//singleton class
* class UserManager;//singleton class

There are also other classes for **SQLConnection**, **Controller(JavaFX)** etc.  
Here is the full _class diagram_ of the project.
![Class Diagram](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Class-Diagram.png)

# Database
The database was created by me in [PostrgreSQL](https://www.postgresql.org)  and it has 20 tables.The database is updated automatically based on the actions in the database.The tables have all sorts of constraints to be sure only the valid data enters the database.The diagram of the database can only fit in two pictures:
![Database1](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Database1.png)
![Database2](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Database2.png)

# CSS
Here is an snippet of code from a css file I used for the payment View.
![CSS](https://github.com/bartha26/TennisShoppingApp/blob/main/res/CSS.png)

# Visuals
Here I added some pages from the app.
* LogIn Page
  ![LogIn](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Welcome-Page.png)
* SignUp Page
  ![SignUp](https://github.com/bartha26/TennisShoppingApp/blob/main/res/SignUp-Page.png)
* Main Page
  ![Main Page](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Main-Page.png)
* Review Page
  ![Review Page](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Review-Page.png)
* Finish Payment Page
  ![Finish Payment Page](https://github.com/bartha26/TennisShoppingApp/blob/main/res/Finish-Payment.png)

# Further Developments
1.I could implement a more user friendly interface,make the front end much better  
2.After I delete a user I should also delete it's orders.So I should automatically update the orders and productorders tables.
