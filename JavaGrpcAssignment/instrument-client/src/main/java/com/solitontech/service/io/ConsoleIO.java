package com.solitontech.service.io;

import java.util.Scanner;

public class ConsoleIO implements LineIO {

	private final Scanner scanner = new Scanner(System.in);
	
	@Override
	public String readLine() {
		return scanner.nextLine();
	}

	@Override
	public void writeLine(String Message) {
		System.out.println(Message);
	}

}
