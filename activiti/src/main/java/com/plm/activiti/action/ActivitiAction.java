package com.plm.activiti.action;

import com.plm.activiti.constants.ProcessState;
import com.plm.activiti.model.BatchModel;
import com.plm.activiti.service.ActiviService;
import com.plm.activiti.util.ActivitiUtil;
import com.plm.activiti.util.FileUtil;
import com.plm.activiti.util.Util;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bpmn")
public class ActivitiAction {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    @Qualifier("activiService")
    private ActiviService activiService;

    @ResponseBody
    @RequestMapping("/createBpmn")
    public Map createBpmn() {
        Map<Object, Object> dataMap = new HashMap<>();
        BpmnModel bpmnModel = ActivitiUtil.createReturnModel("returnProcess", null);
        ActivitiUtil.check(bpmnModel);

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        try {
            FileUtil.saveFile("createReturnModel.xml", convertToXML);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bytes = new String(convertToXML);
        System.out.println(bytes);

        dataMap.put("bytes", bytes);
        return dataMap;
    }


    @ResponseBody
    @RequestMapping("/deployProcess")
    public Map deployProcess() {
        Map<Object, Object> dataMap = new HashMap<>();
        DeploymentBuilder builder = repositoryService.createDeployment();
        Deployment testDeploymentBuilder = builder.addBpmnModel("testBpmnModel.bpmn", ActivitiUtil.createReturnModel("returnProcess", null)).name("testDeploymentBuilder").deploy();
        String id = testDeploymentBuilder.getId();
        System.out.println(id);
        dataMap.put("Deployment_Id", id);
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/getLastProcess")
    public Map getLastProcess() {
        Map<Object, Object> dataMap = new HashMap<>();
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("return").latestVersion().singleResult();
        System.out.println(pd.getId());
        dataMap.put("ProcessDefinition_Id", pd.getId());
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/startActiviti")
    public Map startActiviti() {
        Map<Object, Object> dataMap = new HashMap<>();
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("return").latestVersion().singleResult();
        // 启动流程之前绑定流程启动人
        identityService.setAuthenticatedUserId("huahua");
        Map<String, Object> map = new HashMap<>();
        String busKey = Util.getBusKey();
        map.put("buskey", busKey);
        map.put("processState", ProcessState.I);
        activiService.addBatch(map);
        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), busKey, map);
        dataMap.put("ProcessInstance_Id", pi.getId());
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/queryBatch")
    public Map queryBatch() {
        Map<Object, Object> dataMap = new HashMap<>();
        List<Map> list = activiService.queryBatch();
        dataMap.put("list", list);
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/queryAllBatch")
    public Map queryAllBatch() {
        Map<Object, Object> dataMap = new HashMap<>();
        List<Map> list = activiService.queryAllBatch();
        dataMap.put("list", list);
        return dataMap;
    }
    //20210423154647

    @ResponseBody
    @RequestMapping("/processTask")
    public Map processTask(@RequestBody Map data) {
        Map<Object, Object> dataMap = new HashMap<>();

        Integer approveState = data.get("approveState") != null ? Integer.valueOf(data.get("approveState").toString()) : -1;
        String approveReason = data.get("approveReason") != null ? String.valueOf(data.get("approveReason")) : "";

        Integer batchId = data.get("batchId") != null ? Integer.valueOf(data.get("batchId").toString()) : 0;
        BatchModel batchModel = activiService.queryBatchDetail(batchId);
        String busKey = batchModel.getBusKey();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(busKey).singleResult();
        List<org.activiti.engine.task.Task> list = taskService.createTaskQuery().list();
//        org.activiti.engine.task.Task actTask = null;
//        for (org.activiti.engine.task.Task task1 : list) {
//            if (task1.getProcessInstanceId().equals(processInstance.getProcessInstanceId())) {
//                actTask = task1;
//                break;
//            }
//        }
        org.activiti.engine.task.Task actTask = taskService.createTaskQuery().processInstanceBusinessKey(busKey).singleResult();

        //Map val = new HashMap<>();
        //val.put("users","a,b,c");
        taskService.complete(actTask.getId(), null);

        //当节点结束后，获取下一个节点接受人任务列表，判断任务列表中是否存在该流程实例的任务
        org.activiti.engine.task.Task nextTask = taskService.createTaskQuery().processInstanceBusinessKey(busKey).singleResult();
        if (nextTask != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("batchId", batchId);
            map.put("processState", ProcessState.ING);
            //ING updateBatch
            activiService.updateBatch(map);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("batchId", batchId);
            map.put("processState", ProcessState.OK);
            activiService.updateBatch(map);
        }
        dataMap.put("id", processInstance.getId());
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/queryBatchDetail")
    public Map queryBatchDetail(@RequestBody Map data) {
        if (data.get("batchId") == null) {
            return new HashMap();
        }
        Integer batchId = data.get("batchId") != null ? Integer.valueOf(data.get("batchId").toString()) : 0;
        Map<Object, Object> dataMap = new HashMap<>();
        BatchModel batchModel = activiService.queryBatchDetail(batchId);
        dataMap.put("batchModel", batchModel);
        return dataMap;
    }

    @ResponseBody
    @RequestMapping("/queryHist")
    public Map queryHist(@RequestBody Map data) {
        Integer batchId = data.get("batchId") != null ? Integer.valueOf(data.get("batchId").toString()) : 0;
        Map<Object, Object> dataMap = new HashMap<>();
        BatchModel batchModel = activiService.queryBatchDetail(batchId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(batchModel.getBusKey()).singleResult();
        List<HistoricActivityInstance> list = historyService // 历史任务Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId(processInstance.getId())// 指定流程实例id
                .finished() // 查询已经完成的任务
                .list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (HistoricActivityInstance hai : list) {
            System.out.println("任务ID:" + hai.getId());
            System.out.println("流程实例ID:" + hai.getProcessInstanceId());
            System.out.println("活动名称：" + hai.getActivityName());
            System.out.println("办理人：" + hai.getAssignee());
            System.out.println("开始时间：" + sdf.format(hai.getStartTime()));
            System.out.println("结束时间：" + sdf.format(hai.getEndTime()));
            System.out.println("===========================");
        }
        return null;
    }
}
