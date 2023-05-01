# CS180-L24

## Project 5 team: Bao Phan, Ethan Lee

**Ethan Lee - Submitted Vocareum Workspace.**

**Bao Phan - Submitted Report on Brightspace.**

This program is called Boiler Fruit Market. It is developed using IntelliJ IDE 2022.3.2, and Amazon Correto 19 as its SDK. It also uses the "com.opencsv:opencsv:5.5" library to read/write CSV files. Build information can be found in the .idea directory.

To get started,
1. Run `Server.java`
2. Run `ClientGUI.java`
    - On `ClientGUI.java`, it will prompt for IP Address.
  
    - If you wish to connect to localhost, Click `Enter` button without entering anything in the text field.
  
    - If you wish to connect on a different computer
  
    - Disable firewall on the computer which the `Server.java` is running.
  
    - Get the IP address of the computer which the `Server.java` is running.
  
    - Paste the IP address into the textfield in `ClientGUI.java`.
       
3. Log in or sign up as either a customer or a seller.
4. Have fun!

Data Files:
`allUsers.ser` The file containing all the object data for the Server. Located in `/data`



## Class Descriptions:

### User

The `User` class is an abstract class that represents a user in a system. It implements the Serializable interface to support serialization of objects. The class contains three fields: `userName`, `email`, and `password`.

The `userName` field represents the username of the user and is a unique value per user. The `email` field also represents a unique value per user and is used as the key for the user in a HashMap. The `password` field represents the user's password.

The class has two constructors, which have been overloaded for different situations. The first constructor is used to read user data from a file, while the second constructor is used when a user signs up. Both constructors initialize the `userName`, `email`, and `password` fields.

The class also provides getter and setter methods for the `userName`, `email`, and `password` fields. However, no getter method is provided for the `password` field because it needs to be kept secure.

The User class provides a basic abstraction of a user in a system, with fields for username, email, and password, as well as constructors and getter/setter methods for those fields.


### Customer


The `Customer` class is a subclass of the `User` class and represents a customer in a system. It implements the Serializable interface to support serialization of objects. The class contains two fields: `purchaseHistory` and inherits the fields of `User` class: `userName`, `email`, and `password`.

The `purchaseHistory` field is an ArrayList that contains Strings, each of which represents information about a single purchase. The String includes the name of the `item`, `quantity`, `price`, and `store`.

The class provides two constructors. The first constructor takes four parameters: `userName`, `email`, `password`, and `purchaseHistory`. It calls the constructor of the superclass `User` to set the `userName`, `email`, and `password` fields, and initializes the `purchaseHistory` field using the provided ArrayList.

The second constructor takes three parameters: `userName`, `email`, and `password`. It calls the constructor of the superclass `User` to set the `userName`, `email`, and `password` fields, and initializes the `purchaseHistory` field with an empty ArrayList.

The class provides a getter method for the `purchaseHistory` field, which returns the ArrayList of purchase history Strings.

The class also provides a method `addToPurchaseHistory()` that takes two parameters: a `Product` object and an integer representing the quantity purchased. It creates a new String with the store name, product name, price, and quantity and adds it to the `purchaseHistory` field.

### Seller

The `Seller` class is a subclass of the `User` class and represents a seller in a system. It implements the Serializable interface to support serialization of objects. The class contains two fields: `stores` and `contactingCustomers`, and inherits the fields of the User class: `userName`, `email`, and `password`.

The stores field is a HashMap that contains Store objects owned by the seller. The keys of the HashMap are store names.

The `contactingCustomers` field is an ArrayList that contains customer emails who tried to contact the seller.

The class provides two constructors. The first constructor takes five parameters: `userName`, `email`, `password`, `stores`, and `contactingCustomers`. It calls the constructor of the superclass User to set the `userName`, `email`, and `password` fields, and initializes the `stores` and `contactingCustomers` fields using the provided HashMap and ArrayList, respectively.

The second constructor takes three parameters: `userName`, `email`, and `password`. It calls the constructor of the superclass User to set the `userName`, `email`, and `password` fields, and initializes the `stores` and `contactingCustomers` fields with an empty HashMap and an empty ArrayList, respectively. The pre-existing stores will be read from a file in a method from `fileIO` class and assigned after the instantiation of the object.

The class provides a getter method for the `stores` field, which returns the HashMap of stores owned by the seller. The class also provides a method `addStore()` that takes a `Store` object as a parameter and adds it to the seller's stores HashMap using the store name as the key.

The class provides a getter and a setter method for the `contactingCustomers` field, which returns and sets the ArrayList of customer emails who tried to contact the seller.

### Store
This class represents a store that can sell products. It has various fields such as `name`, `sellerEmail`, and `currentProducts`. It also has methods to get and set these fields, as well as methods to add, remove, and display products. 

The `Store` class connections:
To `Seller` class: one `Seller` can have multiple `Store`, which contains its own products, customers, revenue, etc.
To `Product` class: a store can have multiple products, and it can add, remove, and display these products using Product objects.

### Product
This class represents a `Product` that can be sold in a store. It has various fields such as `name`, `storeName`, `sellerEmail`, `description`, `price`, and `quantity`. It also has methods to get and set these fields, as well as a method to return the product's details as a string array. 

The Product class connections:  
To Store class: a store can have multiple products, and the Store class has methods to add, remove, and display products.

### FileIO
This class is responsible for reading and writing data to files. It has methods to read and write data for `User` objects. Since the program data are contained in `users` HashMap (a user could be a seller or a customer), the program keeps a HashMap of all users when it is running, and writes its values inside the `allUsers.ser` file located inside the `/data` folder for the next run. Additionally, the class also contained methods to handle all program file input/output, such as `exportPurchasedHistory` for `Customer`, `importCSV`, and `exportCSV` for `Store`. 

The `FileIO` class connections:  
To `User` class: the class that is read/written to files using this class to achieve data persistence. 
To `Customer` class: this class helps to export a particular Customer instance’s purchase history into the desired file.
To `Store` class: this class helps to import/export a particular CSV file to add/export a particular Store instance.

### ClientGUI
The `ClientGUI` implements runnable, and is invoked later.
This class is responsible for the GUI portion of the program. It is divided into multiple different panels that represent a certain “page” depending on the functionality. It uses one `Jframe` `frame`, and each of the page methods repaint the `frame` with the matching panel.
Within the panel, each button’s actionlistener triggers the appropriate query and sends it to `Server` class.
Depending on the protocol, If it is expected that `Server` class responds back to `ClientGUI` after a query, it will wait for it and type cast the object it received into another data type.
It handles certain exceptions that could occur from invalid input from the user.

### Server
The `Server` class implements runnable to achieve `Concurrency` and allow multiple clients to use the program at the same time. 

This class is responsible for all data processing of the program. It is basically a while loop that constantly listens to queries from clients and sends the corresponding replies based on the content of each predefined query. 

The `Server` class connections:
The `User` classes (`Customer` & `Seller`): server class keeps a HashMap of all users when running and uses this to control the program.
The `FileIO` class: server uses this class to achieve data persistence: it reads in existing users' data at the start, and writes down the users list when shut down.

