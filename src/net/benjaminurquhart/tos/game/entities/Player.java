package net.benjaminurquhart.tos.game.entities;

import java.util.HashSet;
import java.util.Set;

import net.benjaminurquhart.tos.game.ANSI;
import net.benjaminurquhart.tos.game.PlayerTag;

public class Player {
	
	static class AnonymousPlayer extends Player {
		
		public static final AnonymousPlayer INSTANCE = new AnonymousPlayer();

		private AnonymousPlayer() {
			super(ANSI.GREEN + "Someone" + ANSI.RESET, 100);
		}
		
	}
	
	public static AnonymousPlayer getAnonymousPlayer() {
		return AnonymousPlayer.INSTANCE;
	}
	
	private static final Killer[] NO_KILLERS = new Killer[0];
	
	private Set<PlayerTag> tags;

	private CharacterSkin character;
	private boolean alive, won;
	private int position;
	private String name;
	private Role role;
	
	private Killer[] killers;
	private Player target;
	
	private String latestJudgement;
	
	public Player(String name, int position) {
		this.latestJudgement = "N/A";
		this.position = position;
		this.name = name;
		
		this.killers = NO_KILLERS;
		this.alive = true;
		
		this.tags = new HashSet<>();
	}
	
	public String getLatestJudgementVote() {
		return latestJudgement;
	}
	public CharacterSkin getCharacter() {
		return character;
	}
	public Set<PlayerTag> getTags() {
		return tags;
	}
	public Killer[] getKillers() {
		return killers;
	}
	public Player getTarget() {
		return target;
	}
	public int getPosition() {
		return position;
	}
	public boolean isAlive() {
		return alive;
	}
	public boolean didWin() {
		return won;
	}
	public String getName() {
		return name;
	}
	public Role getRole() {
		return role;
	}
	
	public void setLatestJudgementVote(String judgement) {
		this.latestJudgement = judgement;
	}
	public void setCharacter(CharacterSkin character) {
		this.character = character;
	}
	public void setTarget(Player target) {
		this.target = target;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void lose() {
		this.won = false;
	}
	public void win() {
		this.won = true;
	}
	
	public void removeTag(PlayerTag tag) {
		this.tags.remove(tag);
	}
	public void addTag(PlayerTag tag) {
		this.tags.add(tag);
	}
	
	public void lynch() {
		this.killers = new Killer[] {Killer.LYNCHING};
	}
	public void kill(Killer... killers) {
		if(this.killers.length > 0) {
			Killer[] tmp = new Killer[this.killers.length+killers.length];
			System.arraycopy(this.killers, 0, tmp, 0, this.killers.length);
			System.arraycopy(killers, 0, tmp, this.killers.length, killers.length);
			this.killers = tmp;
		}
		else {
			this.killers = killers;
		}
		alive = false;
		//System.out.println("Killed " + name + " (" + position + ")");
	}
	public void resurrect() {
		killers = NO_KILLERS;
		alive = true;
	}
	@Override
	public String toString() {
		return name;
	}
}
