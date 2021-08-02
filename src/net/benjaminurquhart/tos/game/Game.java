package net.benjaminurquhart.tos.game;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import net.benjaminurquhart.tos.game.entities.*;

public class Game {
	
	public static Map<String, StringTableMessage> STRING_TABLE;
	public static Map<String, List<Role>> ROLE_LIST_TABLE;
	public static Map<String, GameMode> GAME_MODE_TABLE;
	public static Map<String, Faction> FACTION_TABLE;
	public static Map<String, Genre> GENRE_TABLE;
	public static Map<String, Role> ROLE_TABLE;
	
	public static Map<String, List<String>> ROLE_ABBREVIATIONS;
	public static Map<Role, List<String>> COMMON_ACTIONS;
	
	public static Map<Integer, GameMode> GAME_MODE_ID_TABLE;
	public static Map<Integer, Role> ROLE_ID_TABLE;
	
	public static CharacterSkin[] CHARACTERS;
	public static Achievement[] ACHIEVEMENTS;
	public static Faction[] FACTIONS;
	public static Killer[] KILLERS;
	public static Scroll[] SCROLLS;
	public static Winner[] WINNERS;
	public static String[] TAUNTS;
	public static Role[] ROLES;
	
	private static Map<String, String> REPLACEMENT_CACHE = new HashMap<>();
	private static Map<String, Pattern> REGEX_CACHE = new HashMap<>();

