package com.ecnice.privilege.cache;

import java.util.*;

import com.ecnice.privilege.api.privilege.impl.PrivilegeApiImpl;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.tools.common.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @className：CacheHandler
 * @description：缓存操作类，对缓存进行管理，采用处理队列，定时循环清理的方式
 * @remark：
 */
@Repository
public class CacheListHandler {
    private static final Logger logger = Logger.getLogger(CacheListHandler.class);
    private static final long SECOND_TIME = 1000;
    private static final SimpleConcurrentMap<String, CacheEntity> map;
    private static final Map<String, CacheEntity> tempList;

    static {
        tempList = new HashMap<String, CacheEntity>();
        map = new SimpleConcurrentMap<String, CacheEntity>(
                new HashMap<String, CacheEntity>(1 << 18));
        new Thread(new TimeoutTimerThread()).start();
    }

    /**
     * 增加缓存对象
     *
     * @param key
     * @param ce
     */
    public static void addCache(String key, CacheEntity ce) {
        addCache(key, ce, ce.getValidityTime());
    }

    /**
     * 增加缓存对象
     *
     * @param key
     * @param ce
     * @param validityTime 有效时间
     */
    public static synchronized void addCache(String key, CacheEntity ce,
                                             int validityTime) {
        ce.setTimeoutStamp(System.currentTimeMillis() + validityTime
                * SECOND_TIME);
        map.put(key, ce);
        // 添加到过期处理队列
        tempList.put(key, ce);
    }

    /**
     * 获取缓存对象
     *
     * @param key
     * @return
     */
    public static synchronized CacheEntity getCache(String key) {
        return map.get(key);
    }

    /**
     * 检查是否含有制定key的缓冲
     *
     * @param key
     * @return
     */
    public static synchronized boolean isConcurrent(String key) {
        return map.containsKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static synchronized void removeCache(String key) {
        map.remove(key);
    }

    /**
     * 批量删除缓存
     *
     * @param entities
     */
    public static synchronized void removeCaches(List<CacheEntity> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            for (CacheEntity cacheEntity : entities) {
                map.remove(cacheEntity.getCacheKey());
                tempList.remove(cacheEntity.getCacheKey());
            }
        }
    }

    /**
     * 获取缓存大小
     */
    public static int getCacheSize() {
        return map.size();
    }

    /**
     * 清除全部缓存
     */
    public static synchronized void clearCache() {
        tempList.clear();
        map.clear();
    }

    static class TimeoutTimerThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    checkTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 过期缓存的具体处理方法
         *
         * @throws Exception
         */
        private void checkTime() throws Exception {
            // "开始处理过期 ";
            long timoutTime = 10000L;

            // " 过期队列大小 : "+tempList.size());
            if (tempList.size() < 1) {
                Thread.sleep(timoutTime);
                return;
            }
            //找到需要清理的数据
            List<CacheEntity> removeList = new ArrayList<CacheEntity>();
            for (Map.Entry<String, CacheEntity> entry : tempList.entrySet()) {
                CacheEntity tce = entry.getValue();
                timoutTime = tce.getTimeoutStamp() - System.currentTimeMillis();
                // " 过期时间 : "+timoutTime);
                if (timoutTime < 0) {
                    // 清除过期缓存和删除对应的缓存队列
                    removeList.add(tce);
                }
            }
            // 清除过期缓存和删除对应的缓存队列
            if (CollectionUtils.isNotEmpty(removeList)) {
                removeCaches(removeList);
            }
            Thread.sleep(5000L);
        }
    }
}
