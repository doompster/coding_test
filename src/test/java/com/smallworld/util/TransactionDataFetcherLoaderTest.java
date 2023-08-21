package com.smallworld.util;

import com.smallworld.dto.TransactionDTO;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TransactionDataFetcherLoaderTest {

    private List<TransactionDTO> transactionDTOUnderTest;

    @Before
    @SneakyThrows
    public void setUp() {
        this.transactionDTOUnderTest =
                TransactionDataFetcherLoader.loadTransactionFileJSON("src/test/resources/transactions.json");
    }

    @Test
    public void testLoadTransactionFileJSON() {
        Assertions.assertThat(this.transactionDTOUnderTest)
                .isNotNull()
                .satisfies(transactions -> {
                    Assertions.assertThat(transactions.size()).isEqualTo(13);
                });
    }
}
