package com.smallworld;

import com.smallworld.dto.TransactionDTO;
import com.smallworld.util.TransactionDataFetcherLoader;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDataFetcherOneRecordTest {

    private TransactionDataFetcher transactionDataFetcherUnderTest;

    @Before
    public void setUp() {
        this.transactionDataFetcherUnderTest = new TransactionDataFetcher(TransactionDataFetcherLoader
                .loadTransactionFileJSON("src/test/resources/transactionsOneRecord.json"));
    }

    @Test
    public void testGetTotalTransactionAmount() {
        assertThat(transactionDataFetcherUnderTest.getTotalTransactionAmount())
                .isEqualTo(430.2);
    }

    @Test
    public void testGetTotalTransactionAmountSentBy() {
        assertThat(transactionDataFetcherUnderTest.getTotalTransactionAmountSentBy("Tom Shelby"))
                .isEqualTo(430.2);
    }

    @Test
    public void testGetMaxTransactionAmount() {
        assertThat(transactionDataFetcherUnderTest.getMaxTransactionAmount())
                .isEqualTo(430.2);
    }

    @Test
    public void testCountUniqueClients() {
        assertThat(transactionDataFetcherUnderTest.countUniqueClients()).isEqualTo(2L);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        assertThat(transactionDataFetcherUnderTest
                .hasOpenComplianceIssues("Tom Shelby")).isTrue();
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        // Setup
        // Run the test
        final Map<String, Object> result =
                transactionDataFetcherUnderTest.getTransactionsByBeneficiaryName();

        // Verify the results
        assertThat(result.containsKey("Alfie Solomons")).isTrue();
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        assertThat(transactionDataFetcherUnderTest.getUnsolvedIssueIds().size())
                .isEqualTo(1)
                .satisfies(transaction -> {
                    assertThat(transaction).isEqualTo(1);
                });
    }

    @Test
    public void testGetAllSolvedIssueMessages() {
        assertThat(transactionDataFetcherUnderTest.getAllSolvedIssueMessages())
                .isEqualTo(List.of());
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        assertThat(transactionDataFetcherUnderTest.getTop3TransactionsByAmount())
                .isNotNull()
                .satisfies((transactions -> {
                    assertThat(transactions.size()).isEqualTo(1);
                    assertThat((TransactionDTO)transactions.get(0))
                            .isNotNull();
                    assertThat(((TransactionDTO)transactions.get(0)).getAmount())
                            .isEqualTo(430.2);

                }));
    }

    @Test
    public void testGetTopSender() {
        assertThat(transactionDataFetcherUnderTest.getTopSender())
                .isEqualTo(Optional.of("Tom Shelby"));
    }
}
