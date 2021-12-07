package com.solitontech.site;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SitesTest {

	@Nested
	class UpdateNoOfSitesTest {
		
		@Test
		void testDefaultNoOfSites() {			
			Sites sites = new Sites();
			assertAll(assertSiteCount(sites, 4));
		}
		
		@Test
		void testIncreaseNoOfSites() {			
			Sites sites = new Sites();			
			sites.setNoOfSites(8);		
			assertAll(assertSiteCount(sites, 8));
		}
		
		@Test
		void testDecreaseNoOfSites() {			
			Sites sites = new Sites();			
			sites.setNoOfSites(2);		
			assertAll(assertSiteCount(sites, 2));
		}
		
		@Test
		void testSameNoOfSites() {			
			Sites sites = new Sites();			
			sites.setNoOfSites(4);		
			assertAll(assertSiteCount(sites, 4));
		}
		
		@Test
		void testZeroNoOfSites() {			
			Sites sites = new Sites();			
			sites.setNoOfSites(0);		
			assertAll(assertSiteCount(sites, 0));
		}
		
		private static List<Executable> assertSiteCount(Sites sites, int noOfSites) {
			List<String> expected = Stream.iterate(1, i -> i + 1)
					.limit(noOfSites)
					.map(i -> "site" + i + ".txt")
					.sorted()
					.toList();

			List<String> actual = Stream.of(new File(Sites.sitesDirPath).listFiles())
					.map(file -> file.getName())
					.sorted()
					.toList();

			return List.of(
					() -> assertEquals(expected, actual, "Site file list check"),
					() -> assertEquals(noOfSites, sites.getNoOfSites(), "Site file list check")
					);
		}
	}
	
	@Test
	void testBurnData() {
		File sourceFile = new File("C:\\test\\sites-assignment\\input\\test_small.txt");
		Sites sites = new Sites();
		
		var actual = sites.burnSites(sourceFile);
		
		assertEquals(List.of(), actual);
	}
	
	@Test
	void testValidateData() {
		File sourceFile = new File("C:\\test\\sites-assignment\\input\\test_small.txt");
		Sites sites = new Sites();
		sites.burnSites(sourceFile);
		
		var failedSites = sites.validateSites(sourceFile);
		
		assertEquals(List.of(), failedSites);
	}
	
	@Test
	void testValidateDataFail() {
		File sourceFile = new File("C:\\test\\sites-assignment\\input\\test_small.txt");
		Sites sites = new Sites();
		sites.setNoOfSites(4);
		sites.burnSites(sourceFile);
		sites.setNoOfSites(8);		
		
		var actual = sites.validateSites(sourceFile);
		
		var expected = List.of("site5", "site6", "site7", "site8");
		
		assertEquals(expected, actual);
	}

}
