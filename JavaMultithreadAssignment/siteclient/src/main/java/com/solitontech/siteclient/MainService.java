package com.solitontech.siteclient;

import java.util.concurrent.TimeUnit;

import com.solitontech.siteserver.proto.Empty;
import com.solitontech.siteserver.proto.FilePath;
import com.solitontech.siteserver.proto.Result;
import com.solitontech.siteserver.proto.SiteInfo;
import com.solitontech.siteserver.proto.SiteServiceGrpc;
import com.solitontech.siteserver.proto.SiteServiceGrpc.SiteServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MainService {
	private ManagedChannel channel;
	private SiteServiceBlockingStub blockingStub;
	private int noOfSites;

	public MainService() {
		this.channel = ManagedChannelBuilder
				.forAddress("localhost", 9095)
				.usePlaintext()
				.build();

		this.blockingStub = SiteServiceGrpc.newBlockingStub(channel);
	}

	public int setNoOfSites(int noOfSites) {
		blockingStub.updateSite(
				SiteInfo.newBuilder()
				.setNoOfSites(noOfSites)
				.build()
				);
		return fetchNoOfSites();
	}
	
	public int fetchNoOfSites() {
		this.noOfSites = blockingStub
				.getSite(Empty.newBuilder().build())
				.getNoOfSites();
		return this.noOfSites;
	}

	public int getNoOfSites() {
		return noOfSites;
	}

	public Result burnData(String path) {
		return blockingStub.burnData(FilePath.newBuilder()
				.setFilePath(path)
				.build());
	}
	
	public Result validateData(String path) {		
		return blockingStub.validateData(FilePath.newBuilder()
				.setFilePath(path)
				.build());
	}

	private void shutdown() {
		channel.shutdown();
		try {
			channel.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}