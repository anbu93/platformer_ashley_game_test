package com.vova_cons.ny2020_test.services;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static ServiceLocator instance = null;
    private static final String TAG = "ServiceLocator";
    private Map<Class<? extends Service>, Service> servicesMap = new HashMap<>();

    private static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public static <T extends Service> T getService(Class<T> type) {
        try {
            Service service = getInstance().servicesMap.get(type);
            return (T) service;
        } catch (Exception e) {
            throw new RuntimeException("get service " + type.getSimpleName() + " not found this service (unsupported cast)");
        }
    }

    public static <T extends Service> void register(Class<T> type, T service) {
        getInstance().servicesMap.put(type, service);
    }

    public static <T extends Service> boolean isExists(Class<T> type) {
        return getInstance().servicesMap.containsKey(type);
    }
    //endregion
}
