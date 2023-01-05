package main;

import java.util.Set;
import redis.clients.jedis.Jedis;


public class DB {
    // naming schema for Redis database
    public static final String SCHEMA = "paths:%s";

    /**
     * Create a connection based on the arguments.
     * @param args
     * @param d
     */
    public static Jedis createConnection(String[] args, int d) {
        // if the number of arguments doesn't match, with and without
        // optional host/port arguments, then return null
        if (args.length != d && args.length != d + 2) {
            return null;
        }

        // if the number of given arguments equals the expected number
        // of arguments, then use default host and port
        if (args.length == d) {
            return new Jedis("127.0.0.1", 6379);
        }

        // account for optional host and port arguments
        return new Jedis(args[args.length - 1], Integer.parseInt(args[args.length - 2]));
    }

    /**
     * Add a key-value pair to Redis database.
     * @param jedis
     * @param key
     * @param value
     */
    public static void addDirectory(Jedis jedis, String key, String value) {
        if (value.equals(".")) {
            String curr = System.getProperty("user.dir");
            jedis.set(String.format(SCHEMA, key), curr);
        } else {
            jedis.set(String.format(SCHEMA, key), value);
        }
    }

    /**
     * Get the value of a key from Redis database.
     * @param jedis
     * @param key
     * @return String value
     */
    public static String getDirectory(Jedis jedis, String key) {
        return jedis.get(String.format(SCHEMA, key));
    }

    /**
     * Remove a key-value pair from Redis database.
     * @param jedis
     * @param key
     */
    public static void removeDirectory(Jedis jedis, String key) {
        jedis.del(String.format(SCHEMA, key));
    }

    /**
     * Get all key-value pairs from Redis database.
     * @param jedis
     * @return KeyValuePair[] key-value pairs
     */
    public static KeyValuePair[] getAllDirectories(Jedis jedis) {
        // get set of strings and convert to array
        Set<String> keySet = jedis.keys(String.format(SCHEMA, "*"));

        // get array of keys
        String[] keys = keySet.toArray(new String[keySet.size()]);

        // initialize KeyValuePair array
        KeyValuePair[] pairs = new KeyValuePair[keys.length];

        // for each key, create a key-value pair
        for (int i = 0; i < pairs.length; i++) {
            // get current key
            String key = keys[i].substring(SCHEMA.length() - 2);

            // get value of key
            String value = getDirectory(jedis, key);

            // make and store key-value pair
            pairs[i] = new KeyValuePair(key, value);
        }

        return pairs;
    }


    /**
     * Save a snapshot of database.
     * @param jedis
     */
    public static void saveAllData(Jedis jedis) {
        jedis.bgsave();
    }
}
