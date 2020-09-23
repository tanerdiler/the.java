package the.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CountTest {


    @Test
    void should_equal_to_diff_instance_with_same_value() {
        var count1 = Count.of(100L);
        var count2 = Count.of(100L);
        assertEquals(count1, count2, "Count should equal to diff instance with same value");
    }

    @Test
    void should_not_equal_to_diff_instance_with_same_value() {
        var count1 = Count.of(100L);
        var count2 = Count.of(101L);
        assertNotEquals(count1, count2, "Count should not equal to diff instance with diff value");
    }

    @Test
    void should_generate_same_hash_code_with_instance_of_same_value() {
        var count1 = Count.of(100L);
        var count2 = Count.of(100L);
        assertEquals(count1.hashCode(), count2.hashCode(), "Count should generate same hash code");
    }

    @Test
    void should_generate_diff_hash_code_with_instance_of_diff_value() {
        var count1 = Count.of(100L);
        var count2 = Count.of(102L);
        assertNotEquals(count1.hashCode(), count2.hashCode(), "Count should not sgenerate same hash code");
    }

    @Test
    void should_return_value_without_modification() {
        var count = Count.of(100L);
        assertEquals(count.getValue(), 100L, "Count should return value without modification");
    }
}