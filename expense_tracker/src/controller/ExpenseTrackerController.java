package controller;

import view.ExpenseTrackerView;

import java.util.Collections;
import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private final ExpenseTrackerModel model;
  private final ExpenseTrackerView view;

  private final TransactionFilter amtFilter;
  private final TransactionFilter catFilter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.catFilter = new CategoryFilter();
    this.amtFilter = new AmountFilter();

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  // Other controller methods

    /**
     *
     * @param filterList: list of transaction object on which we will apply filter
     * @param filterType: type of filter we want to apply
     * @return True or False to verify if we can apply filter, in case of amount because it has inputType as textbox and category has input as drop down
     */
  public boolean applyFilter(List<Object> filterList, String filterType){
        List<Transaction> allTransactions = model.getTransactions();
        List<Transaction> transactions;

        //checking the type of filter chosen
        if(filterType.equalsIgnoreCase("category")){
          if(((String) filterList.get(0)).equalsIgnoreCase("None")){
            return false;
          }
            transactions = catFilter.filter(allTransactions, filterList);
        }
        else{
          //validating if the filter input for min and max amount is valid
          if (!InputValidation.isValidAmount((double) filterList.get(0)) && !InputValidation.isValidAmount((double) filterList.get(1))) {
            return false;
          }
          transactions = amtFilter.filter(allTransactions, filterList);
        }

        view.refreshTableWithColor(allTransactions, transactions);

        return true;
  }

    /**
     * Removes filter from the filtered transaction
     */
  public void removerFilter(){
    view.refreshTableWithColor(model.getTransactions(), Collections.emptyList());
  }
}