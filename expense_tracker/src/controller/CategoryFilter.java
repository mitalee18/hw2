package controller;

import model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter{
    private final String[] validWords = {"food", "travel", "bills", "entertainment", "other"};
    @Override
    public List<Transaction> filter(List<Transaction> transactions, List<Object> filterValues) {

        return transactions.stream()
                .filter(t -> Arrays.asList(validWords).contains(t.getCategory()))
                .collect(Collectors.toList());
    }
}
