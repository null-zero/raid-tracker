package com.raidtracker.ToAPointsTracker.util;

import lombok.Value;

@Value
public class RaidState
{

	private final boolean inLobby;
	private final boolean inRaid;
	private final RaidRoom currentRoom;
	private final int playerCount;

}
