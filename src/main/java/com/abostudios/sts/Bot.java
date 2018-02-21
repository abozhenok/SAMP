package com.abostudios.sts;

import com.abostudios.api.Exchange;
import com.abostudios.sts.strategies.Strategy;

public class Bot {
    private Strategy strategy;
    private Exchange exchange;

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

    public Bot(Strategy strategy, Exchange exchange) {
        this.strategy = strategy;
        this.exchange = exchange;
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
                return strategy.entry(exchange);
            }

            case ACTIVE_ORDER: {
                return strategy.activeOrder(exchange);
            }

            case EXIT: {
                return strategy.exit(exchange);
            }

            //Something went wrong?, destroy the bot
            default: {
                return State.DESTROY;
            }
        }
    }

}
