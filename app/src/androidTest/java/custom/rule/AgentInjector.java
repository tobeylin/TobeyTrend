package custom.rule;

import android.app.Activity;
import android.content.Context;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.main.agent.BaseAgent;
import com.trend.tobeylin.tobeytrend.main.view.AgentView;
import com.trend.tobeylin.tobeytrend.main.view.HomeView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;

/**
 * Created by tobeylin on 15/7/27.
 */
public class AgentInjector<T extends BaseAgent> implements ActivityLifecycleCallback {

    public static final String TAG = AgentInjector.class.getSimpleName();
    private BaseAgent decoratedAgent;
    private Class<T> decoratedAgentClass;
    private CountingIdlingResource agentCounting;

    public AgentInjector(Class<T> decoratedAgentClass) {
        this.decoratedAgentClass = decoratedAgentClass;
    }

    @Override
    public void onActivityLifecycleChanged(Activity activity, Stage stage) {
        switch (stage) {
            case PRE_ON_CREATE:
                Log.i(TAG, "PRE_ON_CREATE");
                beforeOnCreate((AgentView) activity);
                break;
            default:
                break;
        }
    }

    public void beforeOnCreate(AgentView activity) {
        agentCounting = new CountingIdlingResource(activity + TAG);
        agentCounting.increment();

        try {
            //TODO: the dependencies of HomeView should be remove.
            //TODO: the constructor of decorated agent should be unified, e.x.: DecoratedAgent(Context, AgentView, CountingIdlingResource)
            Constructor<T> constructor = decoratedAgentClass.getConstructor(Context.class, HomeView.class, CountingIdlingResource.class);
            decoratedAgent = constructor.newInstance(activity, activity, agentCounting);
            activity.setAgent(decoratedAgent);
            registerIdlingResources(agentCounting);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        Log.i(TAG, "destroy");
        unregisterIdlingResources(agentCounting);
    }
}
