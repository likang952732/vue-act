package com.plm.activiti.util;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class ActivitiUtil {

    /**
     * @ Author     ：pengliming@csii.com.cn
     * @ Date       ：2021/4/1 10:09
     * @ Description：验证bpmnModel 是否是正确的bpmn xml文件
     */
    public static Boolean check(BpmnModel bpmnModel) {
        Boolean result = false;
        try {
            ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
            ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
            //验证失败信息的封装ValidationError
            List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
            if (validate != null && validate.size() == 0){
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static BpmnModel createReturnModel(String processDefinitionId, Map authMap) {

        Process process = new Process();
        //实例化BpmnModel对象
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);
        process.setId(processDefinitionId);

        process.addFlowElement(createStartEvent("start", "start"));
//        process.addFlowElement(createExclusiveGateway("startGateWay", "startGateWay"));
        process.addFlowElement(createSequenceFlow("startToTask1", "startToTask1", "start", "task1", null));
        process.addFlowElement(createUserTask("task1", "task1"));
        process.addFlowElement(createSequenceFlow("task1ToTask2", "task1ToTask2", "task1", "task2", null));
        process.addFlowElement(createUserTask("task2", "task2"));
        process.addFlowElement(createSequenceFlow("task2ToTask3", "task2ToTask3", "task2", "task3", null));
        process.addFlowElement(createUserTask("task3", "task3"));
        process.addFlowElement(createSequenceFlow("task3ToEnd", "task3ToEnd", "task3", "end", null));
        process.addFlowElement(createEndEvent("end", "end"));
        return bpmnModel;

    }

    // 创建起始点
    public static StartEvent createStartEvent(String Id, String name) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId(Id);
        return startEvent;
    }

    // 创建结束点
    protected static EndEvent createEndEvent(String Id, String name) {
        EndEvent endEvent = new EndEvent();
        endEvent.setId(Id);
        return endEvent;
    }

    public static UserTask createUserTask(String id, String name) {
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        return userTask;
    }

    public static SequenceFlow createSequenceFlow(String id, String name, String from, String to, String conditionExpression) {
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(id);
        sequenceFlow.setName(name);
        sequenceFlow.setSourceRef(from);
        sequenceFlow.setTargetRef(to);
        if (conditionExpression != null) {
            sequenceFlow.setConditionExpression(conditionExpression);
        }
        return sequenceFlow;
    }

    // 创建排他网关
    public static ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway gateWay = new ExclusiveGateway();
        gateWay.setId(id);
        gateWay.setName(name);
        gateWay.setExclusive(true);
        return gateWay;
    }
}
