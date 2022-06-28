package com.aukustomx.quartz.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SchedulerTransferServiceImpl implements SchedulerTransferService {

    private static final Logger logger = LogManager.getLogger();
    private static final String SCHEDULED_TRANSFERS_GROUP = "scheduled-transfers";

    private final Scheduler scheduledTransfersScheduler;

    @Autowired
    public SchedulerTransferServiceImpl(Scheduler scheduledTransfersScheduler) {
        this.scheduledTransfersScheduler = scheduledTransfersScheduler;
    }

    @Override
    public Object scheduleTransfer(ScheduledTransferRequest request) {

        var dataMap = new JobDataMap();
        dataMap.put("sourceAccount", request.getSourceAccount());
        dataMap.put("targetAccount", request.getTargetAccount());
        dataMap.put("reason", request.getReason());
        dataMap.put("amount", request.getAmount().toString());

        var scheduledTransferId = UUID.randomUUID().toString();
        var plannedFireTime = request.getPlannedFireTime().toInstant();

        var scheduledTransferJobDetail = JobBuilder.newJob(SchedulerTransferJob.class)
                .withIdentity(scheduledTransferId, SCHEDULED_TRANSFERS_GROUP)
                .withDescription("Scheduled Transfer Job")
                .usingJobData(dataMap)
                .storeDurably()
                .build();

        var scheduledTransferTrigger = TriggerBuilder.newTrigger()
                .forJob(scheduledTransferJobDetail)
                .withIdentity(scheduledTransferJobDetail.getKey().getName(), SCHEDULED_TRANSFERS_GROUP)
                .withDescription("Scheduled Transfer Trigger")
                .startAt(Date.from(plannedFireTime))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

        try {
            scheduledTransfersScheduler.scheduleJob(scheduledTransferJobDetail, scheduledTransferTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("La transferencia no pudo ser programada ");
        }

        return "La transferencia " + scheduledTransferId + " queda programada exitosamente para " + scheduledTransferTrigger.getFinalFireTime().toString();
    }

    @Override
    public void executeScheduledTransfer(JobDetail jobDetail) {
        System.out.println("Executing job " + jobDetail.getDescription());
        logger.info("Executing job {}", jobDetail.getDescription());
    }
}
