package ru.otus.cachehw;

import java.util.Objects;

public class CacheKey<K>{
    private final K key;

    public CacheKey(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheKey<?> cacheKey = (CacheKey<?>) o;

        return Objects.equals(key, cacheKey.key);
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
