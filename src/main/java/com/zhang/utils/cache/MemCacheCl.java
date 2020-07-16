package com.zhang.utils.cache;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
 
public class MemCacheCl {
    private static final Logger LOG = LoggerFactory.getLogger(MemCacheCl.class);
 
    public static final MemCachedClient mcc = new MemCachedClient();
 
    static {
        String ip = "127.0.0.1";
        int port = 11211;
        String server = ip + ":" + port;
        LOG.info("Conn Memcache {}", server);
 
        String[] servers = { server, };
 
        Integer[] weights = { 3, 2 };
 
        SockIOPool pool = SockIOPool.getInstance();
 
        pool.setServers(servers);
        pool.setWeights(weights);
 
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);
 
        pool.initialize();
    }
 
    public String get(String key) {
        String value = "";
        try {
            if (mcc.get(key) != null) {
                value = mcc.get(key).toString();
            }
        } catch (Exception e) {
            LOG.error("memcached get exception: " + key, e);
        }
 
        LOG.debug("GET: key:{}, value:{}", key, value);
        return value;
    }
 
    public boolean delete(String key) {
        try {
            LOG.info("DEL: key:{}", key);
            return mcc.delete(key);
        } catch (Exception e) {
            LOG.error("memcached delete exception: " + key, e);
        }
        return false;
    }
 
    public boolean set(String key, String value, long millisecond) {
        try {
            LOG.info("SET: key:{}, value:{}, expiretime:{}", key, value, millisecond);
            Date expiry = new Date(millisecond);
            return mcc.set(key, value, expiry);
        } catch (Exception e) {
            LOG.error("memcached set exception: " + key, e);
        }
        return false;
    }
 
    public boolean set(String key, String value) {
        try {
            LOG.info("SET: key:{}, value:{}", key, value);
            return mcc.set(key, value);
        } catch (Exception e) {
            LOG.error("memcached set exception: " + key, e);
        }
        return false;
    }
 
    public long incr(String key) {
        return mcc.incr(key);
    }
 
    public long addOrIncr(String key, long value) {
        LOG.info("SET: key:{}, value:{}", key, value);
        return mcc.addOrIncr(key, value);
    }
 
    public static List<String> getAllKeys(MemCachedClient mcc) {
        LOG.info("Get all key.......");
        List<String> list = new ArrayList<String>();
        Map<String, Map<String, String>> items = mcc.statsItems();
        for (Iterator<String> itemIt = items.keySet().iterator(); itemIt.hasNext();) {
            String itemKey = itemIt.next();
            Map<String, String> maps = items.get(itemKey);
            for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt.hasNext();) {
                String mapsKey = mapsIt.next();
                String mapsValue = maps.get(mapsKey);
                if (mapsKey.endsWith("number")) { // memcached key 类型
                                                  // item_str:integer:number_str
                    String[] arr = mapsKey.split(":");
                    int slabNumber = Integer.valueOf(arr[1].trim());
                    int limit = Integer.valueOf(mapsValue.trim());
                    Map<String, Map<String, String>> dumpMaps = mcc.statsCacheDump(slabNumber,
                            limit);
                    for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt
                            .hasNext();) {
                        String dumpKey = dumpIt.next();
                        Map<String, String> allMap = dumpMaps.get(dumpKey);
                        for (Iterator<String> allIt = allMap.keySet().iterator(); allIt
                                .hasNext();) {
                            String allKey = allIt.next();
                            list.add(allKey.trim());
                        }
                    }
                }
            }
        }
        LOG.info("获取服务器中所有的key完成.......");
        return list;
    }
 
    public static void getAllKeyVal(List<String> keys) {
        LOG.info("--------------------getAllKeyVal");
        for (String key : keys) {
            Object val =  mcc.get(key);
            LOG.info("get key: {} , val:{}", key, val);
        }
    }
 
    public static void delAllKeys(List<String> keys) {
        LOG.info("--------------------delAllKeys");
        for (String key : keys) {
            boolean ret = mcc.delete(key);
            LOG.info("delete key: {} , succ:{}", key, ret);
        }
    }
 
    public static void regexGetKey(List<String> keyList, String regex) {
        LOG.info("--------------------regex: {}", regex);
        for (String key : keyList) {
            if (key.matches(regex)) {
                LOG.info("key: {}", key);
            }
        }
    }
 
    public static void main(String[] args) {
        // mcc.delete("XXXXABC");
//        System.out.println(mcc.set("abc", "-1"));
//        System.out.println(mcc.incr("abc"));
//        System.out.println("-----------------------------------");
        // System.out.println(mcc.addOrIncr("abc", 1));
        // String key = "";
        // String cache = (String)mcc.get(key);
        // System.out.println(key+"----"+cache);
 
        List<String> keys = getAllKeys(mcc);
        getAllKeyVal(keys);
    }
 
}