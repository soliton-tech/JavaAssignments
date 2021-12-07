package com.solitontech.site;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.solitontech.site.Site.SiteStatus;

public class Sites {
	public static final String sitesDirPath = "C:\\test\\sites-assignment\\sites";
	private int noOfSites = 0;
	private Map<Integer,Site> sites = new HashMap<>();
	
	public Sites() {
		cleanUp();
		setNoOfSites(4);
	} 

	public int getNoOfSites() {
		return noOfSites;
	}

	public void setNoOfSites(int noOfSites) {
		updateNoOfSites(this.noOfSites, noOfSites);
		this.noOfSites = noOfSites;
	}

	public List<String> burnSites(File source) {
		var futures = new ArrayList<CompletableFuture<Void>>();
		
		for(var key : sites.keySet())
			futures.add(CompletableFuture.runAsync(() -> sites.get(key).burn(source)));
		
		for(var future : futures)
			future.join();
		
		return sites.values().stream()
				.filter(site -> site.getStatus() == SiteStatus.Failed)
				.map(Site::getSiteName)
				.toList();
	}
	
	public List<String> validateSites(File source) {
		var futures = new ArrayList<CompletableFuture<Void>>();
		
		for(var key : sites.keySet())
			futures.add(CompletableFuture.runAsync(() -> sites.get(key).validate(source)));
		
		for(var future : futures)
			future.join();
		
		return sites.values().stream()
				.filter(site -> site.getStatus() == SiteStatus.Failed)
				.map(Site::getSiteName)
				.toList();
	}
	
	private void updateNoOfSites(int noOfSitesOld, int noOfSitesNew) {		
		if(noOfSitesNew == noOfSitesOld)
			return;
		
		if(noOfSitesNew < noOfSitesOld) {
			//Remove unwanted sites
			for(int i = noOfSitesNew + 1; i <= noOfSitesOld; i++) {
				sites.remove(i).remove();
			}
		} else {
			//Add additional sites
			for(int i = noOfSitesOld + 1; i <= noOfSitesNew; i++)
				sites.put(i, new Site(i));
		}
	}
	
	private static void cleanUp() {
		File targetDir = new File(sitesDirPath);
		targetDir.mkdirs();
		for(var file : targetDir.listFiles())
			file.delete();
	}
}
