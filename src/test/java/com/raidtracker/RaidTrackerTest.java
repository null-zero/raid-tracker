package com.raidtracker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.raidtracker.filereadwriter.FileReadWriter;
import com.raidtracker.ui.RaidTrackerPanel;
import junit.framework.TestCase;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.game.ItemManager;
import net.runelite.http.api.item.ItemPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RaidTrackerTest extends TestCase
{


	@Test
	public void TestLootSplits() {
		//TODO: double purples
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);

		List<ItemPrice> kodaiTestList = new ArrayList<>();

		ItemPrice kodaiTest = new ItemPrice();

		kodaiTest.setId(0);
		kodaiTest.setName("Kodai Insignia");
		kodaiTest.setPrice(50505050);

		kodaiTestList.add(kodaiTest);

		Player player = mock(Player.class);

		RaidTrackerPanel panel = mock(RaidTrackerPanel.class);
		raidTrackerPlugin.setPanel(panel);

		FileReadWriter fw = mock(FileReadWriter.class);
		raidTrackerPlugin.setFw(fw);

		when(itemManager.search(anyString())).thenReturn(kodaiTestList);
		when(client.getLocalPlayer()).thenReturn(player);
		when(player.getName()).thenReturn("Canvasba");
		when(raidTrackerConfig.FFACutoff()).thenReturn(1000000);

		raidTracker.setTeamSize(3);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "K1NG DK - Kodai insignia", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);


		raidTrackerPlugin.setSplits(raidTracker);

		assertTrue(raidTracker.getLootSplitReceived() > -1);
		assertEquals(-1, raidTracker.lootSplitPaid);


		raidTracker.setSpecialLootReceiver("Canvasba");
		raidTracker.setSpecialLootInOwnName(true);
		raidTrackerPlugin.setSplits(raidTracker);

		assertTrue(raidTracker.getLootSplitReceived() > -1);
		assertTrue(raidTracker.getLootSplitPaid() > -1);

		assertFalse(raidTracker.isFreeForAll());

		//check ffa for below 1m split
		raidTracker.setSpecialLootValue(2000000);
		raidTracker.setLootSplitPaid(-1);
		raidTrackerPlugin.setSplits(raidTracker);

		assertTrue(raidTracker.isFreeForAll());
		assertEquals(raidTracker.getLootSplitReceived(), 2000000);
		assertEquals(-1, raidTracker.getLootSplitPaid());

		raidTracker.setSpecialLootReceiver("K1NG DK");
		raidTracker.setSpecialLootInOwnName(false);

		raidTracker.setLootSplitReceived(-1);
		raidTrackerPlugin.setSplits(raidTracker);

		assertEquals(-1 , raidTracker.getLootSplitReceived());
	}

	@Mock
	@Bind
	private Client client;

	@Mock
	@Bind
	private ItemManager itemManager;

	@Mock
	@Bind
	private RaidTrackerConfig raidTrackerConfig;


	@Inject
	private RaidTrackerPlugin raidTrackerPlugin;


	@Before
	public void setUp()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	//---------------------------------- onChatMessage tests ------------------------------------------------
	@Test
	public void TestRaidComplete()
	{
		RaidTracker raidTracker = new RaidTracker();

		when(client.getVarbitValue(anyInt())).thenReturn(5); //random integer, I chose 5
		raidTracker.setInRaidChambers(true);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "Congratulations - your raid is complete! Team size: 15 Players Duration: 50:26 Personal best: 31:12", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertTrue(raidTracker.isRaidComplete());
	}

	@Test
	public void TestDuration()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "Congratulations - your raid is complete! Team size: Solo Duration: 1:40:26 Personal best: 31:12", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(6026, raidTracker.getRaidTime());

		message.setMessage("Congratulations - your raid is complete! Team size: 11-15 Players Duration: 50:26.6 Personal best: 31:12");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(3027, raidTracker.getRaidTime());

		//dey0 case
		message.setMessage("Middle level complete! Duration: 7:53 Total: 20:50");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(1250, raidTracker.getMiddleTime());

		//regular case
		message.setMessage("Middle level complete! Duration: 20:50");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(1250, raidTracker.getMiddleTime());

		message.setMessage("Combat room 'Vanguards' complete! Duration: 3:19 Total: 16:16");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(199, raidTracker.getVanguardsTime());


	}

	@Test
	public void TestPurple()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);

		List<ItemPrice> kodaiTestList = new ArrayList<>();

		ItemPrice kodaiTest = new ItemPrice();

		kodaiTest.setId(0);
		kodaiTest.setName("Kodai Insignia");
		kodaiTest.setPrice(505050);

		kodaiTestList.add(kodaiTest);

		when(itemManager.search(anyString())).thenReturn(kodaiTestList);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "K1NG DK - Kodai insignia", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals("K1NG DK", raidTracker.getSpecialLootReceiver());
		assertEquals("Kodai insignia", raidTracker.getSpecialLoot());
		assertEquals(505050, raidTracker.getSpecialLootValue());
	}

	@Test
	public void TestTobPurple()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setRaidComplete(true);
		raidTracker.setInTheatreOfBlood(true);

		List<ItemPrice> avernicTestList = new ArrayList<>();

		ItemPrice avernicTest = new ItemPrice();

		avernicTest.setId(0);
		avernicTest.setName("Avernic defender hilt");
		avernicTest.setPrice(50505050);

		avernicTestList.add(avernicTest);

		when(itemManager.search(anyString())).thenReturn(avernicTestList);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "Canvasba found something special: Avernic defender hilt", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "Canvasba found something special: Lil\\u0027 Zik", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals("Canvasba", raidTracker.getSpecialLootReceiver());
		assertEquals("Avernic defender hilt", raidTracker.getSpecialLoot());
		assertFalse(raidTracker.petReceiver.isEmpty());
		assertEquals("Canvasba", raidTracker.getPetReceiver());
		assertEquals(50505050, raidTracker.getSpecialLootValue());
	}

	@Test
	public void TestDustAndKitRecipients()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.FRIENDSCHATNOTIFICATION, "", "Dust recipients: Canvasba", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		message.setMessage("Twisted Kit recipients: BallerTom");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals("Canvasba", raidTracker.getDustReceiver());
		assertEquals("BallerTom", raidTracker.getKitReceiver());
	}

	@Test
	public void TestChallengeModeAndCompletionCount()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);

		ChatMessage message  = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", "Your completed Chambers of Xeric Challenge Mode count is: 57.", "", 0);
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertTrue(raidTracker.isChallengeMode());
		assertEquals(57, raidTracker.getCompletionCount());

		message.setMessage("Your completed Chambers of Xeric count is: 443");
		raidTrackerPlugin.checkChatMessage(message, raidTracker);

		assertEquals(443, raidTracker.getCompletionCount());
	}


	//---------------------------------- onWidgetLoaded tests ------------------------------------------------


	@Test
	public void TestLootListFactory()
	{
		RaidTracker raidTracker = new RaidTracker();
		raidTracker.setInRaidChambers(true);
		raidTracker.setRaidComplete(true);


		//------------------- general case - 2 stacks of regular items --------------------------
		Item[] items = new Item[2];

		items[0] = new Item(1, 500);
		items[1] = new Item(2, 600);

		ItemComposition comp1 = mock(ItemComposition.class);
		ItemComposition comp2 = mock(ItemComposition.class);

		when(itemManager.getItemComposition(1)).thenReturn(comp1);
		when(itemManager.getItemComposition(2)).thenReturn(comp2);

		when(comp1.getName()).thenReturn("Pure Essence");
		when(comp1.getId()).thenReturn(1);
		when(comp1.getPrice()).thenReturn(5);

		when(comp2.getName()).thenReturn("Teak Planks");
		when(comp2.getId()).thenReturn(2);
		when(comp2.getPrice()).thenReturn(255);

		ArrayList<RaidTrackerItem> lootList = raidTrackerPlugin.lootListFactory(items);

		assertEquals(2, lootList.size());
		assertEquals(2500, lootList.get(0).getPrice());
		assertEquals(153000, lootList.get(1).getPrice());

		//----------------------------------------- purple ----------------------------------------
		items = new Item[1];
		items[0] = new Item(3, 1);

		ItemComposition comp3 = mock(ItemComposition.class);
		when(itemManager.getItemComposition(3)).thenReturn(comp3);

		when(comp3.getName()).thenReturn("Twisted Bow");
		when(comp3.getId()).thenReturn(3);
		when(comp3.getPrice()).thenReturn(1198653000);

		lootList = raidTrackerPlugin.lootListFactory(items);

		assertEquals(1, lootList.size());
		assertEquals(1198653000, lootList.get(0).getPrice());


	}

}