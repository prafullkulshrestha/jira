package com.atlassian.plugin.spring.scanner.test.job;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.scheduler.*;
import com.atlassian.scheduler.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.sal.api.lifecycle.LifecycleAware;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@ExportAsService({ LifecycleAware.class })
@Named
public class MyJobRunner implements JobRunner, LifecycleAware {
    Logger LOG = LoggerFactory.getLogger(MyJobRunner.class);
    private static final long EVERY_MINUTE = TimeUnit.MINUTES.toMillis(1);
    private static final JobRunnerKey JOB_RUNNER_KEY = JobRunnerKey.of(MyJobRunner.class.getName());
    private static final JobId JOB_ID = JobId.of(MyJobRunner.class.getName());
    private final SchedulerService scheduler;

    public MyJobRunner( @ComponentImport SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    @Nullable
    @Override
    public JobRunnerResponse runJob(JobRunnerRequest jobRunnerRequest) {
        LOG.info("Running MyJob at *************************************** " + jobRunnerRequest.getStartTime());
        return JobRunnerResponse.success();
    }

    @Override
    public void onStart() {
        LOG.info("Starting...");
        scheduler.registerJobRunner(JOB_RUNNER_KEY, this);

        final JobConfig jobConfig = JobConfig.forJobRunnerKey(JOB_RUNNER_KEY).withRunMode(RunMode.RUN_LOCALLY)
                .withSchedule(Schedule.forInterval(EVERY_MINUTE, null));

        try {
            scheduler.scheduleJob(JOB_ID, jobConfig);
        } catch (SchedulerServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        System.out.println("Stopping...");
        scheduler.unscheduleJob(JOB_ID);
    }
}
