package controller;

import model.Transaction;

import java.util.List;

public interface TransactionFilter {
    public List<Transaction> filter(List<Transaction> transactions, List<Object> filterValues);
}
