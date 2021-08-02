package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;

import org.json.JSONObject;

public class Killer {
	
	public static final Killer LYNCHING = new Killer();
	
	public static final Color DEFAULT_COLOR = new Color(0x505050);

	private String name;
	private Color color;
	private int id;
	
	public Killer(JSONObject json) {
		//System.out.println(json);
		this.color = json.has("Color") ? new Color(Integer.parseInt(json.getString("Color").substring(1), 16)) : DEFAULT_COLOR;
		this.id = Integer.parseInt(json.getString("id"));
		
		if(json.has("Name") && (json.get("Name") instanceof JSONObject)) {
			json.put("Name", json.getJSONObject("Name").getString("text"));
		}
		this.name = json.optString("Name", "Leaving the Game");
	}
	private Killer() {
		this.color = DEFAULT_COLOR;
		this.name = "Lynching";
		this.id = -1;
	}
	public Color getColor() {
		return color == null ? DEFAULT_COLOR : color;
	}
	public String getName() {
		return name;
	}
	public int getID() {
		return id;
	}
	@Override
	public String toString() {
		return name + " (" + id + ")";
	}
}
