package com.smallworld;

import com.google.gson.Gson;
import com.smallworld.util.TransactionDataFetcherLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author avaldez
 */
@Slf4j
public final class Application {
    @SneakyThrows
    public static void main(final String[] args){
        final TransactionDataFetcher transactionDataFetcher =
                new TransactionDataFetcher(
                        TransactionDataFetcherLoader
                                .loadTransactionFileJSON("src/main/resources/transactions.json"));
        log.info("Transactions by name, {}", transactionDataFetcher.getTransactionsByBeneficiaryName());
        log.info("Top 3 transactions by amount, {}", new Gson().toJson(transactionDataFetcher.getTop3TransactionsByAmount()));
    }
}
