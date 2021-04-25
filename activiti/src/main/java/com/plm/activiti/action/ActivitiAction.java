package com.plm.activiti.action;

import com.plm.activiti.service.ActiviService;
import com.plm.activiti.util.ActivitiUtil;
import com.plm.activiti.util.FileUtil;
import com.plm.activiti.util.Util;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
    //20210423154647

    @ResponseBody
    @RequestMapping("/processTask")
    public Map processTask(@RequestBody Map data) {
        Map<Object, Object> dataMap = new HashMap<>();
        String busKey = data.get("busKey") != null ? String.valueOf(data.get("busKey")) : "";
        Integer approveState = data.get("approveState") != null ? Integer.valueOf(data.get("approveState").toString()) : -1;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(busKey).singleResult();
        List<org.activiti.engine.task.Task> list = taskService.createTaskQuery().list();
        org.activiti.engine.task.Task actTask=null;
        for(org.activiti.engine.task.Task task1 :list){
            if(task1.getProcessInstanceId().equals(processInstance.getProcessInstanceId())){
                actTask =task1;
                break;
            }
        }
        dataMap.put("id", processInstance.getId());
        return dataMap;
    }

}
