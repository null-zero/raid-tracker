package com.raidtracker.ToAPointsTracker.module;

import com.raidtracker.RaidTrackerConfig;
import com.raidtracker.ToAPointsTracker.util.RaidState;

public interface PluginLifecycleComponent
{

	default boolean isEnabled(RaidTrackerConfig config, RaidState raidState)
	{
		return true;
	}

	void startUp();

	void shutDown();

}
