package com.solitontech.siteserver;

import java.io.File;
import java.io.IOException;

import com.solitontech.site.Sites;
import com.solitontech.siteserver.proto.Empty;
import com.solitontech.siteserver.proto.FilePath;
import com.solitontech.siteserver.proto.Result;
import com.solitontech.siteserver.proto.SiteInfo;
import com.solitontech.siteserver.proto.SiteServiceGrpc.SiteServiceImplBase;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class SiteServer extends SiteServiceImplBase {
	private Sites sites = new Sites();
	
	public static void main(String args[]) {
		var server = ServerBuilder
				.forPort(9095)
				.addService(new SiteServer())
				.build();
		
		try {
			server.start();
			
			System.out.println("Server running at port " + server.getPort());
			
			server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateSite(SiteInfo request, StreamObserver<SiteInfo> responseObserver) {
		sites.setNoOfSites(request.getNoOfSites());
		responseObserver.onNext(request);
		responseObserver.onCompleted();
	}
	
	@Override
	public void burnData(FilePath request, StreamObserver<Result> responseObserver) {
		var startTime = System.currentTimeMillis();
		var failedSites = sites.burnSites(new File(request.getFilePath()));	
		var endTime = System.currentTimeMillis();
		var response = Result.newBuilder()
				.addAllFailedSites(failedSites)
				.setElapsedTimeMs((int) (endTime - startTime))
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	@Override
	public void validateData(FilePath request, StreamObserver<Result> responseObserver) {
		var startTime = System.currentTimeMillis();
		var failedSites = sites.validateSites(new File(request.getFilePath()));	
		var endTime = System.currentTimeMillis();
		var response = Result.newBuilder()
				.addAllFailedSites(failedSites)
				.setElapsedTimeMs((int) (endTime - startTime))
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	@Override
	public void getSite(Empty request, StreamObserver<SiteInfo> responseObserver) {
		responseObserver.onNext(SiteInfo.newBuilder().setNoOfSites(sites.getNoOfSites()).build());
		responseObserver.onCompleted();
	}
}
