package com.raidtracker.ToAPointsTracker.features.pointstracker;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.party.messages.PartyMemberMessage;

@RequiredArgsConstructor
@Getter
public class RaidTrackerPointsMessage extends PartyMemberMessage
{

	@SerializedName("p")
	private final int points;

}
