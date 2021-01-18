<<<<<<< HEAD
# Codecool shop (sprint 2)

## Story

It's time to enhance the Online Shop, an online eCommerce web-application with Java.
Where users can not only browse products, add them into a Shopping Cart,
checkout items and make payments. But also can log in and see the abandoned shopping cart or order history.

> Did you know that the very first product on eBay was a broken laser pointer?
> If you don't believe, check their history: [eBay history](https://www.ebayinc.com/company/our-history/)

## What are you going to learn?

- how to log properly and why does it important
- how to use property files to have project settings separately
- writing tests and mocking out dependencies to ensure the correct functionality and gain confidence at future  modification
- using database to make the data persistent
- how to use the `DAO` design pattern in `Java`,
- refreshing SQL knowledge

## Tasks

1. Create a new sprint tab on the existing backlog. Last week you had a long list of stories, a few new stories this week.
    - The new items are added to the backlog
    - The team has created a sprint 2 plan based upon the unified backlog

2. As you will work in a new repository but you need the code from the previous sprint, add the `codecool-shop-2` repository as a new remote to the previous sprint's repository, then pull (merge) and push your changes into it.
    - There is a merge commit in the project's repository that contains code from the previous sprint

3. As a Developer, I want to cover the `ProductDao`, `ProductCategoryDao` and `SupplierDao` memory implementation with tests. So that I can safely change the implementation later.
    - All methods of the DAOs should be tested independently from their dependencies, using mocking.
    - When I run the tests from IDE then I see at least 11 passed tests.

4. As a Developer, I want to have proper log messages when any failure happens to make it possible to find any malfunction in the system and track back user complaints.
    - With the dependency management tool logging facade is put under the app. In case of Java: Maven is used to add SLF4J to the application as dependency.
    - All exceptions are logged with proper description message.

5. As an Operator of the Shop, I want the product data to be persistent. So that I can restart the application without loosing product data.
    - There is an empty PostgreSQL database called `codecoolshop`
    - There is an initializer script file `src/main/sql/init_db.sql`. When I run the script file then all of the empty tables are created that will store `Products`, `ProductCategories` and `Suppliers`.
    - When the page is loaded and DB is used, suppliers, product categories and products are loaded from the database via `ProductDaoJdbc`, `ProductCategoryDaoJdbc`and `SupplierDaoJdbc`.

6. As a Developer, I want to read the DAO settings and DB connection parameters (url, database name, usr, pwd) from a config file so I am able to change the settings of the application on every environment without compiling again (like test environment - memory DB, production - PostgreSQL)
    - Given that I have the config file `src/main/resources/connection.properties`
with the following structure (exact values can be vary)
```
url: localhost:5432
database: codecoolshop
user: postgres
password: postgres
dao: memory```
    - Application reads the dao type settings from the property file. When the setting is `memory` then memory dao implementations are used. When the setting is `jdbc` then JDBC dao implementations are used.
    - Application reads the DB settings from the property file. `JDBC Connection` is initialized based on these settings.

7. As a Developer, I want to cover the `ProductDao`, `ProductCategoryDao` and `SupplierDao` JDBC implementation with tests. So that I can ensure them working correctly.
    - All methods of all JDBC DAO should be tested.
    - Given I have a PostgreSQL database called `codecoolshop` (url: localhost:5432, usr/pwd: postgres/postgres) with empty tables for `Product`, `ProductCategories` and `Suppliers` When I run the tests from IDE then I see at least 11 passed tests.

8. As an Operator of the Shop, I want to keep Order data safe and persistent, so that I won't loose money because of technical issues.
    - Given the User started a checkout process. Then ensure it saves all Order data into database (in each and every step, except cart).

9. As a User, I want to sign up (make a personal account) so that I can store Orders to my personal account.
    - there is a `Sign up` option on the website
    - it has a form with all the required fields:
- `name`
- `email`
- `password`
    - when the user submits the form with correct/valid information then ensure the system saves it's data as a new `User`
    - the system sends a welcome email after successful registration
    - When the User submits the form with incorrect/invalid information then ensure the program shows the same form with the incorrect data, and some description about the errors.

10. As a User, I want to able to login, so I can authenticate myself and access my personal data. I want to be able to logout so I can close my session.
    - There is a `Login` menu on the website
    - When the user chooses the "Login" menu
