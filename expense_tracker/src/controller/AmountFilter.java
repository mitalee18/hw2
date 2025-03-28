package controller;

import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtering transactions according to amount
 */
public class AmountFilter implements TransactionFilter{
    @Override
    public List<Transaction> filter(List<Transaction> transactions, List<Object> filterValues) {
        return transactions.stream()
                .filter(t -> t.getAmount() >= (Double) filterValues.get(0) && t.getAmount() <=  (Double) filterValues.get(1))
                .collect(Collectors.toList());
    }
}
