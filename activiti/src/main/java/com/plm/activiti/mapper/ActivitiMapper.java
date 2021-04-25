package com.plm.activiti.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActivitiMapper {

    public void addBatch(Map dataMap);

    public List<Map> queryBatch();
}
