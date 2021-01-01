package com.vova_cons.ny2020_test.utils;

@FunctionalInterface
public interface Callback {
    void handle();

    static void safeCall(Callback callback) {
        if (callback != null) {
            callback.handle();
        }
    }
}
