package com.abostudios.sts;

import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.sts.strategies.Strategy;

public class Bot {
    private Strategy strategy;
    private ExchangeApiClientFactory exchangeApiClientFactory;

    /**
     * Ticks through different algo states during the trading lifecycle
     *
     * ENTRY: Looking to place an order to open position
     * ACTIVE_ORDER: Waiting for a buy order to fill
     * EXIT: Looking to place an order to close position
     * DESTROY: Stops the algo bot
     */
    public enum State {
        ENTRY,
        ACTIVE_ORDER,
        EXIT,
        DESTROY //USE WITH CAUTION, Algo bot should not stop unless logic says otherwise
    }

    private State state;

    public Bot(Strategy strategy, ExchangeApiClientFactory exchangeApiClientFactory) {
        this.strategy = strategy;
        this.exchangeApiClientFactory = exchangeApiClientFactory;
        this.state = State.ENTRY;
    }

    public void run() {
        while(true) {
            state = update();
            if(state == State.DESTROY) break;
        }
    }

    private State update() {
        switch(state) {
            case ENTRY: {
                return strategy.entry(exchangeApiClientFactory);
            }

            case ACTIVE_ORDER: {
                return strategy.activeOrder(exchangeApiClientFactory);
            }

            case EXIT: {
                return strategy.exit(exchangeApiClientFactory);
            }

            //Something went wrong?, destroy the bot
            default: {
                return State.DESTROY;
            }
        }
    }

}
