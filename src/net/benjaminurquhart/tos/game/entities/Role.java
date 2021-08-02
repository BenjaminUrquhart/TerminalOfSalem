package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import net.benjaminurquhart.tos.game.*;

public class Role {
	
	private static Set<String> unknownPlayerTags = new HashSet<>(), unknownRoleTags = new HashSet<>();

	private Set<PlayerTag> playerTags;
	private Set<RoleTag> roleTags;
	private Faction faction;
	private String type;
	private String name;
	private int id;
	
	private Defense defense;
	private Attack attack;
	
	private Color color;
	
	public Role(JSONObject json) {
		//System.err.println(json);
		if(json.opt("Defense") instanceof String) {
			this.defense = Defense.values()[Integer.parseInt(json.optString("Defense", "0"))];
		}
		else {
			this.defense = Defense.values()[Integer.parseInt(json.getJSONObject("Defense").getString("text"))];
		}
		if(json.opt("Attack") instanceof String) {
			this.attack = Attack.values()[Integer.parseInt(json.optString("Attack", "0"))];
		}
		else {
			this.attack = Attack.values()[Integer.parseInt(json.getJSONObject("Attack").getString("text"))];
		}
		
		this.color = new Color(Integer.parseInt(json.getString("Color").substring(1), 16));
		
		this.faction = json.has("Faction") ? Game.FACTIONS[Integer.parseInt(json.getString("Faction"))-1] : null;
		this.name = json.getJSONObject("Name").getString("text");
		this.type = json.getJSONObject("Type").getString("text");
		this.id = Integer.parseInt(json.getString("id"));
		
		String tag;
		this.playerTags = new HashSet<>();
		if(json.has("PlayerTags")) {
			Collection<Object> tags;
			Object obj = json.get("PlayerTags");
			if(obj instanceof JSONObject) {
				tags = ((JSONObject)obj).toMap().values();
			}
			else {
				tags = json.getJSONArray("PlayerTags").toList();
			}
			for(Object o : tags) {
				tag = String.valueOf(o).replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
				try {
					this.playerTags.add(PlayerTag.valueOf(tag));
				}
				catch(Exception e) {
					if(unknownPlayerTags.add(tag)) {
						System.out.printf("%sUnknown player tag: %s%s\n", ANSI.RED, tag, ANSI.GRAY);
					}
				}
			}
		}
		this.roleTags = new HashSet<>();
		if(json.has("Tags")) {
			Collection<Object> tags;
			Object obj = json.get("Tags");
			if(obj instanceof JSONObject) {
				tags = ((JSONObject)obj).toMap().values();
			}
			else {
				tags = json.getJSONArray("Tags").toList();
			}
			for(Object o : tags) {
				tag = String.valueOf(o).replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
				try {
					this.roleTags.add(RoleTag.valueOf(tag));
				}
				catch(Exception e) {
					if(unknownRoleTags.add(tag)) {
						System.out.printf("%sUnknown role tag: %s%s\n", ANSI.RED, tag, ANSI.GRAY);
					}
				}
			}
		}
	}
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	public String getType() {
		return type;
	}
	public Attack getAttack() {
		return attack;
	}
	public Defense getDefense() {
		return defense;
	}
	public Faction getFaction() {
		return faction;
	}
	public Set<RoleTag> getRoleTags() {
		return roleTags;
	}
	public Set<PlayerTag> getPlayerTags() {
		return playerTags;
	}
	public StringTableMessage getDayAbilityUsesLeftMessage(int usesLeft) {
		return Game.STRING_TABLE.get("GUI_ROLE_"+id+"_DAY" + (usesLeft != 1 ? "_PLURAL" : ""));
	}
	public StringTableMessage getNightAbilityUsesLeftMessage(int usesLeft) {
		return Game.STRING_TABLE.get("GUI_ROLE_"+id+"_NIGHT" + (usesLeft != 1 ? "_PLURAL" : ""));
	}
	@Override
	public String toString() {
		return String.format("%s (%s %s). Attack: %s, Defense: %s", name, faction == null ? "Neutral" : faction.getName(), type, attack, defense);
	}
}
