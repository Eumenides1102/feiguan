package com.bjtu.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisInstance {
    //Privatization constructor
    private JedisInstance(){ }

    //Define a static enumeration class
    static enum SingletonEnum{
        //Create an enumeration object that is inherently singleton
        INSTANCE;
        private JedisPool jedisPool;
        //Constructor for privatizing enumeration
        private SingletonEnum(){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(30);
            config.setMaxIdle(10);

            jedisPool = new JedisPool(config, "127.0.0.1", 6379);
        }
        public JedisPool getInstnce(){
            return jedisPool;
        }
    }
 
    //Expose a static method to get the user object
    public static JedisPool getInstance(){
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

