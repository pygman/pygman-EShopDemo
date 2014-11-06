package com.pygman.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by pygmalion on 2014/11/3.
 */
public class OrderFlowListener3 implements TaskListener {
    private static final long serialVersionUID=8848001899999575L;
    @Override
    public void notify(DelegateTask delegateTask) {
        String taskKey=delegateTask.getTaskDefinitionKey();
        delegateTask.setAssignee(taskKey+"er");
    }
}
