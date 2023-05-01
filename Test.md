## Test 1: IP address input(localhost)
Prerequisite : `Server.java` is running on local machine

Steps:

1. User launches application.
 
2. User clicks `enter`
 
Expected result: Application establishes connection with localhost, and brings user to `log-in page`.

Test Status: Passed. 

## Test 2: IP address input(Remote host)

Prerequisite: `Server.java` is running on a remote machine without a firewall and the user has the remote machine’s ip address.

Steps: 

1. User launches application.

2. User clicks the `IP address text box`.

3. User enters the server IP address.

4. User clicks `enter`

Expected result: Application establishes connection with remote host, and brings users to `log-in page`.

Test Status: Passed. 

## Test 3: User sign up

Steps:

1. User launches application.

2. User selects `customer` from the dropdown menu (default).

3. User selects the `username textbox`.

4. User enters username via the keyboard.

5. User selects the `password textbox`.

6. User enters the password via the keyboard.

7. User selects the `email textbox`.

8. User enters the email via the keyboard.

9. User selects the `Sign up` button.

Expected result: Application verifies that the input email/username does not exist within the database, it then pop up a `JOptionPane` saying Sign Up success, and prompt the user to Log In again. 

Test Status: Passed.

## Test 4: User log in

Steps:

1. User launches application.

2. User selects the `username textbox`.

3. User enters username via the keyboard.

4. User selects the `password textbox`.

5. User enters the password via the keyboard.

6. User selects the `Log in` button. 

Expected result: Application verifies the user's `username` and `password` and loads their homepage automatically. 
Test Status: Passed. 

## Test 5: Buy Item 

Steps:

1. Performs **Test 2**’s steps twice to establish 2 remote connections to `Server`. Each connection will be a thread.
  - One of the users must be a seller.
  - The other user must be a customer.
2. The seller client creates a new store within their clientGUI side using the `create store` button, and fills in the appropriate store name within the `JOptionPane`.
3. The seller client adds 5 new products to the created store using the `importCSV` button, and fills in the desired file path within the `JOptionPane`.
4. The seller client adds one extra product to the created store using the `add product ` button, and fills in the appropriate fields within the `add product page`. 
5. The customer refreshes the page, s/he should see that the page is populated with 6 products from the store created in _step 2_.  
6. The customer chooses `purchase item`, fills in the appropriate quantity in the `JOptionPane`, and the purchase is completed. 
7. The customer chooses `view history`, and the information from the last purchase appears here.
8. Meanwhile, the seller can access the `view sale` button within the store created in _step 2_, and the customer purchase in _step 6_ should appear here.

Expected result: 

- All products should be added correctly and appear correctly on both the customer and seller side.

- The information from the purchase should be reflected in multiple places: the quantity available of the product should decrease on both the seller and customer side

- The purchase information should be available to the customer via the `purchase history page`, and to the seller via the `sale history` located inside the corresponding `store page`. 

Test Status: Passed.

## Test 6: Modify/Delete Product

Steps: 

1. Continuing from *Step 8* of **Test 5**, the seller can choose one of the 6 listing products, and click on the `modify` button.
2. The seller then filled in the appropriate new information to modify the chosen product.
3. The customer then clicks the `refresh` button on his/her page, and the changes should be reflected on their page. 
4. The seller is now taken to the `store page`, s/he choose one of the 6 listing products, 
5. The seller clicks on the delete product, and the selected product should disappear on the seller side.
6. The customer then refreshes his/her page by clicking `refresh`, and the deleted product should also disappear on the `customer page`.

Expected result: the change the seller made is reflected on the customer side.

Test Status: Passed.

## Test 7: Contact Seller/Cust. Inbox

Steps: 

1. Continuing from *step 6* of **Test 6**, the customer can choose one of the listed products, and click on the `contact seller` button.
2. A `JOptionPanel` should pop up saying the seller has been notified.
3. On the seller's side, s/he can go into the `Cust. Inbox` button and an email associated with the customer from *step 1* should show up. 

Expected result: the seller can see the customer email on his/her end.

Test Status: Passed.

## Test 8: Export customer purchase history

Steps: 

1. Continuing from *step 3* of **Test 7**, the customer clicks `go back` to return to the customer page, and clicks `review purchase history`.
2. From the `review purchase history page`, the customer can click `export product history`, and the customer then fills in the desired file path for his/her history to be exported to.

Expected result: the customer can find the file in his/her specified path (note that if the input path is a relative path, the current directory is located at the location of the Client program).

Test Status: Passed.

## Test 9: Export store products

Steps: 

1. Continuing from *step 3* of **Test 7**,  the seller can go back to the seller page and choose a store to access its page. 
2. From that page, the seller can choose to export the current products of the chosen store,   
3. The seller then fills in an appropriate file path inside a `JOptionPane`. 

Expected result: the seller can find the file in his/her specified path (note that if the input path is a relative path, the current directory is located at the location of the client-side).

Test Status: Passed.

## Test 10: Edit account

1. Continuing from *step 3* of **Test 9**, the seller can `go back` to the seller page, and choose `edit account`. 
2. Within the `edit account page`, the seller can choose to change his/her `username`.
3. Filling in a new `username` and clicking `change id` will pop up a `JOptionPane` that announces the change is successful, if it does not match any other existing usernames.
4. The seller can `go back` to the seller page, and his/her `username` on the left side of the GUI should have changed. 
5. Going back to the `edit account page again`, the seller can change his/her `password`.
6. Filling in a new `password`, and clicking `change pw` will pop up a `JOptionPane` that announces the change is successful. 
7. The seller `go back` to the seller page, `log out`, and the seller should be taken to the `login page`. 
8. From the `login page`, the seller tries to log in with the old `id` and `password`, this should pop up an error on `JOptionPane`. 
9. The seller logs in with the new `id` and `password`, the seller should be taken to the `seller page`. 
10. Going back to the `edit account page`, the seller chooses to delete his/her account this time.
11. The user is then taken to the `login page`, he/she can no longer log in with neither the new credentials nor the old credentials. 
12. The customer from *step 2* of **Test 8** can `refresh` his/her page and all the products listed by the deleted seller from _step 10_ will disappear.

Expected result: 

- The `username` field within the GUI changed after the seller edits the `username`.

- The seller can no longer log in with the old credentials. However, the seller can log in with the new one. 

- The seller can no longer log in after deleting his/her account.

- The customer can no longer view products listed by the deleted seller.

Test Status: Passed.



