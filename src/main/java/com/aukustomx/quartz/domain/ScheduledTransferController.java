package com.aukustomx.quartz.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("scheduled-transfers")
public class ScheduledTransferController {

    private final SchedulerTransferService schedulerTransferService;

    @Autowired
    public ScheduledTransferController(SchedulerTransferService schedulerTransferService) {
        this.schedulerTransferService = schedulerTransferService;
    }

    @PostMapping
    public ResponseEntity<Object> scheduleTransfer(@Valid @RequestBody ScheduledTransferRequest transferRequest) {

        var responseBody = schedulerTransferService.scheduleTransfer(transferRequest);
        return ResponseEntity.ok(responseBody);
    }
}
