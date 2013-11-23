package the.utils;

import static com.google.common.collect.Maps.newHashMap;
import static the.helper.Helper.isNull;
import the.helper.Helper;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


public class Singletons
{
    public static final String EVENTMACHINECLIENT = "EVENTMACHINECLIENT";
    
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final WriteLock writeLock = lock.writeLock();
    private static final ReadLock readLock = lock.readLock();

    public static interface InstanceCreator
    {
        public Object create();
    }

    private static class Instance
    {
        private InstanceCreator creator;
        private Object instance;

        private Instance(InstanceCreator creator)
        {
            this.creator = creator;
        }

        public Object create()
        {
            instance = creator.create();
            return this;
        }

        public Object object()
        {
            return instance;
        }

        public boolean isNull()
        {
            return Helper.isNull(instance);
        }

        public static Instance aNew(InstanceCreator creator)
        {
            return new Instance(creator);
        }
    }

    private static Map<Class<?>, Instance> instances = newHashMap();

    public static void init(Class<?> instanceClass, InstanceCreator creator)
    {
        Instance instance = instances.get(instanceClass);
        if (isNull(instance))
        {
            writeLock.lock();
            try {
                instance = Instance.aNew(creator);
                instances.put(instanceClass, instance);
            }
            finally {
                writeLock.unlock();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <K> K get(Class<K> instanceClass)
    {
        K object = null;
        readLock.lock();
        try {
            Instance instance = instances.get(instanceClass);
            instance = createObjectAndSetToInstance(instance);
            object = (K) instance.object();
        } 
        finally {
           readLock.unlock(); 
        }
        return object;
    }

    private static <K> Instance createObjectAndSetToInstance(Instance instance)
    {
        readLock.unlock();
        writeLock.lock();
        try {
            if (instance.isNull())
            {
                instance.create();
            }
        }
        finally {
            readLock.lock();
            writeLock.unlock();
        }
        return instance;
    }

    public static <K> void reset(Class<K> instanceClass)
    {
        writeLock.lock();
        try {
            instances.remove(instanceClass);
        }
        finally {
            writeLock.unlock();
        }
    }
}
