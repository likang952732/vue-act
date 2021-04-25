package com.plm.activiti.service;

import org.activiti.bpmn.model.BpmnModel;

import java.util.List;
import java.util.Map;

public interface ActiviService {

    void addBatch(Map map);

    List<Map> queryBatch();

}
