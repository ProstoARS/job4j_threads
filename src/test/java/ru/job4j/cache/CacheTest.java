package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenCacheAddThenTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        assertThat(cache.add(model)).isTrue();
    }

    @Test
    public void whenUpdateAndVersionIncrement() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 0);
        user1.setName("user1");
        cache.add(user1);
        user1.setName("user2");
        boolean result = cache.update(user1);
        assertThat(result).isTrue();
        assertThat(cache.get(1).getVersion()).isEqualTo(1);
        assertThat(cache.get(1).getName()).isEqualTo("user2");
    }

    @Test
    public void whenException() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 0);
        Base user2 = new Base(1, 1);
        cache.add(user1);
        assertThatExceptionOfType(OptimisticException.class).isThrownBy(() -> cache.update(user2));
    }
}