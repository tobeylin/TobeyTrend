package custom.rule;

import android.app.Activity;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.trend.tobeylin.tobeytrend.main.agent.BaseAgent;

import java.lang.reflect.Constructor;

/**
 * Created by tobeylin on 15/7/27.
 */
public class BaseTestRule<T extends Activity, S extends BaseAgent> extends IntentsTestRule<T> {

    private AgentInjector<S> agentInjector = null;
    private Class<S> decoratedAgentClass;

    public BaseTestRule(Class<T> activityClass, Class<S> decoratedAgentClass) {
        super(activityClass);
        this.decoratedAgentClass = decoratedAgentClass;
    }

    public BaseTestRule(Class<T> activityClass, Class<S> decoratedAgentClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
        this.decoratedAgentClass = decoratedAgentClass;
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        agentInjector = new AgentInjector(decoratedAgentClass);
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(agentInjector);

    }

    @Override
    protected void afterActivityFinished() {
        agentInjector.destroy();
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(agentInjector);
        super.afterActivityFinished();
    }

}
