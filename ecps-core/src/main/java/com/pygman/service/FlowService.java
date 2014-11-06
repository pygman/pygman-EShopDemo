package com.pygman.service;

import com.pygman.model.TaskBean;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface FlowService {
    public void deployFlow();
    public String startProcess(Long orderId);
    public void completeTaskByPI(String processInstanceId,String outcome);
    public void completeTaskById(String taskId,String outcome);
    public List<TaskBean> selectTaskByAssignee(String assignee);
    public TaskBean selectTaskBeanById(String taskId);
}
