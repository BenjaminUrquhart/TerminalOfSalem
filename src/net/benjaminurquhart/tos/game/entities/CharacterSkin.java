package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

public class CharacterSkin {

	private boolean isMale;
	private String name;
	private int id;
	
	public CharacterSkin(JSONObject json) {
		this.isMale = json.getString("IsMale").equals("1");
		this.name = json.getString("Name");
		
		this.id = Integer.parseInt(json.getString("id"));
	}
	public String getName() {
		return name;
	}
	public boolean isMale() {
		return isMale;
	}
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return String.format("CharacterSkin [name=%s, id=%d, gender=%s]", name, id, isMale ? "male" : "female");
	}
}
