package com.CacheSystem;

import com.framework.HashMap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class Cache implements ReadWriteLock {
    HashMap hashMap;
    public Cache() {
        this.hashMap = hashMap;
    }
    public Object getResult(String key) {
        return hashMap.get(key);
    }

    //基于synchronized
    public static synchronized Object getDataSynchronized(String key){
        Cache cache = new Cache();
        Object result = cache.getResult(key);
        if (result == null){
            result = "get from database";
        }
        return result;
    }

    @Override
    public Lock writeLock() {
        return null;
    }

    @Override
    public Lock readLock() {
        return null;
    }

    //基于读写锁
    public static Object getDataLock(String key){
        Cache cache = new Cache();
        cache.readLock().lock();//在读前先上读锁
        Object result = null;
        try{
            result = cache.getResult(key);
            //这个if比较关键，它避免了多余的几次对数据哭的读取
            if(result==null){
                //如果内存中没有所要数据
                cache.readLock().unlock();
                cache.writeLock().lock();
                if(result==null){
                    try{
                        //我们用这个代替对数据库访问得到数据的步骤
                        result = "get from database";
                    }finally{
                        cache.writeLock().unlock();
                    }
                    cache.readLock().lock();
                }
            }
        }finally{
            cache.readLock().unlock();
        }
        return result;
    }
}
