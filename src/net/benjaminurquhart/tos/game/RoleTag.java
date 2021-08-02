package net.benjaminurquhart.tos.game;

//import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RoleTag {

	POST_DEATH_DAY_EXPANDED_ABILITY,
	POST_DEATH_NIGHT_ABILITY,
	POST_DEATH_DAY_ABILITY,
	
	NIGHT_ABILITY_IS_SILENT,
	
	DAY_EXPANDED_ABILITY,
	NIGHT_ABILITY,
	DAY_ABILITY,
	
	DEFERRED_ABILITY_USE,
	
	CLIENT_SIDE_TARGET_TRACKING,
	
	GOAL_TARGET_IS_FIXED_TARGET,
	TARGET_CAN_TARGET_SELF,
	 
	INDEPENDENT_WIN_CONDITION,
	
	ROLE_BLOCK_IMMUNE,
	RESTFUL_DEAD,
	FORGETTABLE,
	SAFE_ZOMBIE;
	
	private static final Pattern NAME_PATTERN = Pattern.compile("([A-Z])(.+?)?( |$)");
	private String name;
	
	/*
	static {
		System.out.println(Arrays.toString(values()));
	}*/
	
	@Override
	public String toString() {
		if(name == null) {
			Matcher matcher = NAME_PATTERN.matcher(this.name().replace('_', ' '));
			StringBuilder sb = new StringBuilder();
			while(matcher.find()) {
				sb.append(matcher.group(1).toUpperCase());
				sb.append(matcher.group(2).toLowerCase());
				sb.append(matcher.group(3));
			}
			name = sb.toString();
		}
		return name;
	}
	
}
