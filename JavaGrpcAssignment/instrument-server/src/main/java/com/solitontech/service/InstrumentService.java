package com.solitontech.service;

import java.util.ArrayList;
import java.util.List;

import com.solitontech.instrument.InstrumentCollection;
import com.solitontech.instrumentserver.proto.AddInstrumentRequest;
import com.solitontech.instrumentserver.proto.Empty;
import com.solitontech.instrumentserver.proto.FileName;
import com.solitontech.instrumentserver.proto.InitializeInstrumentRequest;
import com.solitontech.instrumentserver.proto.InitializeInstrumentResponse;
import com.solitontech.instrumentserver.proto.InitializeOrder;
import com.solitontech.instrumentserver.proto.InstrumentServiceGrpc.InstrumentServiceImplBase;

import io.grpc.stub.StreamObserver;

public class InstrumentService extends InstrumentServiceImplBase{
	private InstrumentCollection instrumentCollection = new InstrumentCollection();
	
	@Override
	public void addInstrument(AddInstrumentRequest request, StreamObserver<Empty> responseObserver) {
		instrumentCollection.addInstrument(request.getInstrument());
		responseObserver.onNext(Empty.newBuilder().build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void initializeInstrument(InitializeInstrumentRequest request,
			StreamObserver<InitializeInstrumentResponse> responseObserver) {
		
		List<String> instruments = new ArrayList<String>();
		
		if(request.getInitializeOrder() == InitializeOrder.SEQUENCE) {
			if(request.getInitializeAll())
				instruments = instrumentCollection.initializeInstrument();
			else
				instruments = instrumentCollection.initializeInstrument(request.getType());
		}
		else if(request.getInitializeOrder() == InitializeOrder.PARALLEL){
			if(request.getInitializeAll())
				instruments = instrumentCollection.initializeInstrumentAsync();
			else
				instruments = instrumentCollection.initializeInstrumentAsync(request.getType());
		}
		
		InitializeInstrumentResponse response = InitializeInstrumentResponse.newBuilder()
				.addAllInitializedInstruments(instruments)
				.build();		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void exportInstruments(FileName request, StreamObserver<Empty> responseObserver) {
		instrumentCollection.exportInstruments(request.getFileName());
		responseObserver.onNext(Empty.newBuilder().build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void importInstruments(FileName request, StreamObserver<Empty> responseObserver) {
		instrumentCollection.importInstruments(request.getFileName());
		responseObserver.onNext(Empty.newBuilder().build());
		responseObserver.onCompleted();
	}
}
