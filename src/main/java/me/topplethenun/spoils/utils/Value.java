package me.topplethenun.spoils.utils;

/**
 * Used for holding a single value of any type that does not change.
 * @param <T> type of value
 */
public class Value<T> {

    private final T val;

    private Value(T val) {
        this.val = val;
    }

    /**
     * Gets and returns the held value.
     * @return held value
     */
    public T get() {
        return val;
    }

    /**
     * Creates a new {@code Value<T>} based on the type of the passed-in parameter.
     * @param val value to be held
     * @param <T> type of value
     * @return new Value
     */
    public static <T> Value<T> create(T val) {
        return new Value<>(val);
    }

}