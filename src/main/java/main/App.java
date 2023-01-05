package main;

import redis.clients.jedis.Jedis;


/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        // if no arguments are provided, throw an error
        if (args.length <= 0) {
            throw new Exception("error: make sure you have more than one argument!");
        }

        // connection object
        Jedis jedis;

        // key value pair reference, in case it is needed
        KeyValuePair[] pairs;

        // switch statement for command
        if (args[0].equals("set")) {
            jedis = DB.createConnection(args, 3);
            DB.addDirectory(jedis, args[1], args[2]);
        } else if (args[0].equals("get")) {
            jedis = DB.createConnection(args, 2);
            System.out.println(DB.getDirectory(jedis, args[1]));
        } else if (args[0].equals("remove")) {
            jedis = DB.createConnection(args, 2);
            DB.removeDirectory(jedis, args[1]);
        } else if (args[0].equals("list")) {
            jedis = DB.createConnection(args, 1);
            pairs = DB.getAllDirectories(jedis);

            for (KeyValuePair pair : pairs) {
                System.out.println(String.format("%s > %s", pair.getKey(), pair.getValue()));
            }
        } else if (args[0].equals("reset")) {
            jedis = DB.createConnection(args, 1);
            pairs = DB.getAllDirectories(jedis);

            for (KeyValuePair pair : pairs) {
                DB.removeDirectory(jedis, pair.getKey());
            }
        } else {
            throw new Exception("error: please use a valid command");
        }

        jedis.close();
    }
}