	static {
		try {
			JSONObject json = new JSONObject(Game.load("/GameRules.json")), tmp;
			JSONArray factions = json.getJSONArray("Factions");
			FACTIONS = new Faction[factions.length()];
			FACTION_TABLE = new HashMap<>();
			for(int i = 0, length = factions.length(); i < length; i++) {
				FACTIONS[i] = new Faction(factions.getJSONObject(i));
				FACTION_TABLE.put(FACTIONS[i].getName(), FACTIONS[i]);
			}
			JSONArray roles = json.getJSONArray("Roles");
			ROLES = new Role[roles.length()];
			ROLE_ID_TABLE = new HashMap<>();
			ROLE_TABLE = new HashMap<>();
			Role role;
			for(int i = 0, length = roles.length(); i < length; i++) {
				tmp = roles.getJSONObject(i);
				//System.err.println(tmp);
				if(!tmp.has("Type")) {
					role = new RandomRole(tmp);
				}
				else {
					role = new Role(tmp);
				}
				ROLES[i] = role;
				ROLE_TABLE.put(role.getName(), role);
				ROLE_ID_TABLE.put(role.getID(), role);
			}
			JSONArray killers = json.getJSONArray("Killers");
			KILLERS = new Killer[killers.length()+1];
			for(int i = 0, length = killers.length(); i < length; i++) {
				KILLERS[i] = new Killer(killers.getJSONObject(i));
			}
			KILLERS[KILLERS.length-1] = Killer.LYNCHING;
			JSONArray gamemodes = json.getJSONArray("Modes");
			GAME_MODE_ID_TABLE = new HashMap<>();
			GAME_MODE_TABLE = new HashMap<>();
			GameMode mode;
			for(int i = 0, length = gamemodes.length(); i < length; i++) {
				tmp = gamemodes.getJSONObject(i);
				if(tmp.get("id") instanceof JSONArray) {
					tmp.put("id", tmp.getJSONArray("id").getString(0));
				}
				mode = new GameMode(tmp);
				GAME_MODE_ID_TABLE.put(mode.getID(), mode);
				GAME_MODE_TABLE.put(mode.getName(), mode);
			}
			JSONArray winners = json.getJSONArray("WinningGroups");
			WINNERS = new Winner[winners.length()];
			for(int i = 0, length = winners.length(); i < length; i++) {
				WINNERS[i] = new Winner(winners.getJSONObject(i));
			}
			JSONArray rolelists = json.getJSONArray("RoleLists");
			ROLE_LIST_TABLE = new HashMap<>();
			for(int i = 0, length = rolelists.length(); i < length; i++) {
				tmp = rolelists.getJSONObject(i);
				ROLE_LIST_TABLE.put(
						tmp.getString("id"), 
						tmp.getJSONArray("Role").toList()
												.stream()
												.map(Map.class::cast)
												.map(m -> (String)m.get("id"))
												.map(Integer::parseInt)
												.map(ROLE_ID_TABLE::get)
												.collect(Collectors.toList())
				);
			}
			json = new JSONObject(Game.load("/Customization.json"));
			JSONArray scrolls = json.getJSONArray("CustomizationScrolls");
			SCROLLS = new Scroll[scrolls.length()];
			for(int i = 0, length = scrolls.length(); i < length; i++) {
				tmp = scrolls.getJSONObject(i);
				if(!tmp.has("Role")) {
					SCROLLS[i] = new NoScroll(tmp);
				}
				else {
					SCROLLS[i] = new Scroll(tmp);
				}
			}
			JSONArray taunts = json.getJSONArray("CustomizationTaunts");
			TAUNTS = new String[taunts.length()];
			for(int i = 0, length = taunts.length(); i < length; i++) {
				tmp = taunts.getJSONObject(i);
				TAUNTS[i] = tmp.getJSONObject("Name").getString("text");
			}
			JSONArray characters = new JSONObject(Game.load("/Characters.json")).getJSONArray("Character");
			CHARACTERS = new CharacterSkin[characters.length()];
			for(int i = 0; i < CHARACTERS.length; i++) {
				CHARACTERS[i] = new CharacterSkin(characters.getJSONObject(i));
			}
			JSONObject table = new JSONObject(Game.load("/StringTable.json"));
			STRING_TABLE = new HashMap<>();
			for(String key : table.keySet()) {
				STRING_TABLE.put(key, new StringTableMessage(key, table.getJSONObject(key)));
			}
			tmp = new JSONObject(Game.load("/Achievements.json"));
			JSONArray achievements = tmp.getJSONArray("Achievement");
			ACHIEVEMENTS = new Achievement[achievements.length()];
			for(int i = 0, length = achievements.length(); i < length; i++) {
				ACHIEVEMENTS[i] = new Achievement(achievements.getJSONObject(i));
			}
			JSONArray genres = tmp.getJSONArray("Genre");
			GENRE_TABLE = new HashMap<>();
			Genre genre;
			for(int i = 0, length = genres.length(); i < length; i++) {
				genre = new Genre(genres.getJSONObject(i));
				GENRE_TABLE.put(genre.getID(), genre);
			}
			final JSONObject abbreviationJSON = new JSONObject(Game.load("/RoleAliases.json"));
			ROLE_ABBREVIATIONS = abbreviationJSON.keySet()
												 .stream()
												 .collect(Collectors.toMap(key -> key, key -> {
													 JSONArray abbreviations = abbreviationJSON.getJSONArray((String)key);
													 return abbreviations.toList().stream().map(String::valueOf).collect(Collectors.toList());
												 }));
			final JSONObject commonActionJSON = new JSONObject(Game.load("/CommonActions.json"));
			COMMON_ACTIONS = new HashMap<>();
			
			JSONObject definitions = commonActionJSON.getJSONObject("definitions");
			commonActionJSON.remove("definitions");
			
			List<String> actions;
			
			for(String key : commonActionJSON.keySet()) {
				role = Game.ROLE_ID_TABLE.get(definitions.get(key));
				if(role == null) {
					continue;
				}
				actions = commonActionJSON.getJSONArray(key).toList().stream().map(String::valueOf).collect(Collectors.toList());
				COMMON_ACTIONS.put(role, actions);
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public String insertColors(String text) {
		return insertColors(text, ANSI.RESET, this);
	}
	public String insertColors(String text, ANSI defaultColor) {
		return insertColors(text, defaultColor.toString(), this);
	}
	public String insertColors(String text, Color defaultColor) {
		return insertColors(text, ANSI.toTrueColor(defaultColor), this);
	}
	public static String insertColors(String text, Game ctx) {
		return insertColors(text, ANSI.RESET, ctx);
	}
	public static String insertColors(String text, ANSI defaultColor, Game ctx) {
		return insertColors(text, defaultColor.toString(), ctx);
	}
	public static String insertColors(String text, Color defaultColor, Game ctx) {
		return insertColors(text, ANSI.toTrueColor(defaultColor), ctx);
	}
	public static String insertColors(String text, String defaultColor, Game ctx) {
		Pattern pattern;
		String match;
		for(Role role : ROLES) {
			for(String abbreviation : ROLE_ABBREVIATIONS.computeIfAbsent(role.getName(), n -> new ArrayList<>())) {
				pattern = Game.getPatternFor(abbreviation);
				match = String.format(Game.getReplacementFor(abbreviation, ANSI.toTrueColor(role.getColor())), defaultColor);
				text = pattern.matcher(text).replaceAll(match);
				
			}
			if(COMMON_ACTIONS.containsKey(role)) {
				for(String action : COMMON_ACTIONS.get(role)) {
					pattern = Game.getPatternFor(action);
					match = String.format(Game.getReplacementFor(action, ANSI.toTrueColor(role.getColor())), defaultColor);
					text = pattern.matcher(text).replaceAll(match);
				}
			}
			pattern = Game.getPatternFor(role.getName());
			match = String.format(Game.getReplacementFor(role.getName(), ANSI.toTrueColor(role.getColor())), defaultColor);
			text = pattern.matcher(text).replaceAll(match);
		}
		for(Faction faction : FACTIONS) {
			pattern = Game.getPatternFor(faction.getName());
			match = String.format(Game.getReplacementFor(faction.getName(), ANSI.valueOf(faction.getName().toUpperCase())), defaultColor);
			text = pattern.matcher(text).replaceAll(match);
		}
		
		ANSI evil = ctx != null && ctx.mode != null && ctx.getMode().isCovenGamemode() ? ANSI.COVEN : ANSI.MAFIA;
		// Not using getMode() here because it will trigger the game mode auto-detection when used outside of a game
		if(ctx != null && ctx.mode != null && ctx.mode.getName().contains("Traitor")) {
			
			pattern = Game.getPatternFor("Town Traitor");
			match = String.format(Game.getReplacementFor("Town Traitor", evil), defaultColor);
			text = pattern.matcher(text).replaceAll(match);
			
			pattern = Game.getPatternFor("TT");
			match = String.format(Game.getReplacementFor("TT", evil), defaultColor);
			text = pattern.matcher(text).replaceAll(match);
		}
		
		pattern = Game.getPatternFor("sus");
		match = String.format(Game.getReplacementFor("sus", evil), defaultColor);
		text = pattern.matcher(text).replaceAll(match);
		
		pattern = Game.getPatternFor("suspicious");
		match = String.format(Game.getReplacementFor("suspicious", evil), defaultColor);
		text = pattern.matcher(text).replaceAll(match);
		
		return text;
	}
	private static String load(String path) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream stream = Game.class.getResourceAsStream(path);
		byte[] buff = new byte[1024];
		int read;
		while((read = stream.read(buff)) != -1) {
			if(read == 0) continue;
			sb.append(new String(Arrays.copyOfRange(buff, 0, read)));
		}
		stream.close();
		return sb.toString();
	}
	private static Pattern getPatternFor(String text) {
		return REGEX_CACHE.computeIfAbsent(text, t -> Pattern.compile("(?i)(^|\\s|\\p{P}|=)((?:"+Pattern.quote(t)+"s?)+)(\\s|$|\\p{P}|=)"));
	}
	private static String getReplacementFor(String text, Object replacement) {
		return REPLACEMENT_CACHE.computeIfAbsent(text, t -> "$1"+replacement+"$2%s$3");
	}
	
	private List<Role> rolelist;
	private int playerOnTrial;
	
	private Player[] players;
	private int selfPosition;
	
	private String[] lobbyUsernames;
	
	private GamePhase phase;
	private GameMode mode;
	
	private Winner winner;
	
	private int abilitiesLeft;
	
	public Game() {
		this.lobbyUsernames = new String[15];
		this.players = new Player[15];
		this.selfPosition = -1;
	}
	public GameMode getMode() {
		if(mode == null) {
			System.out.printf(
					"%sWarning: This game capture was started late. Make sure you start capturing before you enter a game lobby!\n%sAttempting to infer game mode...\n",
					ANSI.ORANGE,
					ANSI.GRAY
			);
			this.inferModeFromRoleList();
		}
		return mode;
	}
	public String[] getLobbyUsernames() {
		return lobbyUsernames;
	}
	public String getLobbyUsername(int position) {
		return lobbyUsernames[position-1];
	}
	public GamePhase getPhase() {
		return phase;
	}
	public Player[] getPlayers() {
		return players;
	}
	public Player getPlayer(int position) {
		if(position == 101) {
			return Player.getAnonymousPlayer();
		}
		if(players[position-1] == null) {
			this.updatePlayerName("Player "+position, position);
		}
		return players[position-1];
	}
	public Player getSelfPlayer() {
		return players[selfPosition-1];
	}
	public List<Role> getRoleList() {
		return rolelist;
	}
	public Winner getWinner() {
		return winner;
	}
	public void setMode(GameMode mode) {
		if(mode.getName().contains("Custom") && rolelist == null) {
			rolelist = new ArrayList<>();
		}
		this.mode = mode;
	}
	public void setPhase(GamePhase phase) {
		this.phase = phase;
		if(phase == GamePhase.PICK_NAME || phase == GamePhase.LOBBY) {
			for(Player p : players) {
				if(p != null) {
					p.getTags().clear();
					p.setTarget(null);
					p.setRole(null);
					p.resurrect();
					p.lose();
				}
			}
			this.winner = null;
		}
	}
	public void setWinner(Winner winner) {
		this.winner = winner;
	}
	public void setRoleList(List<Role> list) {
		this.rolelist = list;
	}
	public void setSelfPosition(int position) {
		selfPosition = position;
	}
	public void updateLobbyName(String name, int position) {
		lobbyUsernames[position-1] = name;
	}
	public void updatePlayerName(String name, int position) {
		if(players[position-1] != null) {
			players[position-1].setName(name);
		}
		else {
			players[position-1] = new Player(name, position);
		}
	}
	
	public void placePlayerOnTrial(int position) {
		playerOnTrial = position;
	}
	public void clearPlayerOnTrial() {
		playerOnTrial = -1;
	}
	public Player getPlayerOnTrial() {
		if(playerOnTrial < 0) {
			return null;
		}
		return players[playerOnTrial-1];
	}
	public void setAbilitiesLeft(int left) {
		abilitiesLeft = left;
	}
	public int getAbilitiesLeft() {
		return abilitiesLeft;
	}
	public void consumeAbility() {
		abilitiesLeft = Math.min(0, abilitiesLeft-1);
	}
	public String getDayAbilitiesLeftMessage() {
		StringTableMessage msg = this.getSelfPlayer().getRole().getDayAbilityUsesLeftMessage(abilitiesLeft);
		if(msg == null) {
			return null;
		}
		return formatMsg(msg);
	}
	public String getNightAbilitiesLeftMessage() {
		StringTableMessage msg = this.getSelfPlayer().getRole().getNightAbilityUsesLeftMessage(abilitiesLeft);
		if(msg == null) {
			return null;
		}
		return formatMsg(msg);
	}
	private String formatMsg(StringTableMessage msg) {
		return String.format(
				"%s%s%s%s", 
				ANSI.WHITE, 
				ANSI.toTrueColorBackground(msg.getColor()), 
				msg.getText().replace("%number%", String.valueOf(abilitiesLeft)), 
				ANSI.RESET, 
				ANSI.GRAY
		);
	}
	private void inferModeFromRoleList() {
		if(rolelist == null) {
			return;
		}
		for(GameMode mode : Game.GAME_MODE_TABLE.values()) {
			if(mode.getRoleList() == null) {
				continue;
			}
			if(mode.getRoleList().containsAll(rolelist)) {
				System.out.printf("%sSuccessfully determined game mode from role list: %s\n ", ANSI.GRAY, mode.getName());
				this.mode = mode;
				return;
			}
		}
		System.out.println(ANSI.GRAY+"Non-standard role list detected!");
		
		// TODO: differentiate between classic and coven custom
		this.mode = Game.GAME_MODE_TABLE.get("Custom");
		System.out.printf("%sBased on the roles in the list, the game mode was inferred to be %s\n", ANSI.GRAY, mode.getName());
	}
}
