package com.abostudios.api.exchanges.binance.impl;

import com.abostudios.api.exchanges.ExchangeApiError;
import com.abostudios.api.exchanges.binance.constant.BinanceApiConstants;
import com.abostudios.api.exchanges.ExchangeApiException;
import com.abostudios.api.security.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Generates a Binance API implementation based on @see {@link BinanceApiService}.
 */
public class BinanceApiServiceGenerator {

    public static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder =
        new Retrofit.Builder()
            .baseUrl(BinanceApiConstants.API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                ExchangeApiError apiError = getBinanceApiError(response);
                throw new ExchangeApiException(apiError);
            }
        } catch (IOException e) {
            throw new ExchangeApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static ExchangeApiError getBinanceApiError(Response<?> response) throws IOException, ExchangeApiException {
        return (ExchangeApiError)retrofit.responseBodyConverter(ExchangeApiError.class, new Annotation[0])
            .convert(response.errorBody());
    }
}