package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

import net.benjaminurquhart.tos.game.Game;

public class Scroll {

	private int id, roleId, odds;
	private String name;
	
	public Scroll(JSONObject json) {
		this.name = json.getJSONObject("Name").getString("text");
		
		this.roleId = Integer.parseInt(json.getString("Role"));
		this.odds = Integer.parseInt(json.getString("Odds"));
		this.id = Integer.parseInt(json.getString("id"));
	}
	public int getID() {
		return id;
	}
	public int getOdds() {
		return odds;
	}
	public Role getRole() {
		return Game.ROLES[roleId];
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return String.format("%s (x%d Odds)", name, odds);
	}
}
