package net.benjaminurquhart.tos;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class StringTableFormatter {

	public static void main(String[] args) throws Exception {
		JSONObject json = new JSONObject(Files.lines(new File("StringTableRaw.json").toPath(), Charset.defaultCharset()).collect(Collectors.joining()));
		JSONObject out = new JSONObject(), tmp;
		
		String key;
		
		for(Object obj : json.getJSONArray("Entry")) {
			tmp = (JSONObject) obj;
			key = tmp.getString("key");
			tmp.remove("key");
			out.put(key, tmp);
		}
		
		Files.write(new File("StringTable.json").toPath(), out.toString().getBytes(Charset.forName("UTF-8")));
	}
}
