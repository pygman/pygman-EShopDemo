package com.pygman.model;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public class TaskBean {
    private String taskId;
    private String businessKey;
    private String assignee;
    private List<String> comeouts;
    private EbOrder order;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<String> getComeouts() {
        return comeouts;
    }

    public void setComeouts(List<String> comeouts) {
        this.comeouts = comeouts;
    }

    public EbOrder getOrder() {
        return order;
    }

    public void setOrder(EbOrder order) {
        this.order = order;
    }
}
