# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## New Functionality
1. Applied encapsulation for list of transactions
2. Applied immutability on the list of transactions when the getter method is invoked.
3. Applied encapsulation to Transaction class to prevent external data modification by making all attributes private and final, and removed setters, to make the class support immutability.
4. Implemented filter feature by attribute type amount and category

### Filter Feature
1. Created a Transaction filter interface with ```filter``` method.
2. This class is being implemented by ```AmountFilter``` and ```CategoryFilter``` class.
3. Both these classes (AmountFilter and CategoryFilter) have their own implementation of ```filter``` method.
4. Added new elements in ExpenseTrackerView
   1. Dropdown for selecting filter type called ```filterBox```.
      1. Added action listener to this drop down to hide and un-hide the view elements of category dropdown, minimum and maximum amount text boxes.  
   2. Button for adding filter ```addFilterBtn```
      1. Added action listener to this button to call ```applyFilter``` (implemented in the controller) with Type of filter and the value of filter.
      2. ```applyFilter``` method would then call the ```filter``` method in ```AmountFilter``` or ```CategoryFilter``` class depending on the type of filter selected and pass values of filter and receive filtered list from them.
      3. ```applyFilter``` method would then pass all transactions and filtered transactions to ```refreshTableWithColor``` function in view class
   3. Button for removing filter ```removeFilterBtn```
      1. Added action listener to this button to call ```removeFilter``` (implemented in the controller)
      2. The ```removeFilter``` method called ```refreshTableWithColor``` and passes all transactions and an empty list to remove color.
   4. Dropdown for selecting type of category when filter type is category
   5. Empty text boxes for inputting minimum and maximum amount for filtering according to amount range