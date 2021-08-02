package net.benjaminurquhart.tos.handlers;

import java.awt.Color;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.benjaminurquhart.tos.Arguments;
import net.benjaminurquhart.tos.game.ANSI;
import net.benjaminurquhart.tos.game.Game;
import net.benjaminurquhart.tos.game.GameMode;
import net.benjaminurquhart.tos.game.GamePhase;
import net.benjaminurquhart.tos.game.PlayerTag;
import net.benjaminurquhart.tos.game.RoleTag;
import net.benjaminurquhart.tos.game.entities.Achievement;
import net.benjaminurquhart.tos.game.entities.Faction;
import net.benjaminurquhart.tos.game.entities.Genre;
import net.benjaminurquhart.tos.game.entities.Killer;
import net.benjaminurquhart.tos.game.entities.Player;
import net.benjaminurquhart.tos.game.entities.Role;
import net.benjaminurquhart.tos.game.entities.Scroll;
import net.benjaminurquhart.tos.game.entities.StringTableMessage;
import net.benjaminurquhart.tos.game.entities.Winner;

public class ServerMessageHandler extends MessageHandler {
	
	private static final Pattern END_GAME_RESULTS = Pattern.compile("\\(([^\\)]+)\\)");
	
	private Game game;
	private Arguments args;
	
