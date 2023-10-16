import javax.swing.JOptionPane;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });


    view.getAddFilterBtn().addActionListener(e -> {
      String filterSelected = view.getFilterSelected();
      String category;
      double minAmount, maxAmount;
      List<Object> obj;
      boolean added;
      if (filterSelected.equals("Category")){
        category = view.getFilteredCategory();
        obj = new ArrayList<>();
        obj.add(category);
        added = controller.applyFilter(obj, filterSelected);
        if (!added) {
          JOptionPane.showMessageDialog(view, "Category Select is None");
          view.toFront();
        }
      }
      else if (filterSelected.equals("Amount")) {
        obj = new ArrayList<>();
        minAmount = view.getMinAmountField();
        maxAmount = view.getMaxAmountField();
        obj.add(minAmount);
        obj.add(maxAmount);
        added = controller.applyFilter(obj, filterSelected);
        if (!added) {
          JOptionPane.showMessageDialog(view, "Invalid minimum or maximum Amount");
          view.toFront();
        }
      }
      else{
        JOptionPane.showMessageDialog(view, "Filter Type not selected");
        view.toFront();
      }


    });

    view.getRemoveFilterBtn().addActionListener(e -> {
      controller.removerFilter();
    });

  }

}