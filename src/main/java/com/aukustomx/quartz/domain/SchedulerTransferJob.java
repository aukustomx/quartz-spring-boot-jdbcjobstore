package com.aukustomx.quartz.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTransferJob implements Job {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private SchedulerTransferService schedulerTransferService;

    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {

        schedulerTransferService.executeScheduledTransfer(jobContext.getJobDetail());
    }
}
