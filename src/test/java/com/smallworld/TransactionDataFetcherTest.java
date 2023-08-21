package com.smallworld;

import com.smallworld.dto.TransactionDTO;
import com.smallworld.util.TransactionDataFetcherLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDataFetcherTest {

    private TransactionDataFetcher transactionDataFetcherUnderTest;

    @Before
    public void setUp() {
        this.transactionDataFetcherUnderTest = new TransactionDataFetcher(TransactionDataFetcherLoader
                .loadTransactionFileJSON("src/test/resources/transactions.json"));
    }

    @Test
    public void testGetTotalTransactionAmount() {
        assertThat(transactionDataFetcherUnderTest.getTotalTransactionAmount())
                .isEqualTo(2889.17);
    }

    @Test
    public void testGetTotalTransactionAmountSentBy() {
        assertThat(transactionDataFetcherUnderTest.getTotalTransactionAmountSentBy("Tom Shelby"))
                .isEqualTo(678.06);
    }

    @Test
    public void testGetMaxTransactionAmount() {
        assertThat(transactionDataFetcherUnderTest.getMaxTransactionAmount())
                .isEqualTo(985.0);
    }

    @Test
    public void testCountUniqueClients() {
        assertThat(transactionDataFetcherUnderTest.countUniqueClients()).isEqualTo(14L);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        assertThat(transactionDataFetcherUnderTest
                .hasOpenComplianceIssues("Tom Shelby")).isTrue();
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        // Run the test
        final Map<String, Object> result =
                transactionDataFetcherUnderTest.getTransactionsByBeneficiaryName();

        // Verify the results
        assertThat(result)
                .isNotNull()
                .satisfies(transactionsByBeneficiary -> {
                    assertThat(result.size()).isEqualTo(10);
                });
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        assertThat(transactionDataFetcherUnderTest.getUnsolvedIssueIds())
                .isNotNull()
                .satisfies(transaction -> {
                    assertThat(transaction.size()).isEqualTo(2);
                });
    }

    @Test
    public void testGetAllSolvedIssueMessages() {
        assertThat(transactionDataFetcherUnderTest.getAllSolvedIssueMessages())
                .isNotNull()
                .satisfies(transaction -> {
                    assertThat(transaction.size()).isEqualTo(3);
                });
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        assertThat(transactionDataFetcherUnderTest.getTop3TransactionsByAmount())
                .isNotNull()
                .satisfies((transactions -> {
                    assertThat(transactions.size()).isEqualTo(3);
                    assertThat((TransactionDTO)transactions.get(0))
                            .isNotNull();
                    assertThat(((TransactionDTO)transactions.get(0)).getAmount())
                            .isEqualTo(430.2);
                    assertThat((TransactionDTO)transactions.get(1))
                            .isNotNull();
                    assertThat(((TransactionDTO)transactions.get(1)).getAmount())
                            .isEqualTo(666.0);
                    assertThat((TransactionDTO)transactions.get(2))
                            .isNotNull();
                    assertThat(((TransactionDTO)transactions.get(2)).getAmount())
                            .isEqualTo(985.0);

                }));
    }

    @Test
    public void testGetTopSender() {
        assertThat(transactionDataFetcherUnderTest.getTopSender())
                .isEqualTo(Optional.of("Arthur Shelby"));
    }
}
