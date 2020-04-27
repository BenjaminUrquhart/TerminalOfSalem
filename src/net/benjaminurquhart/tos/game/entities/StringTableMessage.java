package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;

import org.json.JSONObject;

public class StringTableMessage {

	private String text, id;
	private Color color;
	
	public StringTableMessage(String id, JSONObject json) {
		switch(json.optString("style", "none")) {
		case "mayor_reveal": color = new Color(0xfa5757); break;
		
		case "ability_feedback": color = Color.BLACK; break;
		case "ability_status": color = Color.LIGHT_GRAY; break;
		case "positive": color = Color.GREEN; break;
		case "alert": color = Color.RED; break;
		case "vital": color = Color.BLACK; break;
		case "spy": color = Color.BLACK; break;
		default: color = Color.GRAY; break;
		}
		this.text = json.optString("text");
		this.id = id;
		
		if(text != null) {
			text = text.replaceAll("</?[^>]+>", "");
		}
	}
	public String getText() {
		return text;
	}
	public Color getColor() {
		return color;
	}
	public String getID() {
		return id;
	}
	@Override
	public String toString() {
		return text + " (" + id + ")";
	}
}
