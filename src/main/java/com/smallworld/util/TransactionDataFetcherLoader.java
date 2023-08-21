package com.smallworld.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smallworld.dto.TransactionDTO;
import lombok.SneakyThrows;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author avaldez
 */
public final class TransactionDataFetcherLoader {

    protected TransactionDataFetcherLoader(){}

    @SneakyThrows
    public static List<TransactionDTO> loadTransactionFileJSON(final String jsonFileName) {
        final Gson gson = new Gson();
        final Reader reader = Files.newBufferedReader(Paths.get(jsonFileName));
        final List<TransactionDTO> transactionDTOStream =
                gson.fromJson(reader, new TypeToken<List<TransactionDTO>>() {}.getType());
        reader.close();
        return transactionDTOStream;
    }
}