then ensure to provide a login form with the following fields:
- email address
- password
    - When the user submits the form with valid information then authenticate and give a new logged-in session to the User
    - When the user submits the form with invalid information then provide an error message
    - Ensure to provide a Logout option for loggend in users. When the user chooses the "Logout" option then reset the session and redirect back to the login form.

11. As a loggedin User, I want to see my Order history, so that I can see my previous Orders and follow their status.
    - There is an `Order history` menu item
    - provide a list with all the Orders of that user, with the following details:
- order date
- order status (checked / paid / confirmed / shipped)
- total price
- product list (with product name, price)

12. As a logged-in User, I want to save the current items of my Shopping cart so that I can order my selected Products later.
    - there is a `Save my cart` button (on the Shopping cart review page)
    - by clicking on this button the system saves the cart items into the database - for that loggedin User
    - Given that there is a User with a previously saved shopping cart. When the user finished the login process then ensure to refill the user's shopping cart with the saved items.

13. As a loggedin User, I want to save my billing and shipping info (to my personal account) so that I don't need to type these data all the time - during checkout.
    - there is a `Billing info` menu item
    - after clicking on the menu a provide a form where the user can fill in
the personal billing and shipping info (what is needed for the checkout)
    - Given there's a Shopping Cart review page. When I click on the "Checkout" button then ensure the system shows the pre-filled billing and shipping info on the checkout form.

## General requirements

- Advanced OOP concepts are used in the project: inheritance, there is at
least on abstract class, there is at least one interface implemented
=======
# Codecool shop (sprint 1)

## Story

