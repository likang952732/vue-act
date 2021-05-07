package com.plm.activiti.service.impl;

import com.plm.activiti.mapper.ActivitiMapper;
import com.plm.activiti.model.BatchModel;
import com.plm.activiti.service.ActiviService;
import org.activiti.bpmn.model.BpmnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activiService")
public class ActiviServiceImpl implements ActiviService {

    @Autowired
    private ActivitiMapper activitiMapper;

    @Override
    public void addBatch(Map map) {
        activitiMapper.addBatch(map);
    }


    @Override
    public List<Map> queryBatch() {
        return activitiMapper.queryBatch();
    }

    @Override
    public List<Map> queryAllBatch() {
        return activitiMapper.queryAllBatch();
    }

    @Override
    public BatchModel queryBatchDetail(Integer batchId) {
        return activitiMapper.queryBatchDetail(batchId);
    }

    @Override
    public void updateBatch(Map map) {
        activitiMapper.updateBatch(map);
    }

}
