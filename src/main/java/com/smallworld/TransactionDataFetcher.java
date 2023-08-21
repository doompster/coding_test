package com.smallworld;

import com.smallworld.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author avaldez
 */
@Getter
@Setter
@AllArgsConstructor
public final class TransactionDataFetcher {
    private final List<TransactionDTO> transactions;

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        return io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .mapToDouble(TransactionDTO::getAmount)
                .sum();
    }
    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(final String senderFullName) {
        return io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .filter(transactionDTO -> StringUtils.equals(transactionDTO.getSenderFullName(), senderFullName))
                .mapToDouble(TransactionDTO::getAmount)
                .sum();
    }
    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .mapToDouble(TransactionDTO::getAmount)
                .max()
                .orElseThrow(NoSuchElementException::new);
    }
    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        final Set<String> s1 = this.transactions.stream()
                .map(TransactionDTO::getSenderFullName)
                .collect(Collectors.toSet());
        final Set<String> s2 = this.transactions.stream()
                .map(TransactionDTO::getBeneficiaryFullName)
                .collect(Collectors.toSet());
        return Stream.concat(s1.stream(), s2.stream())
                .collect(Collectors.toSet())
                .stream().count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        return this.transactions.stream()
                .filter(transaction -> StringUtils.equals(transaction.getSenderFullName(), clientFullName)
                        || StringUtils.equals(transaction.getBeneficiaryFullName(), clientFullName))
                .sorted(Comparator.comparing(TransactionDTO::isIssueSolved))
                .collect(Collectors.toMap(TransactionDTO::getIssueId, TransactionDTO::isIssueSolved,
                        (existing, replacement) -> replacement))
                .containsValue(false);
    }
    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Object> getTransactionsByBeneficiaryName() {
        return io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .collect(Collectors.toMap(TransactionDTO::getBeneficiaryFullName, Function.identity()));
    }
    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        return this.transactions.stream()
                .sorted(Comparator.comparing(TransactionDTO::getMtn)
                        .thenComparing(TransactionDTO::isIssueSolved))
                .collect(Collectors.toMap(TransactionDTO::getMtn, TransactionDTO::isIssueSolved,
                        (existing, replacement) -> replacement))
                .entrySet()
                .stream()
                .filter(integerBooleanEntry -> !integerBooleanEntry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        return this.transactions.stream()
                .filter(transaction -> transaction.isIssueSolved()
                        && Objects.nonNull(transaction.getIssueMessage()))
                .map(TransactionDTO::getIssueMessage)
                .toList();
    }
    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Object> getTop3TransactionsByAmount() {
        final List<Object> transactionVOList = io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .sorted(Comparator.comparing(TransactionDTO::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
        Collections.reverse(transactionVOList);
        return transactionVOList;
    }
    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Object> getTopSender() {
        return io.vavr.collection.List.ofAll(this.transactions)
                .distinctBy(TransactionDTO::getMtn)
                .toJavaList().stream()
                .collect(Collectors.toMap(TransactionDTO::getSenderFullName, TransactionDTO::getAmount,
                        (existing, replacement) -> existing + replacement))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(stringDoubleEntry -> (Object)stringDoubleEntry.getKey())
                .findFirst();
    }

}
