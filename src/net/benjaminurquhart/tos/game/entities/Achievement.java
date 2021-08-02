package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

import net.benjaminurquhart.tos.game.Game;

public class Achievement {

	private String steam, points, genre;
	private int id, roleID;
	
	public Achievement(JSONObject json) {
		this.points = json.optString("points");
		this.steam = json.optString("steam");
		this.genre = json.optString("genre");
		
		this.roleID = Integer.parseInt(json.getString("role"));
		this.id = Integer.parseInt(json.getString("id"));
	}
	public String getPoints() {
		return points;
	}
	public String getSteam() {
		return steam;
	}
	public String getGenre() {
		return genre;
	}
	public Role getRole() {
		return id < 0? null : Game.ROLES[roleID];
	}
	public int getID() {
		return id;
	}
	@Override
	public String toString() {
		return genre + ": " + steam + " (" + points + ")";
	}
}
