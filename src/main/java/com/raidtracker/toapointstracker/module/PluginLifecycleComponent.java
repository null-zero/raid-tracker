package com.raidtracker.toapointstracker.module;

import com.raidtracker.RaidTrackerConfig;
import com.raidtracker.toapointstracker.util.RaidState;

public interface PluginLifecycleComponent
{

	default boolean isEnabled(RaidTrackerConfig config, RaidState raidState)
	{
		return true;
	}

	void startUp();

	void shutDown();

}
