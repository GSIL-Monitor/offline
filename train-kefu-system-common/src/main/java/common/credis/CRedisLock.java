package common.credis;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * distributed lock using CRedis
 * todo 2017-11-11
 * @author ying_zhou 2017/11/7 8:32
 */
public class CRedisLock implements Lock{
    @Override
    public void lock() {
        throw new NotImplementedException();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new NotImplementedException();
    }

    @Override
    public boolean tryLock() {
        throw new NotImplementedException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new NotImplementedException();
    }

    @Override
    public void unlock() {
        throw new NotImplementedException();
    }

    @Override
    public Condition newCondition() {
        throw new NotImplementedException();
    }
}
