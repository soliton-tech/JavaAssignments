package com.solitontech.site;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Site {
	private final int siteNumber;
	private final String siteName;
	private final File siteFile;
	private SiteStatus status = SiteStatus.Failed;
	

	/**
	 * Creates site file if its missing.
	 * @param siteNumber Index of the site.
	 * @return false if the site fails to burn. 
	 */
	public Site(int siteNumber) {
		this.siteNumber = siteNumber;
		this.siteName = "site" + siteNumber;
		this.siteFile = new File(Sites.sitesDirPath + File.separator + siteName + ".txt");
	
		try {
			siteFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SiteStatus getStatus() {
		return status;
	}

	public String getSiteName() {
		return siteName;
	}

	/**
	 * Burns the source file to the site.
	 * @param fileSource Source file to burn to the site.
	 * @return false if the site fails to burn. 
	 */
	public void burn(File fileSource) {
		try(
			var source = new FileInputStream(fileSource).getChannel();
			var target = new FileOutputStream(siteFile).getChannel();
		){
			source.transferTo(0, source.size(), target);
			validate(fileSource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = SiteStatus.Failed;
		}
	}
	
	/**
	 * Validates the data from source file to the site file.
	 * @param fileSource Source file to compare the site file.
	 * @return false if the validation fails. 
	 */
	public void validate(File fileSource) {
		try {
			status = (Files.mismatch(fileSource.toPath(), siteFile.toPath()) == -1L) ? SiteStatus.Passed : SiteStatus.Failed;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = SiteStatus.Failed;
		}
	}
	
	/**
	 * Deletes the site file.
	 */
	public void remove() {
		siteFile.delete();
	}
	
	public enum SiteStatus {
		Failed,
		Passed
	}
}
