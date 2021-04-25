//package com.plm.activiti.config.activiti;
//
//import org.activiti.engine.*;
//import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class ActivitiConfig {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    DataSourceTransactionManager transactionManager;
//
//    @Bean
//    public ProcessEngineConfiguration processEngineConfiguration() {
//        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
//        // 连接数据库的配置
//        processEngineConfiguration.setDataSource(dataSource);
//        processEngineConfiguration.setAsyncExecutorActivate(true);
//        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        return processEngineConfiguration;
//    }
//
//    /* @Bean
//     public ProcessEngine processEngine() {
//         return processEngineConfiguration().buildProcessEngine();
//     }
//     */
//    @Bean
//    public RepositoryService repositoryService() {
//        return processEngineConfiguration().buildProcessEngine().getRepositoryService();
//    }
//
//    @Bean
//    public RuntimeService runtimeService() {
//        return processEngineConfiguration().buildProcessEngine().getRuntimeService();
//    }
//
//    @Bean
//    public TaskService taskService() {
//        return processEngineConfiguration().buildProcessEngine().getTaskService();
//    }
//
//    @Bean
//    public FormService formService() {
//        return processEngineConfiguration().buildProcessEngine().getFormService();
//    }
//
//    @Bean
//    public HistoryService historyService() {
//        return processEngineConfiguration().buildProcessEngine().getHistoryService();
//    }
//
//    @Bean
//    public ManagementService managementService() {
//        return processEngineConfiguration().buildProcessEngine().getManagementService();
//    }
//
//    @Bean
//    public IdentityService identityService() {
//        return processEngineConfiguration().buildProcessEngine().getIdentityService();
//    }
//}