    public ServerMessageHandler(Game game) {
    	this(game, null);
	}
    public ServerMessageHandler(Game game, Arguments args) {
    	super("Server");
		this.game = game;
		this.args = args;
    }
	@Override
	public void processCommand(byte[] command) {
	    	//this.onUnhandledCommand(command);
	        switch (command[0]&0xff) { 
	                 case 0: onDefaultFunction(command); break;
	                 case 1: onLoginSuccess(command); break;
	                 case 2: onJoinedGameLobby(command); break;
	                 case 3: onDefaultFunction(command); break;
	                 case 4: onUsersJoinedLobby(command); break;
	                 case 5: onUserLeftGame(command); break;
	                 case 6: onChatBoxMessage(command); break;
	                 case 7: onDefaultFunction(command); break;
	                 case 8: onDefaultFunction(command); break;
	                 case 9: onCustomRoleListAdd(command); break;
	                 case 10: onCustomRoleRemove(command); break;
	                 case 11: onGameStartCountdown(command); break;
	                 case 12: onGameCountdownCanceled(command); break;
	                 case 13: onDefaultFunction(command); break;
	                 case 14: onVoteToRepickHost(command); break;
	                 case 15: onDefaultFunction(command); break;
	                 case 16: onDoNotSpam(command); break;
	                 case 17: onGameStatus(command); break;
	                 case 18: onSystemMessage(command); break;
	                 case 19: onStringTableMessage(command); break;
	                 case 20: onDefaultFunction(command); break;
	                 case 21: onDefaultFunction(command); break;
	                 case 22: onDefaultFunction(command); break;
	                 case 23: onDefaultFunction(command); break;
	                 case 24: onDefaultFunction(command); break;
	                 case 25: onDefaultFunction(command); break;
	                 case 26: onFriendUpdate(command); break;
	                 case 27: onDefaultFunction(command); break;
	                 case 28: onDefaultFunction(command); break;
	                 case 29: onDefaultFunction(command); break;
	                 case 30: onDefaultFunction(command); break;
	                 case 31: onPartyInvite(command); break;
	                 case 32: onJoinedParty(command); break;
	                 case 33: onUsersJoinedParty(command); break;
	                 case 34: onDefaultFunction(command); break;
	                 case 35: onPartyChatBoxMessage(command); break;
	                 case 36: onUserLeftParty(command); break;
	                 case 37: onDefaultFunction(command); break;
	                 case 38: onDefaultFunction(command); break;
	                 case 39: onForcedLogout(command); break;
	                 case 40: onReturnToHomePage(command); break;
	                 case 41: onDefaultFunction(command); break;
	                 case 42: onShopPurchaseSuccess(command); break;
	                 case 43: onDefaultFunction(command); break;
	                 case 44: onDefaultFunction(command); break;
	                 case 45: onDefaultFunction(command); break;
	                 case 46: onDefaultFunction(command); break;
	                 case 47: onDefaultFunction(command); break;
	                 case 48: onUpdatePaidCurrency(command); break;
	                 case 49: onFriendReferrals(command); break;
	                 case 50: onDefaultFunction(command); break;
	                 case 51: onSetLastBonusWinTime(command); break;
	                 case 52: onDefaultFunction(command); break;
	                 case 53: onDefaultFunction(command); break;
	                 case 54: onDefaultFunction(command); break;
	                 case 55: onDefaultFunction(command); break;
	                 case 56: onDefaultFunction(command); break;
	                 case 57: onDefaultFunction(command); break;
	                 case 58: onUserBecamePartyHost(command); break;
	                 case 59: onDefaultFunction(command); break;
	                 case 60: onDefaultFunction(command); break;
	                 case 61: onKickedFromParty(command); break;
	                 case 62: onUserCanInviteToParty(command); break;
	                 case 63: onDefaultFunction(command); break;
	                 case 64: onDefaultFunction(command); break;
	                 case 65: onDefaultFunction(command); break;
	                 case 66: onUpdateFriendName(command); break;
	                 case 67: onDefaultFunction(command); break;
	                 case 68: onAccountPasswordUpdate(command); break;
	                 case 69: onDefaultFunction(command); break;
	                 case 70: onDefaultFunction(command); break;
	                 case 71: onJoinRankedQueue(command); break;
	                 case 72: onLeftRankedQueue(command); break;
	                 case 73: onRankedGameAvailable(command); break;
	                 case 74: onUserStatistics(command); break;
	                 case 75: onDefaultFunction(command); break;
	                 case 76: onDefaultFunction(command); break;
	                 case 77: onModeratorMessage(command); break;
	                 case 78: onReferAFriendUpdate(command); break;
	                 case 79: onPlayerStatistics(command); break;
	                 case 80: onScrollConsumed(command); break;
	                 case 81: onDefaultFunction(command); break;
	                 case 82: onLobbyWaitPopup(command); break;
	                 case 83: onPromotionPopup(command); break;
	                 case 84: onReferralCodes(command); break;
	                 case 85: onDefaultFunction(command); break;
	                 case 86: onDefaultFunction(command); break;
	                 case 87: onDefaultFunction(command); break;
	                 case 88: onCurrencyMultiplier(command); break;
	                 case 89: onDefaultFunction(command); break;

	                 case 90: onPickNames(command); break;
	                 case 91: onNamesAndPositionsOfUsers(command); break;
	                 case 92: onRoleAndPosition(command); break;
	                 case 93: onStartNight(command); break;
	                 case 94: onStartDay(command); break;
	                 case 95: onWhoDiedAndHow(command); break;
	                 case 96: onStartDiscussion(command); break;
	                 case 97: onStartVoting(command); break;
	                 case 98: onStartDefenseTransition(command); break;
	                 case 99: onStartJudgement(command); break;
	                 case 100: onTrialFoundGuilty(command); break;
	                 case 101: onTrialFoundNotGuilty(command); break;
	                 case 102: onLookoutNightAbilityMessage(command); break;
	                 case 103: onUserVoted(command); break;
	                 case 104: onUserCanceledVote(command); break;
	                 case 105: onUserChangedVote(command); break;
	                 case 106: onUserDied(command); break;
	                 case 107: onResurrection(command); break;
	                 case 108: onTellRoleList(command); break;
	                 case 109: onUserChosenName(command); break;
	                 case 110: onOtherMafia(command); break;
	                 case 111: onTellTownAmnesiacChangedRole(command); break;
	                 case 112: onAmnesiacChangedRole(command); break;
	                 case 113: onBroughtBackToLife(command); break;
	                 case 114: onStartFirstDay(command); break;
	                 case 115: onBeingJailed(command); break;
	                 case 116: onJailedTarget(command); break;
	                 case 117: onUserJudgementVoted(command); break;
	                 case 118: onUserChangedJudgementVote(command); break;
	                 case 119: onUserCanceledJudgementVote(command); break;
	                 case 120: onTellJudgementVotes(command); break;
	                 case 121: onExecutionerCompletedGoal(command); break;
	                 case 122: onJesterCompletedGoal(command); break;
	                 case 123: onMayorRevealed(command); break;
	                 case 124: onMayorRevealedAndAlreadyVoted(command); break;
	                 case 125: onDisguiserStoleYourIdentity(command); break;
	                 case 126: onDisguiserChangedIdentity(command); break;
	                 case 127: onDisguiserChangedUpdateMafia(command); break;
	                 case 128: onMediumIsTalkingToUs(command); break;
	                 case 129: onMediumCommunicating(command); break;
	                 case 130: onTellLastWill(command); break;
	                 case 131: onHowManyAbilitiesLeft(command); break;
	                 case 132: onFactionTargeting(command); break;
	                 case 133: onTellJanitorTargetsRole(command); break;
	                 case 134: onTellJanitorTargetsWill(command); break;
	                 case 135: onSomeoneHasWon(command); break;
	                 case 136: onMafiosoPromotedToGodfather(command); break;
	                 case 137: onMafiosoPromotedToGodfatherUpdateMafia(command); break;
	                 case 138: onMafiaPromotedToMafioso(command); break;
	                 case 139: onTellMafiaAboutMafiosoPromotion(command); break;
	                 case 140: onExecutionerConvertedToJester(command); break;
	                 case 141: onAmnesiacBecameMafiaOrCoven(command); break;
	                 case 142: onUserDisconnected(command); break;
	                 case 143: onSomeoneJailedTellFaction(command); break;
	                 case 144: onInvalidNameMessage(command); break;
	                 case 145: onStartNightTransition(command); break;
	                 case 146: onStartDayTransition(command); break;
	                 case 147: onLynchUser(command); break;
	                 case 148: onDeathNote(command); break;
	                 case 149: onDefaultFunction(command); break;
	                 case 150: onHousesChosen(command); break;
	                 case 151: onFirstDayTransition(command); break;
	                 case 152: onPlayersDoused(command); break;
	                 case 153: onCharactersChosen(command); break;
	                 case 154: onResurrectionSetAlive(command); break;
	                 case 155: onStartDefense(command); break;
	                 case 156: onDefaultFunction(command); break;
	                 case 157: onUserLeftDuringSelection(command); break;
	                 case 158: onVigilanteKilledTown(command); break;
	                 case 159: onNotifyUsersOfPrivateMessage(command); break;
	                 case 160: onPrivateMessage(command); break;
	                 case 161: onEarnedAchievements(command); break;
	                 case 162: onAccountProblem(command); break;
	                 case 163: onSpyNightAbilityMessage(command); break;
	                 case 164: onOneDayBeforeStalemate(command); break;
	                 case 165: onPetsChosen(command); break;
	                 case 166: onFacebookShareAchievement(command); break;
	                 case 167: onFacebookShareWin(command); break;
	                 case 168: onDeathAnimationsChosen(command); break;
	                 case 169: onFullMoonNight(command); break;
	                 case 170: onIdentify(command); break;
	                 case 171: onEndGameInfo(command); break;
	                 case 172: onEndGameChatMessage(command); break;
	                 case 173: onEndGameUserUpdate(command); break;
	                 case 174: onJoinedGameLobby(command); break;
	                 case 175: onRoleLotsInfoMesssage(command); break;
	                 case 176: onPayPalShowApprovalPage(command); break;
	                 case 177: onDefaultFunction(command); break;
	                 case 178: onDefaultFunction(command); break;
	                 case 179: onDefaultFunction(command); break;
	                 case 180: onVampirePromotion(command); break;
	                 case 181: onOtherVampires(command); break;
	                 case 182: onAddVampire(command); break;
	                 case 183: onCanVampiresConvert(command); break;
	                 case 184: onVampireDied(command); break;
	                 case 185: onVampireHunterPromoted(command); break;
	                 case 186: onVampireVisitedMessage(command); break;
	                 case 187: onDefaultFunction(command); break;
	                 case 188: onDefaultFunction(command); break;
	                 case 189: onDefaultFunction(command); break;
	                 case 190: onDefaultFunction(command); break;
	                 case 191: onDefaultFunction(command); break;
	                 case 192: onTransporterNotification(command); break;
	                 case 193: onItemPurchased(command); break;
	                 case 194: onUpdateFreeCurrency(command); break;
	                 case 195: onActiveEvents(command); break;
	                 case 196: onCauldronStatus(command); break;
	                 case 197: onTauntResult(command); break;
	                 case 198: onTauntActivated(command); break;
	                 case 199: onTauntConsumed(command); break;
	                 case 200: onTrackerNightAbility(command); break;
	                 case 201: onAmbusherNightAbility(command); break;
	                 case 202: onGuardianAngelProtection(command); break;
	                 case 203: onPirateDuel(command); break;
	                 case 204: onDuelTarget(command); break;
	                 case 205: onPotionMasterPotions(command); break;
	                 case 206: onHasNecronomicon(command); break;
	                 case 207: onOtherCoven(command); break;
	                 case 208: onPsychicNightAbility(command); break;
	                 case 209: onTrapperNightAbility(command); break;
	                 case 210: onTrapperTrapStatus(command); break;
	                 case 211: onPestilenceConversion(command); break;
	                 case 212: onJuggernautKillCount(command); break;
	                 case 213: onCovenGotNecronomicon(command); break;
	                 case 214: onGuardianAngelPromoted(command); break;
	                 case 215: onVIPTarget(command); break;
	                 case 216: onPirateDuelOutcome(command); break;
	                 case 217: onPartyGamemodeUpdate(command); break;
	                 case 218: onDefaultFunction(command); break;
	                 case 219: onAccountFlags(command); break;
	                 case 220: onZombieRotted(command); break;
	                 case 221: onLoverTarget(command); break;
	                 case 222: onPlagueSpread(command); break;
	                 case 223: onRivalTarget(command); break;
	                 case 224: onRankedInfo(command); break;
	                 case 225: onJailorDeathNote(command); break;
	                 case 226: onLoginFailure(command); break;
	                 case 227: onSpyNightInfo(command); break;
	                 case 228: onDefaultFunction(command); break;
	                 case 233: onVisibleReferralCodes(command); break;
	                 case 239: onTownTraitor(command); break;
	                 case 240: onTownTraitorCountdown(command); break;
	                 case 241: onAnonymousVotingUpdate(command); break;
	                default: onUnhandledCommand(command); break;
	        }
	    }
	
	
	private void onAccountPasswordUpdate(byte[] command) {
		if(command[1] == 1) {
			System.out.printf("%sAccount password updated successfully%s\n", ANSI.RESET, ANSI.GRAY);
		}
		else {
			System.out.printf("%sFailed to update password (0x%2x)%s\n", ANSI.ORANGE, command[1], ANSI.GRAY);
		}
	}
	private void onAccountProblem(byte[] command) {
		String reason = Game.STRING_TABLE.get("GUI_CONNECTION_STATUS_" + (command[1]-1))
							.getParsedText()
							.replaceAll("%u%([^%]+?)%uend%", " ($1) ")
							.replaceAll("%[^%]+?%", "");
		System.out.printf("%sAccount problem detected:\n%s%s\n", ANSI.ORANGE, reason, ANSI.GRAY);
	}
	private void onKickedFromParty(byte[] command) {
		System.out.printf("%sKicked from party.%s\n", ANSI.RESET, ANSI.GRAY);
	}
	private void onVisibleReferralCodes(byte[] command) {
		
	}
	private void onCustomRoleRemove(byte[] command) {
		List<Role> roles = game.getRoleList();
		if(roles == null || roles.size() < command[1]-1) {
			System.out.printf("Removed role at index %d\n", command[1]-1);
		}
		else {
			Role removed = roles.remove(command[1]-1);
			System.out.printf("Removed role: %s%s%s\n", ANSI.toTrueColor(removed.getColor()), removed.getName(), ANSI.GRAY);
		}
	}
	private void onAnonymousVotingUpdate(byte[] command) {
		//onUnhandledCommand(command);
		String key = "GUI_LOBBY_FEEDBACK_ALTERNATE_RULE_0_" + (command[2]-1);
		StringTableMessage msg = Game.STRING_TABLE.get(key);
		System.out.printf("%s%s%s\n", ANSI.YELLOW, msg.getText(), ANSI.GRAY);
	}
	private void onPlayersDoused(byte[] command) {
		onUnhandledCommand(command);
		
		Player player;
		for(int i = 2; i < command.length-1; i++) {
			player = game.getPlayer(command[i]);
			System.out.printf("%s%s was doused\n", ANSI.GRAY, player);
			player.addTag(PlayerTag.DOUSED);
		}
	}
	private void onTownTraitorCountdown(byte[] command) {
		StringTableMessage countdown = Game.STRING_TABLE.get(command[1] == 1 ? "GUI_DAYS_LEFT_TO_FIND_TRAITOR_DAY" : "GUI_DAYS_LEFT_TO_FIND_TRAITOR_DAY_2");
		StringTableMessage warning = Game.STRING_TABLE.get("GUI_DAYS_LEFT_TO_FIND_TRAITOR_DAY_3");
		
		System.out.printf(
				"%s%s%s\n%s%s%s\n",
				ANSI.toTrueColorBackground(Color.BLACK),
				ANSI.RED,
				countdown.getText().replace("%daysLeft%", ANSI.WHITE+String.valueOf(command[1])+ANSI.RED),
				warning.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}
	private void onTownTraitor(byte[] command) {
		Player traitor = game.getPlayer(command[1]), self = game.getSelfPlayer();
		
		StringTableMessage msg;
		if(traitor == self) {
			msg = Game.STRING_TABLE.get("GUI_WE_ARE_TRAITOR_" + (game.getMode().isCovenGamemode() ? "COVEN" : "MAFIA"));
		}
		else {
			msg = Game.STRING_TABLE.get("GUI_TRAITOR_PLAYER");
		}
		
		traitor.addTag(PlayerTag.TRAITOR);
		
		Faction selfFaction = self.getRole().getFaction();
		
		if(traitor == self || (selfFaction != null && (selfFaction.equals(Game.FACTION_TABLE.get("Mafia")) || selfFaction.equals(Game.FACTION_TABLE.get("Coven"))))) {
			System.out.printf(
					"%s%s%s%s%s\n",
					ANSI.toTrueColorBackground(Color.LIGHT_GRAY),
					ANSI.BLACK,
					msg.getText().replace("%name%", (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) + traitor.getName() + ANSI.BLACK),
					ANSI.RESET,
					ANSI.GRAY
			);
		}
	}
	private void onUserLeftParty(byte[] command) {
		System.out.printf("%s%s left the party%s\n", ANSI.RED, this.convertToString(command), ANSI.GRAY);
	}
	private void onCauldronStatus(byte[] command) {
		String[] split = this.convertToString(command).split(",");
		int timeRemaining = Integer.parseInt(split[4]);
		if(timeRemaining <= 0) {
			System.out.printf("%sCauldron is available (Day %s/%s)\n", ANSI.GRAY, split[1], split[2]);
		}
		else {
			int seconds = timeRemaining%60;
			int minutes = timeRemaining/60;
			int hours = minutes/60;
			
			minutes%=60;
			
			System.out.printf("%sCauldron will be available in %02dH %02dM %02dS (Day %s/%s)\n", ANSI.GRAY, hours, minutes, seconds, split[1], split[2]);
		}
	}
	public void onGameCountdownCanceled(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_START_COUNTDOWN_CANCELED");
		System.out.printf("%s%s%s\n", ANSI.RED, msg.getText(), ANSI.GRAY);
	}
	public void onLeftRankedQueue(byte[] command) {
		System.out.printf("%sLeft Ranked queue%s\n", ANSI.GREEN, ANSI.GRAY);
	}

	
	public void onReferralCodes(byte[] command) {
		System.out.printf("%sReceived referral code update\n", ANSI.GRAY);
	}

	
	public void onLobbyWaitPopup(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_RANKED_MUST_WAIT");
		System.out.printf("%s%s%s\n", ANSI.YELLOW, msg.getText(), ANSI.GRAY);
	}

	
	public void onCustomRoleListAdd(byte[] command) {
		Role role = Game.ROLES[command[1]-1];
		System.out.printf(
				"%sAdded role: %s%s%s\n",
				ANSI.GRAY,
				ANSI.toTrueColor(role.getColor()),
				role.getName(),
				ANSI.GRAY
		);
		List<Role> roles = game.getRoleList();
		if(roles == null) {
			game.setRoleList(roles = new ArrayList<>());
		}
		roles.add(role);
	}

	
	public void onFriendReferrals(byte[] command) {
		System.out.printf("%sReceived friend referral information\n", ANSI.GRAY);
	}

	
	public void onVoteToRepickHost(byte[] command) {
		int votes = command[1]-1;
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_REPICK_HOST_VOTES_NEEDED_" + (votes > 1 ? "2" : "1"));
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				msg.getText().replace("%x%", String.valueOf(votes)),
				ANSI.GRAY
		);
	}

	
	public void onRankedGameAvailable(byte[] command) {
		System.out.printf("%sA Ranked match is available%s\n", ANSI.GREEN, ANSI.GRAY);
	}

	
	public void onJoinRankedQueue(byte[] command) {
		String time = new String(Arrays.copyOfRange(command, 2, command.length-1), Charset.forName("UTF-8"));
		// For some reason, the server uses the same message ID for referrals as ranked queues.
		// Why? Beats me. At least referrals aren't numeric data.
		if(!time.matches("\\d+")) {
			return;
		}
		System.out.printf(
				"%sJoined Ranked queue (%s seconds remaining)%s\n", 
				ANSI.GREEN,
				time,
				ANSI.GRAY
		);
	}

	
	public void onPartyGamemodeUpdate(byte[] command) {
		//onUnhandledCommand(command);
		GameMode mode = Game.GAME_MODE_ID_TABLE.get((command[2]&0xff)-1);
		if(mode == null) {
			System.out.printf(
					"%s%s %d%s\n", 
					ANSI.RED,
					Game.STRING_TABLE.get("GUI_PARTY_GAME_MODE_FAIL").getText(),
					(command[2]&0xff)-1,
					ANSI.GRAY
			);
			return;
		}
		System.out.printf("%sGame Mode updated to %s%s\n", ANSI.RESET, mode.getLabel().getText(), ANSI.GRAY);
	}

	
	public void onUserBecamePartyHost(byte[] command) {
		System.out.printf(
				"%s%s is now the host%s\n",
				ANSI.GREEN,
				this.convertToString(command),
				ANSI.GRAY
		);
	}

	
	public void onUsersJoinedParty(byte[] command) {
		//this.onUnhandledCommand(command);
		int seperator = this.indexOf(command, (byte)'*');
		byte status = command[seperator+1];
		
		String name = new String(Arrays.copyOfRange(command, 1, seperator), Charset.forName("UTF-8"));
		switch(status) {
		case 1: System.out.printf("%s%s was invited to the party\n", ANSI.GRAY, name);
		case 2: System.out.printf("%s%s rejected the party invite\n", ANSI.GRAY, name); break;
		case 3: System.out.printf("%s%s joined the party\n", ANSI.GRAY, name); break;
		case 5: System.out.printf("%s%s accepted the party invite\n", ANSI.GRAY, name); break;
		default: this.onUnhandledCommand(command);
		}
	}

	
	public void onJoinedParty(byte[] command) {
		System.out.printf("%sJoined party%s\n", ANSI.RESET, ANSI.GRAY);
	}

	
	public void onUserCanInviteToParty(byte[] command) {
		System.out.printf(
				"%s%s%s can now invite others to the party%s\n", 
				ANSI.RESET,
				new String(Arrays.copyOfRange(command, 1, command.length-1), Charset.forName("UTF-8")),
				ANSI.GREEN,
				ANSI.GRAY
		);
	}

	
	public void onPartyInvite(byte[] command) {
		int seperator = this.indexOf(command, (byte)'*');
		String username = new String(Arrays.copyOfRange(command, seperator+1, command.length-1), Charset.forName("UTF-8"));
		String party = new String(Arrays.copyOfRange(command, 1, seperator), Charset.forName("UTF-8"));
		System.out.printf("%sReceived party invite from %s%s (%s)%s\n", ANSI.GREEN, ANSI.RESET, username, party, ANSI.GRAY);
	}

	
	public void onPartyChatBoxMessage(byte[] command) {
		int seperator = this.indexOf(command, (byte)'*');
		String message = new String(Arrays.copyOfRange(command, seperator+1, command.length-1), Charset.forName("UTF-8"));
		String name = new String(Arrays.copyOfRange(command, 1, seperator), Charset.forName("UTF-8"));
		System.out.printf("%s%s: %s%s\n", ANSI.RESET, name, Game.insertColors(message, game), ANSI.GRAY);
	}

	
	public void onItemPurchased(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onGameStartCountdown(byte[] command) {
		System.out.println(ANSI.GREEN+"Lobby is full, game is starting..."+ANSI.GRAY);
	}

	
	public void onGameStatus(byte[] command) {
		int asterisk = this.indexOf(command, (byte)'*');
		int players = Integer.parseInt(new String(Arrays.copyOfRange(command, 1, asterisk), Charset.forName("UTF-8")));
		int games = Integer.parseInt(new String(Arrays.copyOfRange(command, asterisk+1, command.length-1), Charset.forName("UTF-8")));
		System.out.printf("%s%d players online (%d games running)%s\n", ANSI.GREEN, players, games, ANSI.GRAY);
	}

	
	public void onSpyNightInfo(byte[] command) {
		StringTableMessage msg;
		for(int i = 1; i < command.length-1; i++) {
			msg = Game.STRING_TABLE.get("GAME_SPY_RESULT_"+((command[i]&0xff)-1));
			System.out.printf("%s%s%s%s\n", ANSI.RED, ANSI.toTrueColorBackground(msg.getColor()), msg.getText(), ANSI.RESET, ANSI.GRAY);
		}
	}

	
	public void onLoginFailure(byte[] command) {
		System.out.println("Login failed");
	}

	
	public void onJailorDeathNote(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_JAILOR_MENU_OPTION"+command[3]);
		System.out.printf(
				"%sDeath Note: %s%s%s\n",
				ANSI.RESET,
				ANSI.YELLOW,
				msg.getText(),
				ANSI.GRAY
		);
	}

	
	public void onRankedInfo(byte[] command) {
		int[] stats = Arrays.stream(this.convertToString(command, true).split(","))
							.mapToInt(Integer::parseInt)
							.toArray();
		//System.out.println(Arrays.toString(stats));
		boolean coven = stats[0]/6 == 2;
		
		int practiceGamesRequired = stats[9];
		int practiceGamesPlayed = stats[1];
		int rankedGamesDrawn = stats[12];
		int rankedGamesLost = stats[11];
		int rankedGamesWon = stats[10];
		
		int seasonHigh = stats[14];
		int elo = stats[15];
		
		System.out.println("-----------------------------------------------");
		System.out.println((coven ? "Coven" : "Classic") + " Ranked Statistics:");
		System.out.printf("Practice Games: %d/%d\n", practiceGamesPlayed, practiceGamesRequired);
		if(practiceGamesRequired <= practiceGamesPlayed) {
			System.out.printf("Current Elo: %d\nSeason High: %d\n", elo, seasonHigh);
			System.out.printf("Wins: %d | Losses: %d | Draws: %d\n", rankedGamesWon, rankedGamesLost, rankedGamesDrawn);
		}
		System.out.println("-----------------------------------------------");
	}

	
	public void onRivalTarget(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onPlagueSpread(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_PLAGUED_TARGET");
		Player player;
		for(int i = 1; i < command.length-1; i++) {
			player = game.getPlayer(command[i]);
			System.out.printf("%s%s\n", ANSI.GRAY, msg.getText().replace("%name%", player.getName()));
			player.addTag(PlayerTag.INFECTED);
		}
	}

	
	public void onLoverTarget(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_LOVER_TARGET");
		Player lover = game.getPlayer(command[1]);
		lover.addTag(PlayerTag.LOVER);
		System.out.printf(
				"%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				msg.getText().replace("%name%", ANSI.WHITE+lover.getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onZombieRotted(byte[] command) {
		Player zombie = game.getPlayer(command[1]);
		zombie.addTag(PlayerTag.DECAYED);
		System.out.printf(
				"%s%s (%s%s%s) has rotted\n",
				ANSI.GRAY,
				zombie,
				ANSI.toTrueColor(zombie.getRole().getColor()),
				zombie.getRole().getName(),
				ANSI.GRAY
		);
	}

	
	public void onAccountFlags(byte[] command) {
		System.out.printf("%sAccount flags: 0x%02x (0b%s)\n", ANSI.GRAY, command[1], Integer.toBinaryString(command[1]));
	}

	
	public void onPirateDuelOutcome(byte[] command) {
		boolean isPirate = game.getSelfPlayer().getRole().equals(Game.ROLE_TABLE.get("Pirate"));
		StringTableMessage msg = Game.STRING_TABLE.get(String.format(
				"GUI_PIRATE_DUEL_RESULTS_%s%d%d",
				isPirate ? 'A' : 'D',
				command[1],
				command[2]
		));
		ANSI color = msg.getText().contains("lost") ? ANSI.RED : ANSI.GREEN;
		System.out.printf(
				"%s%s%s\n",
				color,
				msg.getText(),
				ANSI.GRAY
		);
	}

	
	public void onVIPTarget(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_VIPTARGET");
		Player vip = game.getPlayer(command[1]);
		vip.addTag(PlayerTag.VIP);
		System.out.printf(
				"%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				msg.getText().replace("%name%", ANSI.WHITE+vip.getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onGuardianAngelPromoted(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Survivor"));
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				Game.insertColors(Game.STRING_TABLE.get("GUI_GUARDIAN_ANGEL_CONVERTED_TO_29").getText(), ANSI.GREEN, game),
				ANSI.GRAY
		);
	}

	
	public void onCovenGotNecronomicon(byte[] command) {
		StringTableMessage hasNecro = Game.STRING_TABLE.get("GUI_HAS_NECRONOMICON"), diedWithNecro = Game.STRING_TABLE.get("GUI_HAS_NECRONOMICON2");
		Player player = game.getPlayer(command[2]);
		// Previous holder died, passed on to next member
		// Otherwise, first time Coven has Necronomicon (Night 3)
		if(command[1] == 2) {
			System.out.printf(
					"%s%s%s\n",
					ANSI.RED,
					diedWithNecro.getText().replace("%name%", String.format("%s (%s%s%s)", player, ANSI.COVEN, player.getRole().getName(), ANSI.RED)),
					ANSI.GRAY
			);
			hasNecro = Game.STRING_TABLE.get("GUI_HAS_NECRONOMICON3");
			player = game.getPlayer(command[3]);
		}
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				hasNecro.getText().replace("%name%", String.format("%s (%s%s%s)", player, ANSI.COVEN, player.getRole().getName(), ANSI.GREEN)),
				ANSI.GRAY
		);
	}

	
	public void onJuggernautKillCount(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_53_FEEDBACK"+(command[1]-1));
		if(msg == null) {
			System.out.printf(
					"%s%sTarget was killed (%d kills)%s%s\n",
					ANSI.toTrueColorBackground(Color.BLACK),
					ANSI.GREEN,
					command[1]-1,
					ANSI.RESET,
					ANSI.GRAY
			);
		}
		else {
			System.out.printf(
					"%s%s%s%s%s\n",
					ANSI.toTrueColorBackground(Color.BLACK),
					ANSI.GREEN,
					msg.getText(),
					ANSI.RESET,
					ANSI.GRAY
			);
		}
	}

	
	public void onPestilenceConversion(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Pestilence"));
	}

	
	public void onTrapperTrapStatus(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_48_FEEDBACK"+(command[1]+1));
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.BLACK,
				ANSI.GREEN,
				msg.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onTrapperNightAbility(byte[] command) {
		//onUnhandledCommand(command);
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_48_FEEDBACK1");
		Role role = Game.ROLES[command[1]-1];
		
		String text = msg.getText().replace("%role%", ANSI.toTrueColor(role.getColor())+role.getName());
		
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.BLACK),
				ANSI.GREEN,
				text,
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onPsychicNightAbility(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_49_FEEDBACK"+(command[1] == 1 ? 2 : 1));
		String text = msg.getText().replace("%name1%", game.getPlayer(command[2]).getName()).replace("%name2%", game.getPlayer(command[3]).getName());
		if(command[1] == 2) {
			text = text.replace("%name3%", game.getPlayer(command[4]).getName());
		}
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				ANSI.GREEN,
				text,
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onOtherCoven(byte[] command) {
		this.tellFactionMembers(command, Game.FACTION_TABLE.get("Coven"));
	}

	
	public void onHasNecronomicon(byte[] command) {
		//onUnhandledCommand(command);
	}

	
	public void onPotionMasterPotions(byte[] command) {
		System.out.printf(
				"%sPotion Status%s: %sHeal%s: %d/3 | %sReveal%s: %d/3 | %sKill%s: %d/3%s\n",
				ANSI.COVEN,
				ANSI.RESET,
				ANSI.GREEN,
				ANSI.RESET,
				4-command[1],
				ANSI.CYAN,
				ANSI.RESET,
				4-command[3],
				ANSI.RED,
				ANSI.RESET,
				4-command[2],
				ANSI.GRAY
		);
	}

	
	public void onDuelTarget(byte[] command) {
		System.out.printf("%sDueling %s...\n", ANSI.GRAY, game.getPlayer(command[1]));
	}

	
	public void onPirateDuel(byte[] command) {
		System.out.printf(
				"%s%sThe pirate duel has roleblocked you!%s%s\n",
				ANSI.toTrueColorBackground(Color.LIGHT_GRAY),
				ANSI.BLACK,
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onGuardianAngelProtection(byte[] command) {
		System.out.printf("%sThe Guardian Angel was watching over %s%s%s\n", ANSI.GREEN, ANSI.RESET, game.getPlayer(command[1]), ANSI.GRAY);
	}

	
	public void onAmbusherNightAbility(byte[] command) {
		//onUnhandledCommand(command);
		Player ambusher = game.getPlayer(command[1]);
		ambusher.setRole(Game.ROLE_TABLE.get("Ambusher"));
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_51_NOTIFICATION");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				ANSI.GREEN,
				msg.getText().replace("%name%", ANSI.WHITE+ambusher.getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onTrackerNightAbility(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_ROLE_47_FEEDBACK1");
		Player destination = game.getPlayer(command[1]);
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				ANSI.GREEN,
				msg.getText().replace("%name%", ANSI.WHITE+destination.getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
		
	}

	
	public void onTauntConsumed(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onTauntActivated(byte[] command) {
		//onUnhandledCommand(command);
		System.out.printf("%s%s was taunted with %s\n", ANSI.GRAY, game.getPlayer(command[1]), Game.TAUNTS[command[3]-1]);
	}

	
	public void onTauntResult(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onActiveEvents(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onUpdateFreeCurrency(byte[] command) {
		System.out.printf("%sMerit Points updated: %s%s\n", ANSI.LIGHT_GRAY, this.convertToString(command), ANSI.GRAY);
	}

	
	public void onTransporterNotification(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onVampireVisitedMessage(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_VAMPIRE_VISITED_MESSAGE2");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				ANSI.GREEN,
				msg.getText().replace("%name%", ANSI.WHITE+game.getPlayer(command[1]).getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onVampireHunterPromoted(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Vigilante"));
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				Game.insertColors(Game.STRING_TABLE.get("GUI_VAMPIRE_HUNTER_PROMOTED1").getText(), ANSI.GREEN, game),
				ANSI.GRAY
		);
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				Game.STRING_TABLE.get("GUI_VAMPIRE_HUNTER_PROMOTED2").getText(),
				ANSI.GRAY
		);
	}

	
	public void onVampireDied(byte[] command) {
		game.getPlayer(command[1]).kill();
	}

	
	public void onCanVampiresConvert(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get(command[1] == 1 ? "GUI_VAMP_CANT_BITE_TONIGHT" : "GUI_VAMP_CAN_BITE_TONIGHT");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.BLACK),
				ANSI.GREEN,
				msg.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onAddVampire(byte[] command) {
		game.getPlayer(command[1]).setRole(Game.ROLE_TABLE.get("Vampire"));
	}

	
	public void onOtherVampires(byte[] command) {
		System.out.printf("%sVampire%s Members:\n", ANSI.VAMPIRE, ANSI.RESET);
		Player member;
		Role role = Game.ROLE_TABLE.get("Vampire");
		for(int i = 1; i < command.length-1; i+=2) {
			member = game.getPlayer(command[i]);
			member.setRole(role);
			System.out.printf(
					"%s (%s%s%s)\n", 
					member, 
					ANSI.toTrueColor(role.getColor()), 
					command[i+1] == 0 ? "Youngest" : "Vampire",
					ANSI.RESET
			);
		}
		System.out.print(ANSI.GRAY);
	}

	
	public void onVampirePromotion(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Vampire"));
	}

	
	public void onPayPalShowApprovalPage(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onRoleLotsInfoMesssage(byte[] command) {
		game.setPhase(GamePhase.GET_ROLE);
		System.out.printf("%s------------------Role Lots--------------------\n", ANSI.RESET);
		String[] lots = new String(Arrays.copyOfRange(command, 1, command.length-1), Charset.forName("UTF-8")).split("\\*"), tmp;
		int mine, total;
		Role role;
		for(String lot : lots) {
			tmp = lot.split(",");
			role = Game.ROLES[Integer.parseInt(tmp[0])];
			total = Integer.parseInt(tmp[1]);
			mine = Integer.parseInt(tmp[2]);
			System.out.printf(
					"%s%-18s%s %3d/%-16d (%d%%)\n",
					ANSI.toTrueColor(role.getColor()),
					role.getName(),
					ANSI.RESET,
					mine,
					total,
					Math.round(mine/(double)total*100)
			);
		}
		System.out.printf("%s-----------------------------------------------%s\n", ANSI.RESET, ANSI.GRAY);
	}

	
	public void onEndGameUserUpdate(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onEndGameChatMessage(byte[] command) {
		onChatBoxMessage(command);
	}

	public void onEndGameInfo(byte[] command) {
		//onUnhandledCommand(command);
		String infoString = this.convertToString(Arrays.copyOfRange(command, this.indexOf(command, (byte)'(')-1, command.length));
		Matcher matcher = END_GAME_RESULTS.matcher(infoString);
		String username, name;
		int position, roleID;
		Role role;
		
		Player player;
		boolean traitorFound = Arrays.stream(game.getPlayers()).filter(p -> p != null).anyMatch(p -> p.getTags().contains(PlayerTag.TRAITOR)) || game.getWinner() == Game.WINNERS[18]; // Special case for Lovers Mode
		
		String[] info, table = new String[15];
		while(matcher.find()) {
			info = matcher.group(1).split(",");
			username = info[1];
			name = info[0];
			
			if(info[3].length() == 1) {
				roleID = info[3].charAt(0);
			}
			else {
				roleID = Integer.parseInt(info[3].trim().substring(2), 16);
			}
			
			position = Integer.parseInt(info[2].trim().substring(2), 16);
			
			role = Game.ROLES[roleID-1];
			
			player = game.getPlayer(position);
			
			if(!traitorFound) {
				try {
					if(game.getWinner() != Game.WINNERS[0] && role.getFaction() != null && role.getFaction().getName().equals(Game.WINNERS[0].getName()) && player.didWin()) {
						System.out.printf(
								"%sThrough process of deduction, %s (%02d) was determined to be Town Traitor %s%s\n",
								ANSI.GRAY,
								player,
								position,
								role.getName(),
								ANSI.RESET
						);
						player.addTag(PlayerTag.TRAITOR);
						traitorFound = true;
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			player.setName(username);
			player.setRole(role);
			
			table[position-1] = String.format(
					"%s%-20s %-20s %s%-20s%s",
					ANSI.RESET,
					name,
					username,
					player.getTags().contains(PlayerTag.TRAITOR) ? (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) : ANSI.toTrueColor(role.getColor()),
					role.getName(),
					ANSI.GRAY
			);
		}
		System.out.printf("%s---------------Game Information----------------\n", ANSI.RESET);
		for(String s : table) {
			if(s == null) continue;
			System.out.println(s);
		}
		System.out.printf("%s-----------------------------------------------%s\n", ANSI.RESET, ANSI.GRAY);
	}

	
	public void onIdentify(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onFullMoonNight(byte[] command) {
		System.out.printf(
				"%s%sThere is a full moon out tonight%s%s\n",
				ANSI.WHITE,
				ANSI.toTrueColorBackground(Color.CYAN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onDeathAnimationsChosen(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onFacebookShareWin(byte[] command) {
		//onUnhandledCommand(command);
		System.out.printf("%sShare this win on Facebook%s\n", ANSI.BLUE, ANSI.GRAY);
	}

	
	public void onFacebookShareAchievement(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onPetsChosen(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onOneDayBeforeStalemate(byte[] command) {
		System.out.printf("%s%s%s\n", ANSI.GREEN, Game.STRING_TABLE.get("GUI_ONE_DAY_BEFORE_STALEMATE").getText(), ANSI.GRAY);
		
	}

	
	public void onSpyNightAbilityMessage(byte[] command) {
		Faction faction = Game.FACTIONS[command[1]];
		System.out.printf(
				"%sA member of the %s%s%s visited %s%s%s last night%s\n",
				ANSI.GREEN,
				ANSI.valueOf(faction.getName().toUpperCase()),
				faction.getName(),
				ANSI.GREEN,
				ANSI.RESET,
				game.getPlayer(command[2]),
				ANSI.GREEN,
				ANSI.GRAY
		);
	}

	
	public void onEarnedAchievements(byte[] command) {
		Achievement achievement = Game.ACHIEVEMENTS[Integer.parseInt(this.convertToString(command))];
		Role role = achievement.getRole();
		
		StringTableMessage message = Game.STRING_TABLE.get("GUI_ACHIEVEMENT_DESC"+achievement.getID());
		String name, text;
		Color color;
		
		if(role == null) {
			Genre genre = Game.GENRE_TABLE.get(achievement.getGenre());
			color = genre.getColor();
			name = genre.getID();
		}
		else {
			color = role.getColor();
			name = role.getName();
		}
		text = Game.insertColors(message.getText(), game);
		
		System.out.printf(
				"%sReceived %s%s%s achievement: %s%s\n",
				ANSI.RESET,
				ANSI.toTrueColor(color),
				name,
				ANSI.RESET,
				text,
				ANSI.GRAY
		);
	}

	
	public void onPrivateMessage(byte[] command) {
		//for(byte b : command) System.out.printf("%02x ", b); System.out.println();
		String color = ANSI.toTrueColor(Color.MAGENTA);
		// Blackmailer
		if(command[1] == 3) {
			System.out.printf(
					"%sFrom %s%s%s to %s%s:%s %s%s\n",
					color,
					ANSI.GRAY,
					game.getPlayer(command[2]),
					color,
					
					ANSI.GRAY,
					game.getPlayer(command[3]),
					color,
					new String(Arrays.copyOfRange(command, 4, command.length-1), Charset.forName("UTF-8")),
					ANSI.GRAY
			);
		}
		else {
			System.out.printf(
					"%s%s %s%s:%s %s%s\n",
					color,
					command[1] == 0x01 ? "To" : "From",
					ANSI.GRAY,
					game.getPlayer(command[2]),
					color,
					new String(Arrays.copyOfRange(command, 3, command.length-1), Charset.forName("UTF-8")),
					ANSI.GRAY
			);
		}
	}

	
	public void onNotifyUsersOfPrivateMessage(byte[] command) {
		System.out.printf(
				"%s%s%s is whispering to %s%s%s\n",
				ANSI.LIGHT_GRAY,
				game.getPlayer(command[1]),
				ANSI.toTrueColor(Color.MAGENTA),
				ANSI.LIGHT_GRAY,
				game.getPlayer(command[2]),
				ANSI.GRAY
		);
	}

	
	public void onVigilanteKilledTown(byte[] command) {
		game.getSelfPlayer().addTag(PlayerTag.KILLED_TOWN);
	}

	
	public void onUserLeftDuringSelection(byte[] command) {
		//this.onUnhandledCommand(command);
		if(game.getPhase() == GamePhase.PICK_NAME) {
			System.out.printf("%s%s (#%d) left during name selection%s\n", ANSI.RED, game.getPlayer(command[1]), command[1], ANSI.GRAY);
		}
	}

	
	public void onStartDefense(byte[] command) {
		game.setPhase(GamePhase.DEFENSE);
		System.out.println(ANSI.GRAY+"----------Defense----------");
	}

	
	public void onResurrectionSetAlive(byte[] command) {
		game.getPlayer(command[1]).resurrect();
	}

	
	public void onCharactersChosen(byte[] command) {
		for(int i = 1; i < command.length-1; i+=2) {
			game.getPlayer(command[i]&0xff).setCharacter(Game.CHARACTERS[(command[i+1]&0xff)-1]);
		}
	}

	
	public void onFirstDayTransition(byte[] command) {
		game.setPhase(GamePhase.FIRST_DAY_TRANSITION);
		System.out.println(ANSI.GRAY+"---------- Start ----------");
	}

	
	public void onHousesChosen(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onDeathNote(byte[] command) {
		System.out.printf(
				"%sDeath Note:\n%s%s%s\n",
				ANSI.RESET,
				ANSI.RED,
				new String(Arrays.copyOfRange(command, 3, command.length-1), Charset.forName("UTF-8")).replace((char)0x0d, '\n'),
				ANSI.GRAY
		);
	}

	
	public void onLynchUser(byte[] command) {
		game.setPhase(GamePhase.EXECUTION);
		game.getPlayerOnTrial().lynch();
	}

	
	public void onStartDayTransition(byte[] command) {
		if(command.length == 2) {
			System.out.printf("%sNo deaths last night...\n", ANSI.GRAY);
		}
		for(int i = 1; i < command.length-1; i++) {
			game.getPlayer(command[i]).kill();
		}
	}

	
	public void onStartNightTransition(byte[] command) {
		game.setPhase(GamePhase.NIGHT_TRANSITION);
	}

	
	public void onInvalidNameMessage(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onSomeoneJailedTellFaction(byte[] command) {
		//onUnhandledCommand(command);
		
		Player player = game.getPlayer(command[1]);
		Role role = player.getRole();
		
		ANSI color = player.getTags().contains(PlayerTag.TRAITOR) ? (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) : (role == null ? ANSI.LIGHT_GRAY : ANSI.valueOf(role.getFaction().getName().toUpperCase()));
		System.out.printf(
				"%s%s (%s%s%s) was hauled off to jail!%s\n",
				ANSI.LIGHT_GRAY,
				game.getPlayer(command[1]),
				color,
				role == null ? "???" : role.getName(),
				ANSI.LIGHT_GRAY,
				ANSI.GRAY
		);
	}

	
	public void onUserDisconnected(byte[] command) {
		//onUnhandledCommand(command);
	}

	
	public void onAmnesiacBecameMafiaOrCoven(byte[] command) {
		game.getPlayer(command[1]).setRole(Game.ROLES[(command[2]&0xff)-1]);
	}

	
	public void onExecutionerConvertedToJester(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Jester"));
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				Game.insertColors(Game.STRING_TABLE.get("GUI_EXECUTIONER_CONVERTED_TO_27").getText(), ANSI.GREEN, game),
				ANSI.GRAY
		);
	}

	
	public void onTellMafiaAboutMafiosoPromotion(byte[] command) {
		//onUnhandledCommand(command);
		Role role = Game.ROLE_TABLE.get("Mafioso");
		game.getPlayer(command[1]).setRole(role);
		System.out.printf(
				"%s was promoted to %s%s%s\n",
				game.getPlayer(command[1]),
				ANSI.toTrueColor(role.getColor()),
				role.getName(),
				ANSI.GRAY
		);
	}

	
	public void onMafiaPromotedToMafioso(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Mafioso"));
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.LIGHT_GRAY),
				ANSI.BLACK,
				Game.STRING_TABLE.get("GUI_PROMOTED_TO_23").getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onMafiosoPromotedToGodfatherUpdateMafia(byte[] command) {
		Role role = Game.ROLE_TABLE.get("Godfather");
		game.getPlayer(command[1]).setRole(role);
		System.out.printf(
				"%s was promoted to %s%s%s\n",
				game.getPlayer(command[1]),
				ANSI.toTrueColor(role.getColor()),
				role.getName(),
				ANSI.GRAY
		);
	}

	
	public void onMafiosoPromotedToGodfather(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLE_TABLE.get("Godfather"));
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.LIGHT_GRAY),
				ANSI.BLACK,
				Game.STRING_TABLE.get("GUI_PROMOTED_TO_23").getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onSomeoneHasWon(byte[] command) {
		//onUnhandledCommand(command);
		Winner winner = Game.WINNERS[command[1]-1];
		game.setWinner(winner);
		
		List<String> winners = new ArrayList<>();
		Player player;
		
		for(int i = 2; i < command.length-1; i++) {
			player = game.getPlayer(command[i]);
			winners.add(player.getName());
			player.win();
		}
		System.out.printf(
				"%s%s%s\n",
				ANSI.toTrueColor(winner.getColor()),
				winner,
				ANSI.GRAY
		);
		if(winners.size() > 0) {
			System.out.printf(
					"%s%s %s won the game%s\n", 
					ANSI.GREEN,
					String.join(", ", winners),
					winners.size() == 1 ? "has" : "have",
					ANSI.GRAY
			);
		}
	}

	public void onTellJanitorTargetsWill(byte[] command) {
		//onUnhandledCommand(command);
		String will = new String(Arrays.copyOfRange(command, 2, command.length-1), Charset.forName("UTF-8")).replace((char)0x0d, '\n');
		if(!will.trim().isEmpty()) {
			System.out.printf(
					"%s%s%s\n%s%s%s\n",
					ANSI.toTrueColorBackground(Color.BLACK),
					ANSI.GREEN,
					Game.STRING_TABLE.get("GUI_JANITOR_KNOWS_WILL1").getText(),
					Game.STRING_TABLE.get("GUI_JANITOR_KNOWS_WILL2").getText(),
					ANSI.RESET,
					ANSI.GRAY
			);
			System.out.printf("%sWill:\n%s%s\n\n", ANSI.RESET, Game.insertColors(will, game), ANSI.GRAY);
		}
	}

	
	public void onTellJanitorTargetsRole(byte[] command) {
		//onUnhandledCommand(command);
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_JANITOR_KNOWS_ROLE_X");
		Player target = game.getSelfPlayer().getTarget();
		Role role = Game.ROLES[(command[1]&0xff)-1];
		if(target != null) {
			target.setRole(role);
		}
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(msg.getColor()),
				ANSI.GREEN,
				msg.getText().replace("%role%", ANSI.toTrueColor(role.getColor())+role.getName()+ANSI.GREEN),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onFactionTargeting(byte[] command) {
		//onUnhandledCommand(command);
		Player member = game.getPlayer(command[1]);
		Role role = member.getRole();
		
		// Send help
		String color = String.valueOf(
				member.getTags().contains(PlayerTag.TRAITOR) ? (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) : ANSI.toTrueColor(role.getColor())
		);
		
		if(command[3] == 30) {
			System.out.printf(
					"%s%s (%s%s%s) has changed their mind\n",
					ANSI.GRAY,
					member,
					color,
					role.getName(),
					ANSI.GRAY
			);
		}
		else {
			Player target = game.getPlayer(command[3]);
			String targetName = target.getName(), type = "", action = "";
			String msgID = "GUI_FACTION_TARGETING_"+role.getID();
			
			member.setTarget(target);
			// TODO: fix messages for 2-target roles
			if(command[4] == 7 && role.equals(Game.ROLE_TABLE.get("Medusa"))) {
				targetName = "visitors";
			}
			else if(role.equals(Game.ROLE_TABLE.get("Necromancer"))) {
				boolean ghoul = command[4] == 11;
				if(ghoul) {
					msgID += "_GHOUL";
				}
				else {
					type = "target";
					action = "target";
					msgID += "_ADDITIONAL";
				}
			}
			else if(command[4] == 4 && role.equals(Game.ROLE_TABLE.get("Coven Leader"))) {
				msgID += "_VICTIM";
			}
			
			// New disguiser backwards-compatibility
			else if(command.length > 4 && command[4] == 4 && role.equals(Game.ROLE_TABLE.get("Disguiser"))) {
				msgID += "_VICTIM";
			}
			StringTableMessage msg = Game.STRING_TABLE.get(msgID);
			String title = String.format(
					 "%s (%s%s%s)",
					 member,
					 color,
					 role.getName(),
					 ANSI.GRAY
			);
			if(msg == null) {
				if(role.equals(Game.ROLE_TABLE.get("Potion Master"))) {
					switch(command[5]) {
					case 1: msgID+="_HEAL"; break;
					case 2: msgID+="_ATTACK"; break;
					case 3: msgID+="_INVESTIGATE"; break;
					}
					msg = Game.STRING_TABLE.get(msgID);
				}
				if(msg == null) {
					msg = Game.STRING_TABLE.get("GUI_FACTION_TARGETING_DEFAULT");
				}
			}
			System.out.printf(
					"%s%s\n",
					ANSI.GRAY,
					msg.getText()
								 .replace("%name%", title)
								 .replace("%name1%", title)
								 .replace("%name2%", targetName)
								 .replace("%actiontype%", action)
								 .replace("%monstertype%", type)
			);
		}
	}

	
	public void onHowManyAbilitiesLeft(byte[] command) {
		game.setAbilitiesLeft(command[1]-1);
	}
	
	public void onTellLastWill(byte[] command) {
		Object color = ANSI.RESET;
		boolean hasWill = false;
		String key = "";
		switch(command[2]) {
		case 1:  key = "GUI_FOUND_NO_WILL"; break;
		case 2:  key = "GUI_FOUND_WILL"; hasWill = true; break;
		case 3:  key = "GUI_FOUND_BLOOD_COVERED_WILL"; color = ANSI.RED; break;
		default: key = "GUI_ERROR"; hasWill = true; break;
		}
		StringTableMessage msg = Game.STRING_TABLE.get(key);
		System.out.println(color+msg.getText());
		if(hasWill) {
			String will = new String(Arrays.copyOfRange(command, 3, command.length-1), Charset.forName("UTF-8")).replace((char)0x0d, '\n');
			System.out.printf("%s%s%s\n", ANSI.RESET, Game.insertColors(will, game), ANSI.GRAY);
		}
		System.out.println();
	}

	
	public void onMediumCommunicating(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_YOU_ARE_TALKING_TO_THE_LIVING");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.BLACK),
				ANSI.CYAN,
				msg.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onMediumIsTalkingToUs(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_MEDIUM_IS_TALKING_TO_YOU");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.BLACK),
				ANSI.CYAN,
				msg.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onDisguiserChangedUpdateMafia(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onDisguiserChangedIdentity(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onDisguiserStoleYourIdentity(byte[] command) {
		onUnhandledCommand(command);
		
	}

	// Unneeded, we don't track votes (yet?)
	public void onMayorRevealedAndAlreadyVoted(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onMayorRevealed(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_XMAYOR_REVEALED");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.RESET,
				msg.getText().replace("%name%", game.getPlayer(command[1]).getName()+ANSI.toTrueColor(msg.getColor())),
				ANSI.GRAY
		);
		game.getPlayer(command[1]).setRole(Game.ROLE_TABLE.get("Mayor"));
	}

	public void onJesterCompletedGoal(byte[] command) {
		//onUnhandledCommand(command);
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_JESTER_COMPLETED_GOAL");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN,
				msg.getText(),
				ANSI.GRAY
		);
		List<Player> targets = new ArrayList<>();
		for(int i = 1; i < command.length-1; i++) {
			targets.add(game.getPlayer(command[i]));
		}
		
		// See: https://www.blankmediagames.com/phpbb/viewtopic.php?f=10&t=110851
		// Sorting here in the meanwhile to prevent abuse
		// Plus, it looks better this way
		targets.sort((a,b) -> a.getPosition()-b.getPosition());
		
		System.out.printf("%sAvailable Jester targets:\n", ANSI.RESET);
		for(Player player : targets) {
			System.out.printf(
					"%s(%02d) %-16s - %-35s",
					ANSI.RESET,
					player.getPosition(), 
					player, 
					player.getLatestJudgementVote()
			);
			if(player.getRole() != null) {
				System.out.printf(
						"(%s%s%s)",
						ANSI.toTrueColor(player.getRole().getColor()),
						player.getRole().getName(),
						ANSI.RESET
				);
			}
			System.out.println(ANSI.GRAY);
		}
	}

	
	public void onExecutionerCompletedGoal(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_EXECUTIONER_COMPLETED_GOAL");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN,
				msg.getText(),
				ANSI.GRAY
		);
	}

	
	public void onTellJudgementVotes(byte[] command) {
		StringTableMessage verdict, vote = Game.STRING_TABLE.get("GUI_JUDGEMENT_VOTED");
		Player player = game.getPlayer(command[1]);
		String msgID = "GUI_JUDGEMENT_";
		boolean abstained = false;
		Color color = Color.GRAY;
		switch(command[2]) {
		case 1: color = Color.RED; msgID += "VOTED_GUILTY"; break;
		case 2: color = Color.GREEN; msgID += "VOTED_INNOCENT"; break;
		case 3: color = Color.CYAN; msgID += "ABSTAINED"; abstained = true; break;
		}
		verdict = Game.STRING_TABLE.get(msgID);
		if(abstained) {
			player.setLatestJudgementVote(ANSI.toTrueColor(color) + verdict.getText() + ANSI.RESET);
		}
		else {
			player.setLatestJudgementVote(vote.getText() + " " + ANSI.toTrueColor(color) + verdict.getText() + ANSI.RESET);
		}
		System.out.printf(
				"%s%s%s %s%s\n",
				ANSI.RESET,
				player.getName(),
				ANSI.GREEN,
				player.getLatestJudgementVote(),
				ANSI.GRAY
		);
	}

	
	public void onUserCanceledJudgementVote(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_XJUDGEMENT_CANCELLED");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN, 
				msg.getText().replace("%name%", ANSI.RESET + game.getPlayer(command[1]).getName() + ANSI.GREEN), 
				ANSI.GRAY
		);
	}

	
	public void onUserChangedJudgementVote(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_XJUDGEMENT_CHANGED_VOTE");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN, 
				msg.getText().replace("%name%", ANSI.RESET + game.getPlayer(command[1]).getName() + ANSI.GREEN), 
				ANSI.GRAY
		);
	}

	
	public void onUserJudgementVoted(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_XJUDGEMENT_HAS_VOTED");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN, 
				msg.getText().replace("%name%", ANSI.RESET + game.getPlayer(command[1]).getName() + ANSI.GREEN), 
				ANSI.GRAY
		);
	}

	
	public void onJailedTarget(byte[] command) {
		Player jailee = game.getPlayer(command[1]);
		System.out.printf(
				"%s%sYou hauled %s off to jail!%s\n", 
				ANSI.toTrueColorBackground(Color.LIGHT_GRAY), 
				ANSI.WHITE,
				jailee,
				ANSI.RESET, 
				ANSI.GRAY
		);
	}

	
	public void onBeingJailed(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_YOU_WERE_JAILED");
		System.out.printf(
				"%s%s%s%s\n", 
				ANSI.toTrueColorBackground(Color.LIGHT_GRAY), 
				ANSI.WHITE,
				msg.getText(),
				ANSI.RESET, 
				ANSI.GRAY
		);
	}

	
	public void onStartFirstDay(byte[] command) {
		game.setPhase(GamePhase.FIRST_DAY);
		/*
		for(Player player : game.getPlayers()) {
			if(player == null) continue;
			System.out.printf("%s%-20s %s%s\n", ANSI.RESET, player, player.getCharacter(), ANSI.GRAY);
		}*/
	}

	
	public void onBroughtBackToLife(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_YOU_WERE_RESURRECTED");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN,
				msg.getText(),
				ANSI.GRAY
		);
	}

	
	public void onAmnesiacChangedRole(byte[] command) {
		game.getSelfPlayer().setRole(Game.ROLES[(command[1]&0xff)-1]);
	}

	
	public void onTellTownAmnesiacChangedRole(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_AMNESIAC_REMEMBERED_ROLE_X");
		Role role = Game.ROLES[(command[1]&0xff)-1];
		
		String text = msg.getText().replace("%role%", ANSI.toTrueColor(role.getColor())+role.getName()+ANSI.RESET);
		
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				text,
				ANSI.GRAY
		);
	}

	
	public void onOtherMafia(byte[] command) {
		//onUnhandledCommand(command);
		this.tellFactionMembers(command, Game.FACTION_TABLE.get("Mafia"));
	}

	
	public void onUserChosenName(byte[] command) {
		//onUnhandledCommand(command);
		String name = new String(Arrays.copyOfRange(command, 3, command.length-1), Charset.forName("UTF-8"));
		game.updatePlayerName(name, command[2]);
		System.out.printf("%s%s (%d) has joined the Town%s\n", ANSI.GREEN, name, command[2], ANSI.GRAY);
	}

	
	public void onTellRoleList(byte[] command) {
		System.out.println("Role List:");
		Color color;
		Role role;
		List<Role> rolelist = new ArrayList<>();
		for(int i = 1; i < command.length-1; i++) {
			role = Game.ROLES[command[i]-1];
			color = role.getColor();
			rolelist.add(role);
			System.out.printf("%s%s%s\n", ANSI.toTrueColor(color), role.getName(), ANSI.GRAY);
		}
		game.setRoleList(rolelist);
	}

	
	public void onResurrection(byte[] command) {
		Player player = game.getPlayer(command[1]);
		String nameFormatted = player+" ("+ANSI.toTrueColor(player.getRole().getColor())+player.getRole().getName()+ANSI.RESET+")";
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_WAS_BROUGHT_BACK_TO_LIFE");
		System.out.printf(
				"%s%s%s\n", 
				ANSI.RESET,
				msg.getText().replace("%name%", nameFormatted),
				ANSI.GRAY
		);
	}

	
	public void onUserDied(byte[] command) {
		game.getSelfPlayer().kill();
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_YOU_HAVE_DIED");
		System.out.printf(
				"%s%s%s%s%s\n",
				ANSI.toTrueColorBackground(Color.RED),
				ANSI.WHITE,
				msg.getText(),
				ANSI.RESET,
				ANSI.GRAY
		);
	}

	
	public void onUserChangedVote(byte[] command) {
		//onUnhandledCommand(command);
		Player voter = game.getPlayer(command[1]), voted = game.getPlayer(command[2]);
		System.out.printf(
				"%s%s%s has changed their vote to %s%s (Vote Worth: %d)%s\n",
				ANSI.RESET,
				voter,
				ANSI.GREEN,
				ANSI.RESET,
				voted,
				command[4],
				ANSI.GRAY
		);
	}

	
	public void onUserCanceledVote(byte[] command) {
		System.out.printf(
				"%s%s%s has canceled their vote%s\n",
				ANSI.RESET,
				game.getPlayer(command[1]),
				ANSI.GREEN,
				ANSI.GRAY
		);
	}

	
	public void onUserVoted(byte[] command) {
		Player voter = game.getPlayer(command[1]), voted = game.getPlayer(command[2]);
		System.out.printf(
				"%s%s%s has voted against %s%s (Vote Worth: %d)%s\n",
				ANSI.RESET,
				voter,
				ANSI.GREEN,
				ANSI.RESET,
				voted,
				command[3],
				ANSI.GRAY
		);
	}

	
	public void onLookoutNightAbilityMessage(byte[] command) {
		//onUnhandledCommand(command);
		Player player = game.getPlayer(command[1]);
		if(player == Player.getAnonymousPlayer()) {
			StringTableMessage msg = Game.STRING_TABLE.get("GUI_MULTI_VISITED_YOUR_TARGET");
			System.out.printf("%s%s%s\n", ANSI.GREEN, msg.getText(), ANSI.GRAY);
		}
		else {
			System.out.printf(
					"%s%s%s visited %s%s%s last night%s\n",
					ANSI.RESET,
					player,
					ANSI.GREEN,
					ANSI.RESET,
					game.getSelfPlayer().getTarget(),
					ANSI.GREEN,
					ANSI.GRAY
			);
		}
	}

	
	public void onTrialFoundNotGuilty(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_TOWN_DECIDED_TO_PARDON_THEM");
		game.setPhase(GamePhase.DEEMED_NOT_GUILTY);
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				msg.getText().replace("%name%", game.getPlayerOnTrial().getName())
							 .replace("%x%", ANSI.RED + String.valueOf(command[1]-1) + ANSI.GREEN)
							 .replace("%y%", ANSI.TOWN + String.valueOf(command[2]-1) + ANSI.GREEN),
				ANSI.GRAY
		);
		game.clearPlayerOnTrial();
	}

	
	public void onTrialFoundGuilty(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_TOWN_DECIDED_TO_LYNCH_THEM");
		game.setPhase(GamePhase.DEEMED_GUILTY);
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				msg.getText().replace("%name%", game.getPlayerOnTrial().getName())
							 .replace("%x%", ANSI.RED + String.valueOf(command[1]-1) + ANSI.GREEN)
							 .replace("%y%", ANSI.TOWN + String.valueOf(command[2]-1) + ANSI.GREEN),
				ANSI.GRAY
		);
	}

	
	public void onStartJudgement(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_TOWN_MAY_VOTE_ON_THEM");
		game.setPhase(GamePhase.JUDGEMENT);
		System.out.printf(
				"%s%s%s\n", 
				ANSI.GREEN, 
				msg.getText().replace("%name%",ANSI.RESET + game.getPlayerOnTrial().getName() + ANSI.GREEN), 
				ANSI.GRAY
		);
		System.out.println("---------Judgement---------");
	}
	
	public void onStartDefenseTransition(byte[] command) {
		StringTableMessage msg = Game.STRING_TABLE.get("GUI_TOWN_PUT_THEM_ON_TRIAL");
		game.placePlayerOnTrial(command[1]);
		System.out.printf(
				"%s%s%s\n",
				ANSI.GREEN,
				msg.getText().replace("%name%",ANSI.RESET + game.getPlayerOnTrial().getName() + ANSI.GREEN),
				ANSI.GRAY
		);
	}

	
	public void onStartVoting(byte[] command) {
		game.setPhase(GamePhase.VOTING);
		System.out.println(ANSI.GRAY+"----------Voting-----------");
	}

	
	public void onStartDiscussion(byte[] command) {
		game.setPhase(GamePhase.DISCUSSION);
		System.out.println(ANSI.GRAY+"--------Discussion---------");
		String msg = game.getDayAbilitiesLeftMessage();
		if(msg != null) {
			System.out.println(msg);
		}
	}

	
	public void onWhoDiedAndHow(byte[] command) {
		//System.out.println(ANSI.RESET+"-----------Death-----------");
		//this.onUnhandledCommand(command);
		int roleID = (command[2]&0xff)-1;
		Role role = Game.ROLE_ID_TABLE.get(roleID);
		Player player = game.getPlayer(command[1]);
		
		String color = String.valueOf(
				player.getTags().contains(PlayerTag.TRAITOR) ? (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) : ANSI.toTrueColor(role.getColor())
		);
		
		List<Killer> killers = new ArrayList<>();
		Killer[] oldKillers = player.getKillers();
		for(int i = 4; i < command.length-1; i++) {
			killers.add(Game.KILLERS[command[i]-1]);
		}
		player.kill(killers.toArray(new Killer[0]));
		for(Killer k : oldKillers) {
			killers.add(k);
		}
		if(args != null && args.useFancyDeaths() && !killers.contains(Killer.LYNCHING)) {
			int which = Math.min(killers.size(), 3), subID = 2;
			StringTableMessage fluff = Game.STRING_TABLE.get(killers.size() == 1 && killers.get(0).equals(Game.KILLERS[5]) ? "GUI_DEAD_FLUFF00" : "GUI_DEAD_FLUFF0"+which);
			System.out.printf("\n%s%s\n", ANSI.RESET, fluff.getText().replace("%name%", player.getName()));
			String gender = player.getCharacter() == null ? "He/She" : player.getCharacter().isMale() ? "He" : "She";
			for(Killer killer : killers) {
				fluff = Game.STRING_TABLE.get("GUI_XDIED_REASON"+killer.getID()+"_"+subID);
				System.out.printf(
						"%s%s\n", 
						ANSI.RESET,
						fluff.getText().replace("%gender%", gender).replace("%role%", ANSI.toTrueColor(killer.getColor())+killer.getName()+ANSI.RESET)
				);
				subID = 1;
			}
		}
		else {
			String killersStr = killers.stream()
					   .map(k -> String.format("%s%s%s", ANSI.toTrueColor(k.getColor()), k.getName(), ANSI.RESET))
					   .collect(Collectors.joining(", "));
			System.out.printf("\n%s%s (%d) was killed%s%s\n", ANSI.RESET, player, command[1], killers.size() > 0 ? " by " : "", killersStr);
		}
		
		// I'm sure there's a message for this but I can't be bothered to find it
		Player self = game.getSelfPlayer();
		if(role != null && killers.contains(Game.KILLERS[0]) && !self.getTags().contains(PlayerTag.TRAITOR) && self.getRole().equals(Game.ROLE_TABLE.get("Jailor"))) {
			if(role.getFaction() != null && role.getFaction().equals(Game.FACTION_TABLE.get("Town")) && !player.getTags().contains(PlayerTag.TRAITOR)) {
				self.addTag(PlayerTag.KILLED_TOWN);
			}
		}
		StringTableMessage roleMsg;
		if(role == null) {
			System.out.printf("We could not determine their role (Unknown role: %d)%s\n", roleID, ANSI.GRAY);
		}
		else if (role.getPlayerTags().contains(PlayerTag.STONED) || role.getPlayerTags().contains(PlayerTag.CLEANED)) {
			roleMsg = Game.STRING_TABLE.get("GUI_WE_COULD_NOT_DETERMINE_XROLE");
			System.out.printf(
					"%s (%s%s%s)%s\n",
					roleMsg.getText().replace("%name%", player.getName()),
					color,
					role.getPlayerTags().stream().map(String::valueOf).collect(Collectors.joining(", ")),
					ANSI.RESET,
					ANSI.GRAY
			);
			if(player.getRole() == null) {
				player.setRole(role);
			}
		}
		else {
			roleMsg = Game.STRING_TABLE.get("GUI_XROLE_WAS_Y");
			StringTableMessage disguise = Game.STRING_TABLE.get("GUI_ROLE_ABILITY_VERB_18");
			String message = roleMsg.getText().replace("%name%", player.getName());
			Role knownRole = player.getRole();
			
			boolean possibleConvert = knownRole != null && Game.FACTION_TABLE.get("Town").equals(knownRole.getFaction()) && role.equals(Game.ROLE_TABLE.get("Vampire"));
			if(!possibleConvert && knownRole != null && !role.equals(knownRole) && !knownRole.equals(Game.ROLE_TABLE.get("Random Town"))) {
				message = message.replace("%role%", ANSI.toTrueColor(knownRole.getColor())+knownRole.getName()+ANSI.RESET);
				message += String.format(
						" (%s: %s%s%s)",
						disguise.getText(),
						ANSI.toTrueColor(role.getColor()),
						role.getName(),
						ANSI.RESET
				);
			}
			else {
				message = message.replace("%role%", color+role.getName()+ANSI.RESET);
				player.setRole(role);
			}
			System.out.println(message);
			System.out.println();
		}
		if(role != null) {
			role.getPlayerTags().forEach(player::addTag);
		}
	}

	
	public void onStartDay(byte[] command) {
		game.setPhase(GamePhase.DAY_TRANSITION);
		Set<RoleTag> tags = game.getSelfPlayer().getRole().getRoleTags();
		if(tags.contains(RoleTag.DAY_EXPANDED_ABILITY) || tags.contains(RoleTag.POST_DEATH_DAY_EXPANDED_ABILITY) || tags.contains(RoleTag.POST_DEATH_DAY_ABILITY) || tags.contains(RoleTag.DAY_ABILITY)) {
			game.getSelfPlayer().setTarget(null);
		}
		System.out.println(ANSI.GRAY+"------------Day------------");
	}

	
	public void onStartNight(byte[] command) {
		Player self = game.getSelfPlayer();
		game.setPhase(GamePhase.NIGHT);
		Set<RoleTag> tags = self.getRole().getRoleTags();
		if(!(tags.contains(RoleTag.DAY_EXPANDED_ABILITY) || tags.contains(RoleTag.POST_DEATH_DAY_EXPANDED_ABILITY) || tags.contains(RoleTag.POST_DEATH_DAY_ABILITY) || tags.contains(RoleTag.DAY_ABILITY))) {
			self.setTarget(null);
		}
		System.out.println(ANSI.GRAY+"-----------Night-----------");
		String msg = game.getNightAbilitiesLeftMessage();
		if(msg != null) {
			System.out.println(msg);
			
			if(self.getTags().contains(PlayerTag.KILLED_TOWN)) {
				StringTableMessage message = null;
				if(self.getRole().equals(Game.ROLE_TABLE.get("Vigilante"))) {
					message = Game.STRING_TABLE.get("GUI_VIGILANTE_KILLED_TOWN");
					self.getTags().remove(PlayerTag.KILLED_TOWN);
				}
				else if(self.getRole().equals(Game.ROLE_TABLE.get("Jailor"))) {
					message = Game.STRING_TABLE.get("GUI_JAILOR_CANT_KILL_AGAIN");
				}
				if(message != null) {
					System.out.printf(
							"%s%s%s%s%s\n",
							ANSI.toTrueColorBackground(message.getColor()),
							ANSI.WHITE,
							message.getText(),
							ANSI.RESET,
							ANSI.GRAY
					);
				}
			}
		}
	}

	
	public void onRoleAndPosition(byte[] command) {
		Role role = Game.ROLES[command[1]-1];
		Player self = game.getPlayer(command[2]);
		game.setSelfPosition(command[2]);
		self.setRole(role);
		System.out.printf("%sRole: %s%s%s\nPosition: %d%s\n", ANSI.RESET, ANSI.toTrueColor(role.getColor()), role.getName(), ANSI.RESET, command[2], ANSI.GRAY);
		if(command.length > 4) {
			Player target = game.getPlayer(command[3]);
			target.addTag(PlayerTag.TARGET);
			System.out.printf("%sTarget: %s (%02d)%s\n", ANSI.RESET, target, command[3], ANSI.GRAY);
			
			if(role.equals(Game.ROLE_TABLE.get("Executioner"))) {
				target.setRole(Game.ROLE_TABLE.get("Random Town"));
			}
		}
	}

	
	public void onNamesAndPositionsOfUsers(byte[] command) {
		game.updatePlayerName(new String(Arrays.copyOfRange(command, 2, command.length-1), Charset.forName("UTF-8")), command[1]);
	}

	
	public void onPickNames(byte[] command) {
		game.setPhase(GamePhase.PICK_NAME);
		for(int i = 1; i <= 15; i++) {
			game.updatePlayerName("Player "+i, i);
		}
		System.out.println("Name selection has begun");
	}

	
	public void onCurrencyMultiplier(byte[] command) {
		onUnhandledCommand(command);
	}

	
	public void onPromotionPopup(byte[] command) {
		onUnhandledCommand(command);
	}

	
	public void onScrollConsumed(byte[] command) {
		//onUnhandledCommand(command);
		Scroll scroll = Game.SCROLLS[Integer.parseInt(new String(Arrays.copyOfRange(command, 1, command.length-1), Charset.forName("UTF-8")))];
		System.out.printf("%sScroll used: %s%s\n", ANSI.RESET, Game.insertColors(scroll.toString(), game), ANSI.GRAY);
	}

	
	public void onPlayerStatistics(byte[] command) {
		onUnhandledCommand(command);
	}

	
	public void onReferAFriendUpdate(byte[] command) {
		//onUnhandledCommand(command);
	}

	
	public void onModeratorMessage(byte[] command) {
		onUnhandledCommand(command);
		StringTableMessage msg = Game.STRING_TABLE.get("MOD_"+command[1]);
		System.out.printf(
				"%s%s%s\n", 
				ANSI.YELLOW, 
				msg.getText().replaceAll("\\{.{0,}\\}", new String(Arrays.copyOfRange(command, 2, command.length-1), Charset.forName("UTF-8"))),
				ANSI.GRAY
		);
	}

	
	public void onUserStatistics(byte[] command) {
		onUnhandledCommand(command);
	}

	
	public void onUpdateFriendName(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onSetLastBonusWinTime(byte[] command) {
		//onUnhandledCommand(command);
	}

	
	public void onUpdatePaidCurrency(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onShopPurchaseSuccess(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onReturnToHomePage(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onForcedLogout(byte[] command) {
		onUnhandledCommand(command);
		
	}

	
	public void onFriendUpdate(byte[] command) {
		//onUnhandledCommand(command);
		
	}

	
	public void onStringTableMessage(byte[] command) {
		String id = String.valueOf((command[1]&0xff)-1);
		if(game.getMode().isCovenGamemode() && Game.STRING_TABLE.containsKey("GAME_COVEN"+id)) {
			id = "COVEN" + id;
		}
		StringTableMessage msg = Game.STRING_TABLE.get("GAME_"+id);
		System.out.printf("%s%s%s%s\n", ANSI.WHITE, ANSI.toTrueColorBackground(msg.getColor()), msg.getText(), ANSI.RESET, ANSI.GRAY);
	}

	
	public void onSystemMessage(byte[] command) {
		StringTableMessage system = Game.STRING_TABLE.get("GUI_SYSTEM_MESSAGE");
		System.out.printf("%s%s: %s%s\n", ANSI.YELLOW, system.getText(), this.convertToString(command), ANSI.RESET);
	}

	
	public void onDoNotSpam(byte[] command) {
		System.out.println(ANSI.YELLOW+"Please do not spam the chat"+ANSI.GRAY);
	}

	
	public void onChatBoxMessage(byte[] command) {
		int offset = command[1] == (byte)0xff ? 1 : 0;
		int position = command[1+offset];
		boolean alive = true, isTraitor = false, isVIP = false, isLover = false, isTarget = false;
		
		Player player;
		
		String name, role = " (???)", positionFormatted = "";
		String color = null;
		if(position == 75) {
			color = ANSI.VAMPIRE.toString();
			name = ANSI.VAMPIRE+"Vampire";
			role = "";
		}
		else if(position == 45) {
			color = ANSI.CYAN.toString();
			name = ANSI.CYAN+"Medium";
			role = "";
		}
		else if(position == 30) {
			color = ANSI.YELLOW.toString();
			name = ANSI.YELLOW+"Jailor";
			role = "";
		}
		else {
			positionFormatted = String.format("(%02d) ", position);
			player = game.getPlayer(position);
			
			// Due to the rolelists for VIP, Town Traitor, Lovers mode, only 1 of these tags can be assigned at a time
			// Even though VIP includes a GA which has a target, they cannot see the VIP.
			isTraitor = player.getTags().contains(PlayerTag.TRAITOR);
			isTarget = player.getTags().contains(PlayerTag.TARGET);
			isLover = player.getTags().contains(PlayerTag.LOVER);
			isVIP = player.getTags().contains(PlayerTag.VIP);
			
			alive = player.isAlive();
			name = player.getName();
			
			List<String> metadata = new ArrayList<>();
			if(player.getRole() != null) {
				metadata.add((isTraitor ? (game.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA) : ANSI.toTrueColor(player.getRole().getColor()))+player.getRole().getName()+ANSI.RESET);
			}
			if(!player.getTags().isEmpty()) {
				player.getTags().stream()
								.map(t -> {
									try {
										return ANSI.valueOf(t.name())+t.toString()+ANSI.RESET;
									}
									catch(Exception e) {
										return null;
									}
								})
								.filter(t -> t != null)
								.forEach(metadata::add);
									
			}
			if(!metadata.isEmpty()) {
				role = " ("+String.join(", ", metadata)+")";
			}
		}
		if(color == null) {
			color = String.valueOf(isTarget ? ANSI.TARGET : isVIP ? ANSI.VIP : isLover ? ANSI.LOVER : alive ? ANSI.RESET : ANSI.GRAY);
		}
		System.out.printf(
				"%s%s%s%s%s: %s%s%s\n",
				alive ? color : ANSI.RED,
				positionFormatted,
				name, 
				ANSI.RESET, 
				role,
				color,
				Game.insertColors(new String(Arrays.copyOfRange(command, 2+offset, command.length-1), Charset.forName("UTF-8")), color, game), 
				ANSI.GRAY
		);
	}

	
	public void onUserLeftGame(byte[] command) {
		//this.onUnhandledCommand(command);
		System.out.printf(
				"%s%s left the game%s\n", 
				game.getPhase() == GamePhase.PICK_NAME || game.getPhase() == GamePhase.LOBBY ? ANSI.RED : ANSI.YELLOW, 
				game.getPhase() == GamePhase.PICK_NAME ? game.getLobbyUsername(command[3]) : game.getPlayer(command[3]).getName(), 
				ANSI.GRAY
		);
		if(game.getPhase() == GamePhase.LOBBY) {
			for(int i = command[3]; i < 15; i++) {
				game.getPlayers()[i-1] = game.getPlayers()[i];
				game.getLobbyUsernames()[i-1] = game.getLobbyUsernames()[i];
			}
			game.getLobbyUsernames()[14] = null;
			game.getPlayers()[14] = null;
		}
	}

	
	public void onUsersJoinedLobby(byte[] command) {
		int asterisk = this.indexOf(command, (byte)'*');
		String name = new String(Arrays.copyOfRange(command, 3, asterisk), Charset.forName("UTF-8"));
		System.out.printf(
				"%sPlayer #%d (%s) joined the game%s\n",
				ANSI.GRAY,
				command[asterisk+1], 
				name,
				command[1] == 0x02 ? " (Host)" : ""
		);
		game.updatePlayerName(name, command[asterisk+1]);
		game.updateLobbyName(name, command[asterisk+1]);
	}

	
	public void onJoinedGameLobby(byte[] command) {
		game.setPhase(GamePhase.LOBBY);
		game.setMode(Game.GAME_MODE_ID_TABLE.get((int)command[2]));
		System.out.printf("%sJoined game lobby (Game Mode: %s)%s\n", ANSI.GREEN, game.getMode().getLabel().getText(), ANSI.GRAY);
		System.out.printf("%sSummary: %s%s\n", ANSI.GREEN, game.getMode().getSummary().getText(), ANSI.GRAY);
	}

	
	public void onLoginSuccess(byte[] command) {
		System.out.println(ANSI.RESET+"Log in succeeded"+ANSI.GRAY);
		//onUnhandledCommand(command);
	}
	
	private void tellFactionMembers(byte[] command, Faction faction) {
		System.out.printf("%s%s%s Members:\n", ANSI.valueOf(faction.getName().toUpperCase()), faction.getName(), ANSI.RESET);
		Player member;
		Role role;
		for(int i = 1; i < command.length-1; i+=2) {
			member = game.getPlayer(command[i]);
			role = Game.ROLES[command[i+1]-1];
			member.setRole(role);
			System.out.printf("%s (%s%s%s)\n", member, ANSI.toTrueColor(role.getColor()), role.getName(), ANSI.RESET);
		}
		System.out.print(ANSI.GRAY);
	}
}
