package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;

import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private final JTable transactionsTable;
  private final JButton addTransactionBtn;

  private final JButton addFilterBtn;

  private final JButton removeFilterBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private final DefaultTableModel model;

  private final JComboBox<String> filterBox;
  private final JFormattedTextField minAmountField;
  private final JFormattedTextField maxAmountField;

  private final JComboBox<String> categoryBox;
  

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(900, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    addFilterBtn = new JButton("Add Filter");
    removeFilterBtn = new JButton("Remove Filter");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);


    //dropdown
    JLabel filterTypeLabel = new JLabel("Select a filter type: ");
    String[] filterType = {"None","Category", "Amount"};
    filterBox = new JComboBox<>(filterType);

    String[] categoryFilterOption = {"None","food", "travel", "bills", "entertainment", "other"};
    categoryBox = new JComboBox<>(categoryFilterOption);
    minAmountField = new JFormattedTextField(format);
    maxAmountField = new JFormattedTextField(format);
    JLabel minAmountLabel = new JLabel("Minimum: ");
    minAmountField.setColumns(5);
    JLabel maxAmountLabel = new JLabel("Maximum: ");
    maxAmountField.setColumns(5);


    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);

    inputPanel.add(filterTypeLabel);
    inputPanel.add(filterBox);


    // Adding Action Listener on filter box
    filterBox.addActionListener(e -> {
      String filterSelected = (String) filterBox.getSelectedItem();
      assert filterSelected != null;
      if (filterSelected.equals("Category")){
        inputPanel.add(categoryBox).setVisible(true);
        inputPanel.add(minAmountLabel).setVisible(false);
        inputPanel.add(minAmountField).setVisible(false);
        inputPanel.add(maxAmountLabel).setVisible(false);
        inputPanel.add(maxAmountField).setVisible(false);
      }
      else if (filterSelected.equals("Amount")) {
        inputPanel.add(categoryBox).setVisible(false);
        inputPanel.add(minAmountLabel).setVisible(true);
        inputPanel.add(minAmountField).setVisible(true);
        inputPanel.add(maxAmountLabel).setVisible(true);
        inputPanel.add(maxAmountField).setVisible(true);
      }
      else{
        inputPanel.add(categoryBox).setVisible(false);
        inputPanel.add(minAmountLabel).setVisible(false);
        inputPanel.add(minAmountField).setVisible(false);
        inputPanel.add(maxAmountLabel).setVisible(false);
        inputPanel.add(maxAmountField).setVisible(false);
      }
      setVisible(true);
    });

    // Add Buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    buttonPanel.add(addFilterBtn);
    buttonPanel.add(removeFilterBtn);
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(1200, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }

      // Add total row
      Object[] totalRow = {"Total", null, null, totalCost};
      model.addRow(totalRow);
      // Fire table update
      transactionsTable.updateUI();
  
    }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
  public String getFilterSelected(){
    return (String) filterBox.getSelectedItem();
  }
  public JButton getAddFilterBtn() {
    return addFilterBtn;
  }

  public JButton getRemoveFilterBtn(){ return removeFilterBtn;}

  public double getMinAmountField(){
    if(minAmountField.getText().isEmpty()){
      return 0;
    }else{
      return Double.parseDouble(minAmountField.getText());
    }
  }

  public String getFilteredCategory(){
    return (String) categoryBox.getSelectedItem();
  }

  public double getMaxAmountField(){
    if(minAmountField.getText().isEmpty()){
      return 1000;
    }else{
      return Double.parseDouble(maxAmountField.getText());
    }
  }

  public void refreshTableWithColor(List<Transaction> allTransactions, List<Transaction> filteredTransactions) {
    // Clear existing rows
    model.setRowCount(0);
    // Get row count
    int rowNum = model.getRowCount();
    double totalCost=0;

    //calculating total transactions and adding into table
    for(Transaction t : allTransactions)
    {
      totalCost+=t.getAmount();
      model.addRow(new Object[]{
              rowNum+=1, t.getAmount(), t.getCategory(), t.getTimestamp()
      });
    }

    // Add total row
    Object[] totalRow = {"Total", null, null, totalCost};
    model.addRow(totalRow);

    // handle row coloring
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Set color if the transaction is present in filtered list
        if(row < allTransactions.size() && filteredTransactions.contains(allTransactions.get(row))) {
          c.setBackground(new Color(173, 255, 168)); // Light green
        }
        else {
          c.setBackground(Color.WHITE);
        }
        return c;
      }});

    // Fire table update
    transactionsTable.updateUI();

  }


}
