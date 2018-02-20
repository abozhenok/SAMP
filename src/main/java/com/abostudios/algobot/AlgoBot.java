package com.abostudios.algobot;

import com.abostudios.algobot.strategies.Strategy;

public class AlgoBot {
    private Strategy strategy;

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
        DESTROY //USE WITH CAUTION, Algo should not stop unless logic says otherwise
    }

    private State state;

    public AlgoBot(Strategy strategy) {
        this.strategy = strategy;
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
                return strategy.entry();
            }

            case ACTIVE_ORDER: {
                return strategy.activeOrder();
            }

            case EXIT: {
                return strategy.exit();
            }

            //Something went wrong?, destroy the bot
            default: {
                return State.DESTROY;
            }
        }
    }

}
