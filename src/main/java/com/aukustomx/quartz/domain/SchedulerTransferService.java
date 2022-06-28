package com.aukustomx.quartz.domain;

import org.quartz.JobDetail;

public interface SchedulerTransferService {

    Object scheduleTransfer(ScheduledTransferRequest request);

    void executeScheduledTransfer(JobDetail jobDetail);
}
