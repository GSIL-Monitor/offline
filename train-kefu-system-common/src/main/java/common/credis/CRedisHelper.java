package common.credis;

import com.alibaba.fastjson.JSON;
import common.constants.CommonConstant;
import common.util.StringUtils;
import credis.java.client.CacheProvider;
import credis.java.client.util.CacheFactory;

import java.util.concurrent.TimeUnit;

/**
 * Redis 操作
 */
public class CRedisHelper {
    private static CacheProvider provider = CacheFactory.GetProvider(CommonConstant.C_REDIS_CLUSTER_NAME);

    public static String get(String key){
        return provider.get(key);
    }
    /**
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public static  <T> T get(String key,Class<T> type){
        String value=get(key);
        if(StringUtils.isBlank(value)){
            return null;
        }
        T object=JSON.parseObject(value,type);
        return object;
    }

    /**
     * cache a value without expired timme
     * @param key
     * @param value
     * @return
     */
    public static  boolean set(String key,String value){
        if(StringUtils.isBlank(value)){
            return  false;
        }
        return provider.set(key,value);
    }

    private static boolean set(String key,String value,int expireSeconds){
        if(set(key, value)){
            return provider.expire(key,expireSeconds);
        }
        return false;
    }

    /**
     * set cache with expired time
     * and the time's unit can be specified
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public  static boolean set(String key, String value, int expireTime, TimeUnit timeUnit){
        return  set(key, value, (int) timeUnit.toSeconds(expireTime));
    }

    /**
     * convert the value into json format
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public static boolean set(String key,Object value,int expireTime, TimeUnit timeUnit){
        String jsonValue=JSON.toJSONString(value);
        return set(key, jsonValue, expireTime, timeUnit);
    }

    /**
     *
     * @param key
     * @return -1 if there is no expired time and -2 of the key do not exist
     */
    public static long getExpiredTime(String key){
        Long rs= provider.ttl(key);
        if(rs!=null){
            return rs;
        }
        else{
            return -1;
        }
    }

    /**
     *
     * @param key
     * @return rue if key is removed.
     */
    public static boolean deleteKey(String key){
         if(StringUtils.isEmpty(key)){
             return false;
         }
        return provider.del(key);
    }

}
