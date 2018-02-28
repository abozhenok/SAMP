package com.abostudios.api.exchanges;

/**
 * <h1>Exchange Exception Handling Table</h1>
 * Due to the catastrophic effects that a bot can have when an error occurs. This
 * was added to make the bot more robust and reduce errors while the bot is running.
 *
 * @author ABOStudios
 * @version 1.0
 * @since 28/02/2018
 *
 */
public interface ExchangeExceptionHandler {
    ExchangeExceptionResponse getResponse(int code);
}
