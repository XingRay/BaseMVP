package com.xingray.mvp;

/**
 * @author : leixing
 * @date : 2017-05-26
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 * Description : interface of view life cycle
 */

public interface LifeCycleObserver {
    void notifyLifeCycleChanged(LifeCycle lifeCycle);
}
