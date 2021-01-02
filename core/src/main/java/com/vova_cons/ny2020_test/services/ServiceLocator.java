package com.vova_cons.ny2020_test.services;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ServiceLocator {
    private static ServiceLocator instance = null;
    private static final String TAG = "ServiceLocator";
    private ObjectMap<Class<? extends Service>, Service> servicesMap = new ObjectMap<>();
    private Array<UpdatableService> updatableServices = new Array<>();

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
        if (service instanceof UpdatableService) {
            getInstance().updatableServices.add((UpdatableService) service);
        }
    }

    public static <T extends Service> boolean isExists(Class<T> type) {
        return getInstance().servicesMap.containsKey(type);
    }

    public static void update(float delta) {
        for(UpdatableService service : getInstance().updatableServices) {
            service.update(delta);
        }
    }
    //endregion
}
