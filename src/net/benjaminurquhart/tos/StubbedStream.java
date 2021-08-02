package net.benjaminurquhart.tos;

import java.io.PrintStream;
import java.util.Locale;

public class StubbedStream extends PrintStream {

	public StubbedStream() {
		super(System.out);
	}
	public void print(Object obj) {}
	public void print(String s) {}
	public void print(long l) {}
	public void print(int i) {}
	public void print(double d) {}
	public void print(float f) {}
	public void print(char c) {}
	public void print(boolean bool) {}
	public void print(char[] chars) {}
	public void println(Object obj) {}
	public void println(String s) {}
	public void println(long l) {}
	public void println(int i) {}
	public void println(double d) {}
	public void println(float f) {}
	public void println(char c) {}
	public void println(boolean bool) {}
	public void println() {}
	public void println(char[] chars) {}
	public PrintStream printf(String format, Object... args) {return this;}
	public PrintStream printf(Locale l, String format, Object... args) {return this;}
}
