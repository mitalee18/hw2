package controller;

import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtering transactions according to category
 */
public class CategoryFilter implements TransactionFilter{

    @Override
    public List<Transaction> filter(List<Transaction> transactions, List<Object> filterValues) {

        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase((String) filterValues.get(0)))
                .collect(Collectors.toList());
    }
}
