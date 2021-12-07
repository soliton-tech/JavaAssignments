package com.solitontech.service;

import java.io.IOException;

import io.grpc.ServerBuilder;

public class InstrumentServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		var server = ServerBuilder.forPort(9093)
				.addService(new InstrumentService())
				.build();
		
		server.start();
		System.out.println("Server running at port " + server.getPort());
		
		server.awaitTermination();
	}

}
