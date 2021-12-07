package com.solitontech.service.io;

public interface LineIO {
	String readLine();
	void writeLine(String Message);
	
	default String readLine(String Message) {
		writeLine(Message);
		return readLine();
	}
}
