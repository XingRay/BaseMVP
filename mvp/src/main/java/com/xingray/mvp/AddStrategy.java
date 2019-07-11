package com.xingray.mvp;

import java.util.List;

/**
 * description : 任务入队策略
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/2/21 20:42
 */
public interface AddStrategy<E> {

    int CODE_OVERRIDE = 0;
    int CODE_ADD_IF_NOT_EXIST = 1;
    int CODE_INSERT_TAIL = 2;
    int CODE_INSERT_HEAD = 3;

    /**
     * 添加任务到任务列表
     *
     * @param list 列表
     * @param e    待添加的元素
     */
    void addTask(List<E> list, E e);

    /**
     * 每一个策略需要有一个唯一的策略编号
     *
     * @return 策略编号
     */
    int getStrategyCode();


    /**
     * 覆盖
     */
    AddStrategy<PresenterTask> OVERRIDE = new AddStrategy<PresenterTask>() {
        @Override
        public void addTask(List<PresenterTask> list, PresenterTask e) {
            list.remove(e);
            list.add(e);
        }

        @Override
        public int getStrategyCode() {
            return CODE_OVERRIDE;
        }
    };


    /**
     * 不存在才添加
     */
    AddStrategy<PresenterTask> ADD_IF_NOT_EXIST = new AddStrategy<PresenterTask>() {
        @Override
        public void addTask(List<PresenterTask> taskList, PresenterTask task) {
            if (taskList.contains(task)) {
                return;
            }
            taskList.add(task);
        }

        @Override
        public int getStrategyCode() {
            return CODE_ADD_IF_NOT_EXIST;
        }
    };


    /**
     * 尾部插入
     */
    AddStrategy<PresenterTask> INSERT_TAIL = new AddStrategy<PresenterTask>() {
        @Override
        public void addTask(List<PresenterTask> taskList, PresenterTask task) {
            taskList.add(task);
        }

        @Override
        public int getStrategyCode() {
            return CODE_INSERT_TAIL;
        }
    };

    /**
     * 头部插入
     */
    AddStrategy<PresenterTask> INSERT_HEAD = new AddStrategy<PresenterTask>() {
        @Override
        public void addTask(List<PresenterTask> taskList, PresenterTask task) {
            taskList.add(0, task);
        }

        @Override
        public int getStrategyCode() {
            return CODE_INSERT_HEAD;
        }
    };

}
