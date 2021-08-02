package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

public class RandomRole extends Role {

	public RandomRole(JSONObject json) {
		super(reprocess(json));
	}
	
	private static JSONObject reprocess(JSONObject json) {
		return json.put("Type", new JSONObject().put("text", "Random"))
				   .put("Attack", "0")
				   .put("Defense", "0");
	}
}
