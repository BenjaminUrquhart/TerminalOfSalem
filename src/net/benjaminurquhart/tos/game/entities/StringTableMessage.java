package net.benjaminurquhart.tos.game.entities;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import net.benjaminurquhart.tos.game.ANSI;

public class StringTableMessage {
	
	private static final Pattern COLOR_REGEX = Pattern.compile("(<color=#([0-9a-fA-F]{6})>)([^>]+)(</color>)");

	private String text, id, parsed;
	private Color color;
	
	public StringTableMessage(String id, JSONObject json) {
		switch(json.optString("style", "none")) {
		case "mayor_reveal": color = new Color(0xfa5757); break;
		
		case "ability_feedback": color = Color.BLACK; break;
		case "ability_status": color = Color.LIGHT_GRAY; break;
		case "positive": color = Color.GREEN; break;
		case "vital": color = Color.BLACK; break;
		case "alert": color = Color.RED; break;
		case "spy": color = Color.BLACK; break;
		default: color = Color.GRAY; break;
		}
		this.text = json.optString("text");
		this.id = id;
		
		if(text != null) {
			this.parsed = text;
			Matcher matcher = COLOR_REGEX.matcher(text);
			String content;
			Color color;
			
			while(matcher.find()) {
				content = matcher.group(3);
				color = new Color(Integer.parseInt(matcher.group(2), 16));
				
				parsed = parsed.replaceFirst(Pattern.quote(matcher.group(0)), ANSI.toTrueColor(color) + content + ANSI.RESET);
			}
			
			parsed = parsed.replaceAll("</?[^>]+>", "");
			text = text.replaceAll("</?[^>]+>", "");
		}
	}
	
	public String getParsedText() {
		return parsed;
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
