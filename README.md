# Introduction
Print-O-Matic is a Java application that serves as the final assignment for the Java foundation course. 

The application enables users to 
- create a user account
- add items to the basket
- review the order information
- edit user and/or shopping cart info
- calculate pickup time.
- export order to json file

# Build
I started with building the classes, which in the beginning were only User, Order, Item, OpeningHours and ProductCatalog. Later, I realized I needed a way to store information on each item added into the shopping cart, so I created the OrderQuant class, which stores the item and the corresponding amount. The ProductCatalog class using the csv reader class to read the items into a HashMap that store the items, price and production time. Later in the development I realized I wanted a way to store user information easily, so I created a private <USER> field inside the Order class. This could have been done with implementation, but doing it this way works all the same. 

The user interface was the next on my list. I built a MenuService that leads the user to create a user account first. It does that inside UserService, which can be accessed from different places inside the app. 
After the user is created, MenuService sends the user to OrderService, a class I designed to fill the shopping cart. This class can also be accessed from CheckoutService (more about that later).

Having added some items in the basket, the user gets sent back to MenuService, who sends it through to CheckoutService. Here, users can view their user information, shopping cart and pickup time. The pickup time is calculated in a different class, PickupTimeCalculator, which uses the WorkWeek pojo to calculate production time. This in turn takes its opening and closing hours from a csv that is read through a separate csv reader class.  They can also change their user information and edit their shopping cart. When that is done, the final order overview is printed one last time, before it is printed to JSON using the Gson library (very cool, btw!).

# Challenges
Initially, I had a hard time just conceptualizing the user flow in the program. Running everything through Main seemed very inefficient, so I came up with the service layer that the user had to go through. Halfway through, I found out that this was almost impossible, since some classes used the same third class for their methods (interface?). Also I think the app could benefit from some inheritance, for instance Order can inherit from User. 

The last part was calculating pickup time. Looping through an array list of opening hours turned out to be much harder than it seemed. Eventually, I figured out that converting all the LocalTimes and LocalDateTimes to int's was the handiest way to start my calculations, and the .plus function helped me out when adding the extra days and hours until the store was open again.   

# Requirements 
- The user needs to input either the id or name of the item to put it in the order.
- The user needs to be able to modify or add multiple items to the order.
- The user needs to be able to modify or add customer information for the order.
- The user needs to be able to create an invoice.
  - On the invoice, the entire order's total cost must be shown.
  - On the invoice, the completion time needs to be shown.
  - The pickup date and time need to be within the store's opening hours.
  - Keep in mind that PhotoShop only has one printer, so orders should stack accordingly.
- The shopping cart needs to be saved to and retrieved from either XML or Json. So that old orders can be reviewed.
- Use the CSV files for the opening hours and the price list.
- The program needs to be made completely in the command log.

# Considerations  
- Make sure you put sufficient comments in your code.
- Apply inheritance, for example, the order can inherit from a photo. 
- Use the naming conventions correctly and consistently.
- Make sure your code is efficient, readable, extendable and maintainable.
- Your grandma should be able to use your application, so make sure is clear and easy to use.
