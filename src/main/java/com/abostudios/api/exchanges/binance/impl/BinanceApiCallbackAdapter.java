package com.abostudios.api.exchanges.binance.impl;

import com.abostudios.api.exchanges.ExchangeApiCallback;
import com.abostudios.api.exchanges.ExchangeApiError;
import com.abostudios.api.exchanges.ExchangeApiException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * An adapter/wrapper which transforms a Callback from Retrofit into a ExchangeApiCallback which is exposed to the client.
 */
public class BinanceApiCallbackAdapter<T> implements Callback<T> {

  private final ExchangeApiCallback<T> callback;

  public BinanceApiCallbackAdapter(ExchangeApiCallback<T> callback) {
    this.callback = callback;
  }

  public void onResponse(Call<T> call, Response<T> response) {
    if (response.isSuccessful()) {
      callback.onResponse(response.body());
    } else {
      if (response.code() == 504) {
        // HTTP 504 return code is used when the API successfully sent the message but not get a response within the timeout period.
        // It is important to NOT treat this as a failure; the execution status is UNKNOWN and could have been a success.
        return;
      }
      try {
        ExchangeApiError apiError = BinanceApiServiceGenerator.getBinanceApiError(response);
        throw new ExchangeApiException(apiError);
      } catch (IOException e) {
        throw new ExchangeApiException(e);
      }
    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable throwable) {
    throw new ExchangeApiException(throwable);
  }
}
