package com.xingray.mvp;

/**
 * description : View的生命周期状态
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/2/21 20:34
 */
public enum LifeCycle implements Comparable<LifeCycle> {
    /**
     * 初始状态
     */
    INIT,

    /**
     * 已创建
     */
    CREATE,

    /**
     * 已开始
     */
    START,

    /**
     * 开始交互
     */
    RESUME,

    /**
     * 暂停
     */
    PAUSE,

    /**
     * 停止
     */
    STOP,

    /**
     * 销毁
     */
    DESTROY
}