Everyone loves to buy and sell stuff but we need a shop for that! In this
project the goal is to build one the [most common type of websites on the
web](https://www.expertmarket.co.uk/web-design/different-types-of-websites): an
online eCommerce web-application, where users can browse products, add them into
a shopping cart, checkout items and make payments.

## What are you going to learn?

- how to create dynamic web pages in `Java` with `servlets`,
- how to use the `DAO` design pattern in `Java`,
- how to use the `Thymeleaf` templating engine.

## Tasks

1. As a Developer, I want to have a version-controlled Java project, where a Java webserver serves requests. So that I can start developing in a sandboxed environment.
    - Given I startup my Java web-application server, when I open `http://localhost:8888` in my browser, then ensure the server gives back an index page

2. As a User, I want to have an index page, where I can see the list of Products within a default Product Category, so that I can browse Products within that Category.
    - Given I have Products and a default Product Category in the application when I open the root url (`/`) then ensure I can see a list of Products with the following details: product title, description, image, price

3. As a User, I want to have an index page, where I can filter Products by Product Categories so that I can browse Products within any Category.
    - Given I have Products and Product Categories listed on the index page when I click on a Category's title then ensure it displays the Products only in the selected Category

4. As a User, I want to have an index page, where I can filter Products by Suppliers so that I can browse Products by Suppliers too.
    - Given I have Products and Suppliers listed on the index page when I click on a Supplier's name then ensure it displays the Products only for the selected Supplier

5. As a User, I want to have a Shopping Cart so that I can add products which I want to buy.
    - Given I have a Product list and the Products have an "Add to cart" button when I click on the "Add to cart" button then ensure it creates a new Order for storing cart data of the User and ensure it creates a new LineItem with the quanity (default: 1) and price (the price of the Product) and ensure it stores this data on the server
    - Given I have a Product list and the Products have an "Add to cart" button when I click on the "Add to cart" button then ensure the number of cart items is displayed in the Page header

6. As a User, I want to review my Shopping Cart so that I can review the items in my shopping cart before checking out so that I can see what I've already selected.
    - Given I have a Shopping Cart with items in it when I click on the "Shopping cart" menu item in the Page header then ensure it displays the items (LineItems) with the following data: name of the Product, quantity, unit price / subtotal price
    - Given I have a Shopping Cart with items in it when I click on the "Shopping cart" menu item in the Page header ensure the total price of all the items in the cart is displayed

7. As a User, I want to edit the items in my Shopping Cart so that I can modify the items when I change my mind - by changing quantity or removing items.
    - Given I have a Shopping Cart review page and the LineItems are displayed in a form and the quantities are displayed in input fields when I change the quantity of an item then ensure it stores the new quantity of the LineItem
    - Given I have a Shopping Cart review page and the LineItems are displayed in a form and the quantities are displayed in input fields when I change the quantity to 0 then ensure it removes the LineItem from the cart

8. As a User, I want to checkout the items from the Shopping Cart so that I can order the Products.
    - Given I have a Shopping Cart review page when I click on the "Checkout" button then ensure it asks the following data from the User: Name, Email, Phone number, Billing Address (Country, City, Zipcode, Address), Shipping Address (Country, City, Zipcode, Address)
    - Given I have a Shopping Cart review page when I click on the "Go to Payment" button then ensure Ensure that data on the checkout form are validated
    - Given I have a Shopping Cart review page when I click on the "Go to Payment" button and data are validated successfully then ensure that data on the checkout form are stored in the Order
    - Given I have a Shopping Cart review page when I click on the "Go to Payment" button and data are validated successfully then ensure that it redirects to the Payment page

9. As a User, I want to pay for my Products so that I can complete the payment online.
    - Given I checked out the items from the Shopping cart then I can see the total price what I have to pay
    - Given I checked out the items from the Shopping cart then I can choose from the following payment methods: credit card, paypal
    - Given I checked out the items from the Shopping cart and chosen the payment method then based on the selected payment method I can enter the credentials for the payment provider: card number, card holder, expiry date, code (in case of credit card), username and password (in case of paypal)

10. As a User, I want to see the result of my payment so that I can get a confirmation about my Order.
    - Given I made a payment and there was an error in the transaction then I can see the details of the error
    - Given I made a payment and the transaction was successful then I can see the Confirmation page with the details of the Order
    - Given I made a payment and the transaction was successful then then ensure it saves the Order into a `JSON` file
    - Given I made a payment and the transaction was successful then ensure it sends an email to the User about the Order

11. As an Admin, I want to have a logfile about the checkout processes (per Order) so that I can see the steps of every Order and investigate issues.
    - Given the User started a checkout process then ensure it saves all the steps and details into a JSON file (where the filename is the Order ID and Date)

## General requirements

- Advanced OOP concepts are used in the project: inheritance, there is at least on abstract class, there is at least one interface implemented
>>>>>>> project-a/development
- The page doesn't show a server error anytime during the review
- All code is pushed to GitHub repository by atomic commits. The implemented feature related commits managed on separated feature branches and merged by a pull request to the `master` branch.

## Hints

<<<<<<< HEAD
- It's not required to integrate real payment services - you can use fake payment implementations.
- Test (and use) the DAO implementations via interfaces so that it will be easy to change the implementation behind the interface. JUnit also provides support for this case, i. e. running the same test set against several implementations of the same interface.

## Background materials

- <i class="far fa-exclamation"></i> [Logging](project/curriculum/materials/pages/general/logging.md)
- <i class="far fa-exclamation"></i> [Logging with SLF4J](project/curriculum/materials/pages/java/logging-with-slf4j.md)
- <i class="far fa-exclamation"></i> [Mocking](project/curriculum/materials/pages/general/mocking.md)
- <i class="far fa-exclamation"></i> [Unit tests, stubs, mocks quick overview by Martin Fowler](https://www.youtube.com/watch?v=sEFhB71tmPM)
- <i class="far fa-exclamation"></i> [Java Dao pattern](https://www.baeldung.com/java-dao-pattern)
- <i class="far fa-exclamation"></i> [Introducing servlets](project/curriculum/materials/pages/java/introducing-servlets.md)
- <i class="far fa-exclamation"></i> [Servlet tutorial](https://www.tutorialspoint.com/servlets/servlets-form-data.htm)
- <i class="far fa-exclamation"></i> [Java properties](https://www.baeldung.com/java-properties)
=======
- Do not use a database, now only use in-memory storage or file storage but
  through the DAO pattern (Data Access Object).
- It's not required to integrate real payment services - you can use fake
  payment implementations.

## Background materials

- <i class="far fa-exclamation"></i> [Introducing servlets](project/curriculum/materials/pages/java/introducing-servlets.md)
- <i class="far fa-exclamation"></i> [Servlet tutorial](https://www.tutorialspoint.com/servlets/servlets-form-data.htm)
- <i class="far fa-exclamation"></i> [Java Dao pattern](https://www.baeldung.com/java-dao-pattern)
- <i class="far fa-exclamation"></i> [Thymeleaf standard dialect](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html)
- <i class="far fa-book-open"></i> [Thymeleaf introductions](https://www.thymeleaf.org/documentation.html#introductions)
>>>>>>> project-a/development
