package com.abostudios.api.exchanges.binance.examples;

import com.abostudios.api.exchanges.binance.BinanceApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;
import com.abostudios.api.domain.account.Account;
import com.abostudios.api.domain.account.AssetBalance;
import com.abostudios.api.domain.event.UserDataUpdateEvent;

import java.util.Map;
import java.util.TreeMap;

/**
 * Illustrates how to use the user data event stream to create a local cache for the balance of an account.
 */
public class AccountBalanceCacheExample {

    private final BinanceApiClientFactory clientFactory;

    /**
     * Key is the symbol, and the value is the balance of that symbol on the account.
     */
    private Map<String, AssetBalance> accountBalanceCache;

    public AccountBalanceCacheExample(String apiKey, String secret) {
        this.clientFactory = BinanceApiClientFactory.newInstance(apiKey, secret);

        //Listen key used to interact with the user data streaming API.
        String listenKey = initializeAssetBalanceCacheAndStreamSession();
        startAccountBalanceEventStreaming(listenKey);
    }

    /**
     * Initializes the asset balance cache by using the REST API and starts a new user data streaming session.
     *
     * @return a listenKey that can be used with the user data streaming API.
     */
    private String initializeAssetBalanceCacheAndStreamSession() {
        ExchangeApiRestClient client = clientFactory.newRestClient();
        Account account = client.getAccount();

        this.accountBalanceCache = new TreeMap<>();
        for (AssetBalance assetBalance : account.getBalances()) {
            accountBalanceCache.put(assetBalance.getAsset(), assetBalance);
        }

        return client.startUserDataStream();
    }

    /**
     * Begins streaming of agg trades events.
     */
    private void startAccountBalanceEventStreaming(String listenKey) {
        ExchangeApiWebSocketClient client = clientFactory.newWebSocketClient();

        client.onUserDataUpdateEvent(listenKey, response -> {
            if (response.getEventType() == UserDataUpdateEvent.UserDataUpdateEventType.ACCOUNT_UPDATE) {
                // Override cached asset balances
                for (AssetBalance assetBalance : response.getAccountUpdateEvent().getBalances()) {
                    accountBalanceCache.put(assetBalance.getAsset(), assetBalance);
                }
                System.out.println(accountBalanceCache);
            }
        });
    }

    /**
     * @return an account balance cache, containing the balance for every asset in this account.
     */
    public Map<String, AssetBalance> getAccountBalanceCache() {
        return accountBalanceCache;
    }

    public static void main(String[] args) {
        new AccountBalanceCacheExample("YOUR_API_KEY", "YOUR_SECRET");
    }
}
