package main;

public class KeyValuePair {
    // key of the pair
    private String key;

    // value of the pair
    private String value;

    /**
     * Create a new key-value pair.
     * @param key
     * @param value
     */
    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key of the key-value pair.
     * @return String key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the value of the key-value pair.
     * @return String value
     */
    public String getValue() {
        return this.value;
    }
}
