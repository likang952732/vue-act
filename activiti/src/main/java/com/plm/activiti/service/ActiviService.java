package com.plm.activiti.service;

import com.plm.activiti.model.BatchModel;
import org.activiti.bpmn.model.BpmnModel;

import java.util.List;
import java.util.Map;

public interface ActiviService {

    void addBatch(Map map);

    List<Map> queryBatch();

    List<Map> queryAllBatch();

    BatchModel queryBatchDetail(Integer batchId);

    public void updateBatch(Map map);
}
