package com.abostudios.api.exchanges.binance.exception;

import com.abostudios.api.exchanges.ExchangeExceptionResponse;
import com.abostudios.api.exchanges.ExchangeExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Binance Exception Handling Lookup Table</h1>
 * A constant static list of binance errors which links to a response that the
 * bot should take upon receiving this exception.
 *
 * @author ABOStudios
 * @version 1.0
 * @since 28/02/2018
 *
 */
public class BinanceExceptionHandler implements ExchangeExceptionHandler {

    //[Error Code] -> What should the bot do?
    private static final Map<Integer, ExchangeExceptionResponse> errorTable;
    static
    {
        errorTable = new HashMap<>();

        errorTable.put(418, ExchangeExceptionResponse.KILL); //Auto-banned after continuing to send requests after receiving 429
        errorTable.put(429, ExchangeExceptionResponse.KILL); //Breaks a request rate limit
        errorTable.put(504, ExchangeExceptionResponse.NOTHING); //API successfully sent message, but no response within timeout period.
    }

    @Override
    public ExchangeExceptionResponse getResponse(int code) {
        return errorTable.get(code);
    }
}
