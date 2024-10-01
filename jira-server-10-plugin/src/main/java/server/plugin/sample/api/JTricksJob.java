package server.plugin.sample.api;

import com.atlassian.scheduler.JobRunner;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public interface JTricksJob extends JobRunner, InitializingBean, DisposableBean {
}
