package com.pattern.singleton;

/**
 * 双重校验锁单例模式
 */
public class DoubleCheckLockSingleton {

    // 保证在所有线程中保持同步
    private static volatile DoubleCheckLockSingleton instance = null;

    // 避免在外部被实例化
    private DoubleCheckLockSingleton(){}

    /**
     * 检查两次，一次不加锁，一次加锁
     * @return 单例对象
     */
    public static DoubleCheckLockSingleton getInstance(){
        // 第一次检查，不加锁
        if (instance == null) {
            synchronized (DoubleCheckLockSingleton.class) {
                // 第二次检查，加锁
                if (instance == null) {
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }

}
