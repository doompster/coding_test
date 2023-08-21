package com.smallworld.dto;

import com.smallworld.util.TransactionDataFetcherLoader;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class TransactionDTOTest {

    private List<TransactionDTO> transactionDTOUnderTest;

    @Before
    @SneakyThrows
    public void setUp() {
        this.transactionDTOUnderTest =
                TransactionDataFetcherLoader.loadTransactionFileJSON("src/test/resources/transactions.json");
    }

    @Test
    public void verifyTransactionVO(){
        Assertions.assertThat(this.transactionDTOUnderTest)
                .isNotNull()
                .satisfies(transactionStream -> {
                    Assertions.assertThat(transactionStream.size()).isEqualTo(13);
                    Assertions.assertThat(transactionStream.get(0)).isNotNull();
                    Assertions.assertThat(transactionStream.get(0).getMtn()).isInstanceOf(Integer.class);
                    Assertions.assertThat(transactionStream.get(0).getAmount()).isInstanceOf(Double.class);
                    Assertions.assertThat(transactionStream.get(0).getSenderFullName()).isInstanceOf(String.class);
                    Assertions.assertThat(transactionStream.get(0).getSenderAge()).isInstanceOf(Integer.class);
                    Assertions.assertThat(transactionStream.get(0).getBeneficiaryFullName()).isInstanceOf(String.class);
                    Assertions.assertThat(transactionStream.get(0).getBeneficiaryAge()).isInstanceOf(Integer.class);
                    Assertions.assertThat(transactionStream.get(0).isIssueSolved()).isInstanceOf(Boolean.class);
                    Assertions.assertThat(transactionStream.get(0).getIssueId()).isInstanceOf(Integer.class);
                    Assertions.assertThat(transactionStream.get(0).getIssueMessage()).isInstanceOf(String.class);
                });
    }
}
