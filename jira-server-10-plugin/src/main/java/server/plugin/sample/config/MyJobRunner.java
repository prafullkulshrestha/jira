package server.plugin.sample.config;

import com.atlassian.scheduler.JobRunner;
import com.atlassian.scheduler.JobRunnerRequest;
import com.atlassian.scheduler.JobRunnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plugin.sample.job.JTricksJobImpl;

import javax.annotation.Nullable;
import javax.inject.Named;

@Named
public class MyJobRunner implements JobRunner {
    Logger LOG = LoggerFactory.getLogger(MyJobRunner.class);
    @Nullable
    @Override
    public JobRunnerResponse runJob(JobRunnerRequest jobRunnerRequest) {
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Running JTricksJob at " + jobRunnerRequest.getStartTime());
        return JobRunnerResponse.success();
    }
}
