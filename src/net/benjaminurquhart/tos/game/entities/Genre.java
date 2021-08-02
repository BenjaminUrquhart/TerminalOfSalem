package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;

import org.json.JSONObject;

public class Genre {

	private Color color;
	private String id;
	
	public Genre(JSONObject json) {
		this.color = new Color(Integer.parseInt(json.optString("color", "#000000").substring(1), 16));
		this.id = json.optString("id");
	}
	public Color getColor() {
		return color;
	}
	public String getID() {
		return id;
	}
	@Override
	public String toString() {
		return id + " (#" + Integer.toHexString(color.getRGB()&0xffffff) + ")";
	}
}
