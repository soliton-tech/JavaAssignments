package com.solitontech.instrument;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.solitontech.instrumentserver.proto.InstrumentInfo;
import com.solitontech.instrumentserver.proto.InstrumentType;

class InstrumentCollectionTest {

	@Disabled
	@Test
	void testAddInstrument() {		
		var instrumentCollection = new InstrumentCollection();
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DIGITAL).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.HYBRID).build());	

		assertEquals(
				instrumentCollection.toString(),
				"[Instrument 1, DCVI]\n"
						+ "[Instrument 2, DIGITAL]\n"
						+ "[Instrument 3, HYBRID]"
				);
	}

	@Disabled
	@Test
	void testAddInstrumentUnsupported() {
		var instrumentCollection = new InstrumentCollection();
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DIGITAL).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.HYBRID).build());

		assertAll(
				() -> assertThrows(
						IllegalArgumentException.class,
						() -> instrumentCollection.addInstrument(InstrumentInfo.newBuilder()
								.setName("Instrument 3")
								.setType(InstrumentType.UNRECOGNIZED)
								.build()),
						"Check UNRECOGNIZED"
						),
				() -> assertThrows(
						IllegalArgumentException.class,
						() -> instrumentCollection.addInstrument(InstrumentInfo.newBuilder()
								.setName("Instrument 3")
								.setType(InstrumentType.UNSPECIFIED_TYPE)
								.build()),
						"Check UNSPECIFIED_TYPE"
						),
				() -> assertEquals(
						instrumentCollection.toString(),
						"[Instrument 1, DCVI]\n"
								+ "[Instrument 2, DIGITAL]\n"
								+ "[Instrument 3, HYBRID]",
								"Check status of list"
						)
				);
	}

	@Nested
	@Disabled
	class InitializeTest {

		@Nested
		class InitializeTestSequence {

			private InstrumentCollection instrumentCollection;

			@BeforeEach
			void setup() {
				instrumentCollection = new InstrumentCollection();
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.DCVI).build());	
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 4").setType(InstrumentType.DIGITAL).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 5").setType(InstrumentType.DIGITAL).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 6").setType(InstrumentType.DIGITAL).build());	
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 7").setType(InstrumentType.HYBRID).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 8").setType(InstrumentType.HYBRID).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 9").setType(InstrumentType.HYBRID).build());			
			}

			@Test
			void testInitializeAllInstrument() {
				assertEquals(
						instrumentCollection.initializeInstrument(),
						List.of(
								"Instrument 1 Successfully initialized",
								"Instrument 2 Successfully initialized",
								"Instrument 3 Successfully initialized",
								"Instrument 4 Successfully initialized",
								"Instrument 5 Successfully initialized",
								"Instrument 6 Successfully initialized",
								"Instrument 7 Successfully initialized",
								"Instrument 8 Successfully initialized",
								"Instrument 9 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeDIGITALInstrument() {
				assertEquals(
						instrumentCollection.initializeInstrument(InstrumentType.DIGITAL),
						List.of(
								"Instrument 4 Successfully initialized",
								"Instrument 5 Successfully initialized",
								"Instrument 6 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeHYBRIDInstrument() {
				assertEquals(
						instrumentCollection.initializeInstrument(InstrumentType.HYBRID),
						List.of(
								"Instrument 7 Successfully initialized",
								"Instrument 8 Successfully initialized",
								"Instrument 9 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeDCVIInstrument() {
				assertEquals(
						instrumentCollection.initializeInstrument(InstrumentType.DCVI),
						List.of(
								"Instrument 1 Successfully initialized",
								"Instrument 2 Successfully initialized",
								"Instrument 3 Successfully initialized"
								)
						);
			}

		}

		@Nested
		class InitializeTestAsync {

			private InstrumentCollection instrumentCollection;

			@BeforeEach
			void setup() {
				instrumentCollection = new InstrumentCollection();
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.DCVI).build());	
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 4").setType(InstrumentType.DIGITAL).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 5").setType(InstrumentType.DIGITAL).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 6").setType(InstrumentType.DIGITAL).build());	
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 7").setType(InstrumentType.HYBRID).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 8").setType(InstrumentType.HYBRID).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 9").setType(InstrumentType.HYBRID).build());			
			}

			@Test
			void testInitializeAllInstrumentAsync() {
				assertEquals(
						instrumentCollection.initializeInstrumentAsync(),
						List.of(
								"Instrument 1 Successfully initialized",
								"Instrument 2 Successfully initialized",
								"Instrument 3 Successfully initialized",
								"Instrument 4 Successfully initialized",
								"Instrument 5 Successfully initialized",
								"Instrument 6 Successfully initialized",
								"Instrument 7 Successfully initialized",
								"Instrument 8 Successfully initialized",
								"Instrument 9 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeDIGITALInstrumentAsync() {
				assertEquals(
						instrumentCollection.initializeInstrumentAsync(InstrumentType.DIGITAL),
						List.of(
								"Instrument 4 Successfully initialized",
								"Instrument 5 Successfully initialized",
								"Instrument 6 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeHYBRIDInstrumentAsync() {
				assertEquals(
						instrumentCollection.initializeInstrumentAsync(InstrumentType.HYBRID),
						List.of(
								"Instrument 7 Successfully initialized",
								"Instrument 8 Successfully initialized",
								"Instrument 9 Successfully initialized"
								)
						);
			}

			@Test
			void testInitializeDCVIInstrumentAsync() {
				assertEquals(
						instrumentCollection.initializeInstrumentAsync(InstrumentType.DCVI),
						List.of(
								"Instrument 1 Successfully initialized",
								"Instrument 2 Successfully initialized",
								"Instrument 3 Successfully initialized"
								)
						);
			}

		}

		@Nested
		class InitializeTestEmptyList {

			private InstrumentCollection instrumentCollection;

			@BeforeEach
			void setup() {
				instrumentCollection = new InstrumentCollection();
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DCVI).build());
				instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.DCVI).build());		
			}

			@Test
			void testInitializeEmptyList() {
				assertEquals(
						instrumentCollection.initializeInstrument(InstrumentType.DIGITAL),
						List.of()
						);
			}

			@Test
			void testInitializeEmptyListAsync() {
				assertEquals(
						instrumentCollection.initializeInstrument(InstrumentType.DIGITAL),
						List.of()
						);
			}
		}
	}

	@Test
	void ExportImportTest() {
		var instrumentCollection = new InstrumentCollection();
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 1").setType(InstrumentType.DCVI).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 2").setType(InstrumentType.DIGITAL).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 3").setType(InstrumentType.HYBRID).build());	

		instrumentCollection.exportInstruments("test");

		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 4").setType(InstrumentType.DCVI).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 5").setType(InstrumentType.DIGITAL).build());
		instrumentCollection.addInstrument(InstrumentInfo.newBuilder().setName("Instrument 6").setType(InstrumentType.HYBRID).build());	

		instrumentCollection.importInstruments("test");

		assertAll(
				() -> assertEquals(
						instrumentCollection.initializeInstrumentAsync(),
						List.of(
								"Instrument 1 Successfully initialized",
								"Instrument 2 Successfully initialized",
								"Instrument 3 Successfully initialized"
								)
						),
				() -> assertEquals(
						instrumentCollection.toString(),
						"[Instrument 1, DCVI]\n"
								+ "[Instrument 2, DIGITAL]\n"
								+ "[Instrument 3, HYBRID]"
						)
				);
	}

}
