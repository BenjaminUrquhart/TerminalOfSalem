package net.benjaminurquhart.tos.game.entities;

import org.json.JSONObject;

public class NoScroll extends Scroll {

	public NoScroll(JSONObject json) {
		super(reprocess(json));
	}
	private static JSONObject reprocess(JSONObject json) {
		return json.put("Role", "-1").put("Odds", "0");
	}
	@Override
	public Role getRole() {
		return null;
	}
	@Override
	public String toString() {
		return "No Scroll";
	}
}
