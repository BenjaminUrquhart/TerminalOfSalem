package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;

import org.json.JSONObject;

public class Winner {

	private boolean isDraw;
	
	private String name;
	private Color color;
	private int id;
	
	public Winner(JSONObject json) {
		this.color = new Color(Integer.parseInt(json.optString("Color", "#505050").substring(1), 16));
		this.name = json.getString("Name");
		
		this.isDraw = json.has("isDraw") && json.getBoolean("isDraw");
		this.id = Integer.parseInt(json.getString("id"));
	}
	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public boolean isDraw() {
		return isDraw;
	}
	public int getID() {
		return id;
	}
	@Override
	public String toString() {
		if(isDraw) {
			return "The game has ended in a draw (" + name + ")";
		}
		return name + " " + (name.endsWith("s") ? "win" : "wins");
	}
}
