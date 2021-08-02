package net.benjaminurquhart.tos.game;

import java.util.List;

import org.json.JSONObject;

import net.benjaminurquhart.tos.game.entities.Role;
import net.benjaminurquhart.tos.game.entities.StringTableMessage;

public class GameMode {
	
	private StringTableMessage label, summary;
	private boolean requiresCoven;
	private int minimumPlayers,id;
	private String name;
	
	private String labelKey, summaryKey, rolelistKey;
	
	public GameMode(JSONObject json) {
		this.minimumPlayers = Integer.parseInt(json.getString("MinimumPlayers"));
		this.requiresCoven = "1".equals(json.optString("PermissionLevel"));
		this.id = Integer.parseInt(json.getString("id"));
		
		if(json.has("RoleList")) {
			this.rolelistKey = json.getJSONObject("RoleList").getString("id");
		}
		
		this.summaryKey = json.getJSONObject("Summary").getString("l10n");
		this.labelKey = json.getJSONObject("Label").getString("l10n");
		
		this.name = json.getString("Name");
	}
	public StringTableMessage getSummary() {
		if(summary == null) {
			summary = Game.STRING_TABLE.get(summaryKey);
		}
		return summary;
	}
	public StringTableMessage getLabel() {
		if(label == null) {
			label = Game.STRING_TABLE.get(labelKey);
		}
		return label;
	}
	public boolean isCovenGamemode() {
		return requiresCoven;
	}
	public List<Role> getRoleList() {
		return Game.ROLE_LIST_TABLE.get(rolelistKey);
	}
	public int getMinimumPlayers() {
		return minimumPlayers;
	}
	public String getName() {
		return name;
	}
	public int getID() {
		return id;
	}
	@Override
	public String toString() {
		return String.format("%s (%s): %s", this.getLabel().getText(), this.getName(), this.getSummary().getText());
	}
}
