package com.trend.tobeylin.tobeytrend.main.view;

import com.trend.tobeylin.tobeytrend.main.agent.BaseAgent;

/**
 * Created by tobeylin on 15/7/29.
 */
public interface AgentView<T extends BaseAgent> {

    void setAgent(T agent);
    T getAgent();

}
