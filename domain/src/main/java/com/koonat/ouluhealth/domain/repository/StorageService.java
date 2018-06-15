package com.koonat.ouluhealth.domain.repository;

public interface StorageService {
    void storeString(String key, String value);

    String readString(String key, String defaultValue);

    void storeInt(String key, int value);

    int readInt(String key, int defaultValue);
}
