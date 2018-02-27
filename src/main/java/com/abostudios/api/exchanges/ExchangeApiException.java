package com.abostudios.api.exchanges;

/**
 * An exception which can occur while invoking methods of the Binance API.
 */
public class ExchangeApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
/**
   * Error response object returned by Binance API.
   */
  private ExchangeApiError error;

  /**
   * Instantiates a new binance api exception.
   *
   * @param error an error response object
   */
  public ExchangeApiException(ExchangeApiError error) {
    this.error = error;
  }

  /**
   * Instantiates a new binance api exception.
   */
  public ExchangeApiException() {
    super();
  }

  /**
   * Instantiates a new binance api exception.
   *
   * @param message the message
   */
  public ExchangeApiException(String message) {
    super(message);
  }

  /**
   * Instantiates a new binance api exception.
   *
   * @param cause the cause
   */
  public ExchangeApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new binance api exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public ExchangeApiException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @return the response error object from Binance API, or null if no response object was returned (e.g. server returned 500).
   */
  public ExchangeApiError getError() {
    return error;
  }

  @Override
  public String getMessage() {
    if (error != null) {
      return error.getMsg();
    }
    return super.getMessage();
  }
}
