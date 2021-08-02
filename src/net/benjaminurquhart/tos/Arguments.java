package net.benjaminurquhart.tos;

public class Arguments {

	private String filename;
	private boolean fancy;
	private Mode mode;
	
	public Arguments(String... args) {
		mode = Mode.LIVE;
		if(args.length > 0) {
			int index = 0;
			if(args[0].equalsIgnoreCase("--fancy-deaths")) {
				fancy = true;
				index++;
			}
			if(args.length <= index) return;
			switch(args[index].toLowerCase()) {
			case "--file": mode = Mode.REVIEW; break;
			case "--preview": mode = Mode.SUMMARIZE; break;
			case "--replay": mode = Mode.REPLAY; break;
			default: return;
			}
			if(mode != Mode.LIVE && args.length == index+1) {
				System.out.println("Missing filepath argument after " + args[index]);
				System.exit(0);
			}
			else {
				filename = args[index+1];
				if(!fancy && args.length > index+2 && args[index+2].equalsIgnoreCase("--fancy-deaths")) {
					fancy = true;
				}
			}
		}
	}
	public Mode getMode() {
		return mode;
	}
	public String getFilename() {
		if(mode == Mode.LIVE) {
			throw new IllegalStateException("Cannot get filename during a live session!");
		}
		return filename;
	}
	public boolean useFancyDeaths() {
		return fancy;
	}
}
