package server.plugin.sample.job;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.scheduler.JobRunner;
import com.atlassian.scheduler.JobRunnerRequest;
import com.atlassian.scheduler.JobRunnerResponse;
import com.atlassian.scheduler.SchedulerService;
import com.atlassian.scheduler.SchedulerServiceException;
import com.atlassian.scheduler.config.JobConfig;
import com.atlassian.scheduler.config.JobId;
import com.atlassian.scheduler.config.JobRunnerKey;
import com.atlassian.scheduler.config.RunMode;
import com.atlassian.scheduler.config.Schedule;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import server.plugin.sample.api.JTricksJob;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

 @ExportAsService({ LifecycleAware.class })
//@Named("jtricksJob")
public class JTricksJobImpl implements JTricksJob, LifecycleAware {

	private static final long EVERY_MINUTE = TimeUnit.MINUTES.toMillis(1);
	private static final JobRunnerKey JOB_RUNNER_KEY = JobRunnerKey.of(JTricksJobImpl.class.getName());
	private static final JobId JOB_ID = JobId.of(JTricksJobImpl.class.getName());
	Logger LOG = LoggerFactory.getLogger(JTricksJobImpl.class);
	@ComponentImport
	private final SchedulerService scheduler;

	@Inject
	public JTricksJobImpl(@ComponentImport SchedulerService scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public JobRunnerResponse runJob(final JobRunnerRequest request) {
		LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Running JTricksJob at " + request.getStartTime());
		return JobRunnerResponse.success();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
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
	public void destroy() throws Exception {
		System.out.println("Stopping...");
		scheduler.unscheduleJob(JOB_ID);
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
