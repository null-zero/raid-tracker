package com.raidtracker.ToAPointsTracker.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.raidtracker.ToAPointsTracker.features.pointstracker.PartyPointsTracker;
import com.raidtracker.ToAPointsTracker.features.pointstracker.PointsTracker;
import com.raidtracker.ToAPointsTracker.util.RaidStateTracker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToAPointsTrackerModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		Multibinder<PluginLifecycleComponent> lifecycleComponents = Multibinder.newSetBinder(binder(), PluginLifecycleComponent.class);
		lifecycleComponents.addBinding().to(PartyPointsTracker.class);
		lifecycleComponents.addBinding().to(PointsTracker.class);
		lifecycleComponents.addBinding().to(RaidStateTracker.class);
	}

}
