package com.xingray.sample.data;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 17:30
 */
public class StudentMockDataSource implements StudentDataSource {
    @Override
    public List<Student> loadStudents() {
        int count = 100;
        List<Student> list = new ArrayList<>(100);
        for (int i = 0; i < count; i++) {
            list.add(new Student(String.valueOf(i), "学生" + i, 10 + (i % 10)));
        }
        return list;
    }
}
