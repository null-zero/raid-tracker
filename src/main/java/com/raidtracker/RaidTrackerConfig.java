package com.raidtracker;

import com.raidtracker.ToAPointsTracker.features.timetracking.SplitsMode;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("raidtracker")
public interface RaidTrackerConfig extends Config
{
	@ConfigItem(
		keyName = "defaultFFA",
		name = "default FFA",
		description = "Sets the default split to free for all, rather than split"
	)
	default boolean defaultFFA()
	{
		return false;
	}

	@ConfigItem(
			keyName = "FFACutoff",
			name = "FFA cut off",
			description = "The value of which, when the split reaches under that value, is considered free for all"
	)

	default int FFACutoff() {
		return 1000000;
	}

	@ConfigItem(
			keyName = "lastXKills",
			name = "Last X Kills",
			description = "When the 'Last X Kills' option is selected, this value is used as X"
	)

	default int lastXKills() {return 50;}

	@ConfigItem(
			keyName = "showTitle",
			name = "Show Title",
			description = "Disable this checkmark to hide the title in the ui"
	)
	default boolean showTitle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "dey0Tracker",
		name = "Log raid room times (dey0)",
		description = "Track raid room times with dey0's raid timers plugin. This will replace the regular time splits panel with more detailed times of each part of the raid"
	)
	default boolean dey0Tracker() { return false; }


	@ConfigItem(
			keyName = "showKillsLogged",
			name = "Show Kills Logged",
			description = "Disable this checkmark to hide the Kills Logged panel in the ui"
	)
	default boolean showKillsLogged()
	{
		return true;
	}


	@ConfigItem(
			keyName = "showFilters",
			name = "Show Filters",
			description = "Disable this checkmark to hide the Filter Panel in the ui"
	)
	default boolean showFilters()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showUniquesTable",
			name = "Show Uniques Table",
			description = "Disable this checkmark to hide the Uniques Table in the ui"
	)
	default boolean showUniquesTable()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showPoints",
			name = "Show Points",
			description = "Disable this checkmark to hide the Points Panel in the ui"
	)
	default boolean showPoints()
	{
		return true;
	}
	@ConfigItem(
		keyName = "showPointsDryReceived",
		name = "Show Points Dry - Received",
		description = "Disable this checkmark to hide the Points Dry Received Panel in the ui"
	)
	default boolean showPointsDryReceived()
	{
		return true;
	}
	@ConfigItem(
		keyName = "showPointsDrySeen",
		name = "Show Points Dry - Seen",
		description = "Disable this checkmark to hide the Points Dry Seen Panel in the ui"
	)

	default boolean showPointsDrySeen()
	{
		return false;
	}

	@ConfigItem(
			keyName = "showTimeSplits",
			name = "Show Time Splits",
			description = "Disable this checkmark to hide the Time Splits Panel in the ui"
	)
	default boolean showTimeSplits()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showSplitGPEarned",
			name = "Show Split GP Earned",
			description = "Disable this checkmark to hide the Split GP Earned Panel in the ui"
	)
	default boolean showSplitGPEarned()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showMVPs",
			name = "Show MVP's",
			description = "Disable this checkmark to hide the MVP's Panel in the ui"
	)
	default boolean showMVPs()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showRegularDrops",
			name = "Show Regular Drops",
			description = "Disable this checkmark to hide the Regular Drops Panel in the ui"
	)
	default boolean showRegularDrops()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showSplitChanger",
			name = "Show Split Changer",
			description = "Disable this checkmark to hide the Split Changer in the ui"
	)
	default boolean showSplitChanger()
	{
		return true;
	}

	String CONFIG_GROUP = "RaidsDryMeterTest";

	@ConfigSection(
		name = "Points Tracker",
		description = "<html>Tracks points for the raid, used in calculating drop chance." +
			"<br/>NOTE: For teams, you MUST use the RuneLite Party plugin to receive team drop chance.</html>",
		position = 6,
		closedByDefault = true
	)
	String SECTION_POINTS_TRACKER = "sectionPointsTracker";

	@ConfigSection(
		name = "Time Tracking",
		description = "Time tracking and splits.",
		position = 9,
		closedByDefault = true
	)
	String SECTION_TIME_TRACKING = "sectionTimeTracking";

	// Points Tracker

	@ConfigItem(
		keyName = "pointsTrackerOverlayEnable",
		name = "Enable Overlay",
		description = "Show points earned within the raid.",
		position = 0,
		section = SECTION_POINTS_TRACKER
	)
	default boolean pointsTrackerOverlayEnable()
	{
		return true;
	}

	@ConfigItem(
		keyName = "pointsTrackerShowRoomPoints",
		name = "Separate Room Points",
		description = "Show points for the current room separate from total points.",
		position = 1,
		section = SECTION_POINTS_TRACKER
	)
	default boolean pointsTrackerShowRoomPoints()
	{
		return true;
	}

	@ConfigItem(
		keyName = "pointsTrackerShowUniqueChance",
		name = "Show Unique %",
		description = "Show unique chance on the overlay.",
		position = 2,
		section = SECTION_POINTS_TRACKER
	)
	default boolean pointsTrackerShowUniqueChance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "pointsTrackerShowPetChance",
		name = "Show Pet %",
		description = "Show pet chance on the overlay.",
		position = 3,
		section = SECTION_POINTS_TRACKER
	)
	default boolean pointsTrackerShowPetChance()
	{
		return true;
	}

	@ConfigItem(
		keyName = "pointsTrackerPostRaidMessage",
		name = "Points Total Message",
		description = "Show the total points in chat after the raid, akin to the Chambers of Xeric.",
		position = 4,
		section = SECTION_POINTS_TRACKER
	)
	default boolean pointsTrackerPostRaidMessage()
	{
		return true;
	}

	// Time Tracking

	@ConfigItem(
		keyName = "targetTimeDisplay",
		name = "Target Time in Timer",
		description = "Expand the in-raid timer to also show the target time to beat.",
		position = 0,
		section = SECTION_TIME_TRACKING
	)
	default boolean targetTimeDisplay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "splitsMessage",
		name = "Splits Post-Raid Message",
		description = "Show room splits in a chat message at the end of the raid. Path shows boss completion times, room shows each individual room (can be very long).",
		position = 1,
		section = SECTION_TIME_TRACKING
	)
	default SplitsMode splitsMessage()
	{
		return SplitsMode.OFF;
	}

	@ConfigItem(
		keyName = "splitsOverlay",
		name = "Splits Overlay",
		description = "Show room splits in an on-screen overlay. Path shows boss completion times, room shows each individual room (can be very long).",
		position = 2,
		section = SECTION_TIME_TRACKING
	)
	default SplitsMode splitsOverlay()
	{
		return SplitsMode.OFF;
	}
}
