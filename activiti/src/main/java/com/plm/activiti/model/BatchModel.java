package com.plm.activiti.model;

import com.plm.activiti.constants.ProcessState;

import java.sql.Timestamp;

public class BatchModel {

    private Long batchId;
    private ProcessState processState;
    private String busKey;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    public String getBusKey() {
        return busKey;
    }

    public void setBusKey(String busKey) {
        this.busKey = busKey;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
