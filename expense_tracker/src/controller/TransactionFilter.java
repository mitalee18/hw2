package controller;

import model.Transaction;

import java.util.List;

/**
 * Interface for creating class which have the method filter
 */
public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions, List<Object> filterValues);
}
