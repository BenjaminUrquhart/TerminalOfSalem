package net.benjaminurquhart.tos;

import net.benjaminurquhart.tos.game.ANSI;
import net.benjaminurquhart.tos.game.Game;
import net.benjaminurquhart.tos.game.entities.*;

public class StringTableColorTest {

	public static void main(String[] args) {
		for(StringTableMessage msg : Game.STRING_TABLE.values()) {
			System.out.printf("%s: %s%s\n", msg.getID(), msg.getParsedText(), ANSI.RESET);
		}
	}
}
