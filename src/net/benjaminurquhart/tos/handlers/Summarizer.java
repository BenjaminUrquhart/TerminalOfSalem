package net.benjaminurquhart.tos.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.benjaminurquhart.tos.game.ANSI;
import net.benjaminurquhart.tos.game.Game;
import net.benjaminurquhart.tos.game.GamePhase;
import net.benjaminurquhart.tos.game.entities.Player;
import net.benjaminurquhart.tos.game.entities.Role;
import net.benjaminurquhart.tos.game.entities.Winner;

public class Summarizer extends ServerMessageHandler {

	private Winner winner;
	private Game game;
	
	private List<Role> roles;
	
	private GamePhase diedDuring;
	private int diedOn;
	
	private int dayNightCycle = 1;
	
	private boolean wasResurrected;
	
	private Player self;
	
	private byte[] endgameInfo;
	
	public Summarizer(Game game) {
		super(game);
		
		this.roles = new ArrayList<>();
		this.game = game;
	}
	@Override
	public void onStartDiscussion(byte[] command) {
		super.onStartDiscussion(command);
		dayNightCycle++;
		if(self.isAlive() && !self.getRole().equals(roles.get(roles.size()-1))) {
			roles.add(self.getRole());
		}
	}
	@Override
	public void onResurrectionSetAlive(byte[] command) {
		boolean wasAlive = self.isAlive();
		super.onResurrectionSetAlive(command);
		if(self.isAlive() && !wasAlive) {
			wasResurrected = true;
			diedDuring = null;
		}
	}
	@Override
	public void onRoleAndPosition(byte[] command) {
		super.onRoleAndPosition(command);
		self = game.getSelfPlayer();
		roles.add(self.getRole());
	}
	@Override
	public void onUserDied(byte[] command) {
		super.onUserDied(command);
		this.wasResurrected = false;
		this.diedDuring = game.getPhase();
		this.diedOn = this.dayNightCycle;
	}
	@Override
	public void onSomeoneHasWon(byte[] command) {
		super.onSomeoneHasWon(command);
		winner = Game.WINNERS[command[1]-1];
	}
	@Override
	public void onEndGameInfo(byte[] command) {
		endgameInfo = command;
	}
	public void summarize() {
		System.out.printf("Game Mode: %s\n", game.getMode() == null ? "???" : game.getMode().getLabel().getText());
		System.out.println("Name: " + self);
		System.out.printf("Role: %s%s%s\n", ANSI.toTrueColor(self.getRole().getColor()), self.getRole().getName(), ANSI.RESET);
		if(roles.size() > 1) {
			String previous = roles.stream()
								   .limit(roles.size()-1)
								   .map(role -> ANSI.toTrueColor(role.getColor())+role.getName()+ANSI.RESET)
								   .collect(Collectors.joining(", "));
			System.out.printf("Previous Roles: %s\n", previous);
		}
		if(diedDuring != null) {
			String deathTime = diedDuring == GamePhase.NIGHT ? "Night" : "Day";
			System.out.printf("Died %s %d%s\n", deathTime, diedOn, wasResurrected ? " (Resurrected)" : "");
		}
		if(winner == null) {
			System.out.println("Outcome: ???");
		}
		else {
			System.out.printf("Outcome: %s%s%s\n", ANSI.toTrueColor(winner.getColor()), winner, ANSI.RESET);
		}
		if(endgameInfo != null) {
			super.onEndGameInfo(endgameInfo);
		}
	}
}
