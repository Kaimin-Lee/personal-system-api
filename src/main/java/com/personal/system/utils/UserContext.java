package com.personal.system.utils;

/**
 * 全局用户上下文（基于 ThreadLocal）
 */
public class UserContext {

    // 存放当前线程的 userId
    private static final ThreadLocal<Long> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存入 userId
     */
    public static void setUserId(Long userId) {
        USER_THREAD_LOCAL.set(userId);
    }

    /**
     * 获取 userId
     */
    public static Long getUserId() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 移除 userId (极其重要！防止内存泄漏和线程池复用导致的数据错乱)
     */
    public static void removeUserId() {
        USER_THREAD_LOCAL.remove();
    }
}