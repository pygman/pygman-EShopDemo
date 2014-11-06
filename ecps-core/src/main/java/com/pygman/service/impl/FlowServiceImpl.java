package com.pygman.service.impl;

import com.pygman.model.TaskBean;
import com.pygman.service.FlowService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Override
    public void deployFlow() {
        DeploymentBuilder deploymentBuilder=repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("diagrams/OrderFlow.bpmn").addClasspathResource("diagrams/OrderFlow.png");
        deploymentBuilder.deploy();
    }

    @Override
    public String startProcess(Long orderId) {
        ProcessInstance pi=runtimeService.startProcessInstanceByKey("OrderFlow",orderId+"");
        return pi.getId();
    }

    @Override
    public void completeTaskByPI(String processInstanceId, String outcome) {
        Task task= taskService.createTaskQuery()
                .processDefinitionKey("OrderFlow")
                .processInstanceId(processInstanceId).singleResult();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("outcome",outcome);
        taskService.complete(task.getId(),map);
    }

    @Override
    public void completeTaskById(String taskId, String outcome) {
        Task task=taskService.createTaskQuery()
                .processDefinitionKey("OrderFlo").taskId(taskId)
                .singleResult();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("outcome",outcome);
        taskService.complete(task.getId(),map);
    }

    @Override
    public List<TaskBean> selectTaskByAssignee(String assignee) {
        List<Task> taskList=taskService.createTaskQuery()
                .processDefinitionKey("OrderFlow")
                .taskAssignee(assignee)
                .orderByTaskCreateTime()
                .desc()
                .list();
        List<TaskBean> tbList=new ArrayList<TaskBean>();
        ProcessInstanceQuery pq=runtimeService.createProcessInstanceQuery();
        for(Task task:taskList){
            TaskBean tb=new TaskBean();
            tb.setAssignee(assignee);
            tb.setTaskId(task.getId());
            ProcessInstance pi=pq.processDefinitionId(task.getProcessInstanceId()).singleResult();
            tb.setBusinessKey(pi.getBusinessKey());
            tbList.add(tb);
        }
        return tbList;
    }

    @Override
    public TaskBean selectTaskBeanById(String taskId) {
        Task task=taskService.createTaskQuery().processDefinitionKey("OrderFlow").taskId(taskId).singleResult();
        ProcessInstance pi=runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        TaskBean tb=new TaskBean();
        tb.setAssignee(task.getAssignee());
        tb.setTaskId(task.getId());
        tb.setBusinessKey(pi.getBusinessKey());
        List<String> outcomes=this.getOutComes(task,pi);
        tb.setComeouts(outcomes);
        return tb;
    }

    private List<String> getOutComes(Task task, ProcessInstance pi) {
        List<String> outcomes=new ArrayList<String>();
        ProcessDefinitionEntity pde= (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(task.getProcessDefinitionId());
        ActivityImpl ai=pde.findActivity(pi.getActivityId());
        List<PvmTransition> pvmList=ai.getOutgoingTransitions();
        for(PvmTransition pvm:pvmList){
            String name= (String) pvm.getProperty("name");
            outcomes.add(name);
        }
        return outcomes;
    }
}
