package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

public class Faction {

	private String name;
	private int id;
	
	public Faction(JSONObject json) {
		this.id = Integer.parseInt(json.getString("id"));
		this.name = json.getString("Name");
	}
	public String getName() {
		return name;
	}
	public int getID() {
		return id;
	}
	public String toString() {
		return "Faction ("+name+", "+id+")";
	}
}
