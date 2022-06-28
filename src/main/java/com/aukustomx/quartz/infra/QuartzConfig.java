package com.aukustomx.quartz.infra;

import com.aukustomx.quartz.domain.SchedulerTransferJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceScriptDatabaseInitializer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@EnableAutoConfiguration
public class QuartzConfig {

    private static final Logger logger = LogManager.getLogger();

    @PostConstruct
    public void init() {
        logger.info("Initializing Quartz...");
    }

//    @Bean
//    @QuartzDataSource
//    public DataSource quartzDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {

        var schedulerFactoryBean = new SchedulerFactoryBean();
        var jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        try {
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
        } catch (IOException e) {
            logger.error(e);
        }
        //schedulerFactoryBean.setDataSource(quartzDataSource());
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");

        return schedulerFactoryBean;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

//    @Bean
//    public JobDetail jobDetail() {
//        return newJob()
//                .ofType(SchedulerTransferJob.class)
//                .storeDurably()
//                .withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
//                .withDescription("Invoke Sample Job service...")
//                .build();
//    }

//    @Bean
//    public Trigger trigger(JobDetail job) {
//
//        int frequencyInSec = 60;
//        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
//
//        return newTrigger()
//                .forJob(job)
//                .withIdentity(TriggerKey.triggerKey("Qrtz_Trigger"))
//                .withDescription("Scheduled transfer trigger")
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(frequencyInSec)
//                        .repeatForever())
//                .build();
//    }
}
