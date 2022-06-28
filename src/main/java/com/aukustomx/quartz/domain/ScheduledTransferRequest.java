package com.aukustomx.quartz.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;

public class ScheduledTransferRequest {

    private String sourceAccount;
    private String targetAccount;
    private String reason;
    private BigDecimal amount;
    private ZonedDateTime plannedFireTime;

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public String getReason() {
        return reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ZonedDateTime getPlannedFireTime() {
        return plannedFireTime;
    }

    public Instant plannedFireTimeAsInstant() {
        return plannedFireTime.toInstant();
    }

    @Override
    public String toString() {
        return "ScheduledTransferRequest{" +
                "sourceAccount='" + sourceAccount + '\'' +
                ", targetAccount='" + targetAccount + '\'' +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                ", plannedExecution=" + plannedFireTime +
                '}';
    }
}
