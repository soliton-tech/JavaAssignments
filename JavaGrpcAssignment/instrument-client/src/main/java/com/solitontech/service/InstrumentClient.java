package com.solitontech.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.solitontech.instrumentserver.proto.AddInstrumentRequest;
import com.solitontech.instrumentserver.proto.FileName;
import com.solitontech.instrumentserver.proto.InitializeInstrumentRequest;
import com.solitontech.instrumentserver.proto.InitializeInstrumentResponse;
import com.solitontech.instrumentserver.proto.InitializeOrder;
import com.solitontech.instrumentserver.proto.InstrumentInfo;
import com.solitontech.instrumentserver.proto.InstrumentServiceGrpc;
import com.solitontech.instrumentserver.proto.InstrumentType;
import com.solitontech.instrumentserver.proto.InstrumentServiceGrpc.InstrumentServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class InstrumentClient {
	private final InstrumentServiceBlockingStub blockingStub;
	private final ManagedChannel channel;
	
	
	public InstrumentClient() {
		channel = ManagedChannelBuilder
				.forAddress("localhost", 9093)
				.usePlaintext()
				.build();
		blockingStub = InstrumentServiceGrpc.newBlockingStub(channel);
	}
	
	public void addInstrument(String name, InstrumentType type) {
		AddInstrumentRequest addInstrumentRequest = AddInstrumentRequest.newBuilder()
				.setInstrument(
						InstrumentInfo.newBuilder()
						.setName(name)
						.setType(type)
						.build()
						)
				.build();
		
		blockingStub.addInstrument(addInstrumentRequest);
	}
	
	public List<String> initializeInstrument(InstrumentType type, InitializeOrder initializeOrder) {
		InitializeInstrumentRequest request = InitializeInstrumentRequest.newBuilder()
				.setInitializeAll(false)
				.setType(type)
				.setInitializeOrder(initializeOrder)
				.build();
		
		InitializeInstrumentResponse response = blockingStub.initializeInstrument(request);
		return response.getInitializedInstrumentsList().stream().toList();
	}
	
	public List<String> initializeInstrument(InitializeOrder initializeOrder) {
		InitializeInstrumentRequest request = InitializeInstrumentRequest.newBuilder()
				.setInitializeAll(true)
				.setInitializeOrder(initializeOrder)
				.build();
		
		InitializeInstrumentResponse response = blockingStub.initializeInstrument(request);
		return response.getInitializedInstrumentsList().stream().toList();
	}
	
	public void importFromJSON(String fileName) {
		FileName request = FileName.newBuilder()
				.setFileName(fileName)
				.build();
		
		blockingStub.importInstruments(request);
	}
	
	public void exportToJSON(String fileName) {
		FileName request = FileName.newBuilder()
				.setFileName(fileName)
				.build();
		
		blockingStub.exportInstruments(request);
	}
	
	public void stop() {
		channel.shutdown();
		try {
			channel.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
