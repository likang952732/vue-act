package com.plm.activiti.mapper;

import com.plm.activiti.model.BatchModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActivitiMapper {

    public void addBatch(Map dataMap);

    public List<Map> queryBatch();

    public List<Map> queryAllBatch();

    public BatchModel queryBatchDetail(Integer batchId);

    public void updateBatch(Map dataMap);
}
