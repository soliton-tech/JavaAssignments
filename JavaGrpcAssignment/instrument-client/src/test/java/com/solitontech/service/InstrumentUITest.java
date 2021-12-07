package com.solitontech.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.solitontech.service.io.LineIO;

class InstrumentUITest {

	private final List<String> cmdExit = List.of(
			"10", //Select Exit
			"1", //Confirm exit
			"0" //Enter any key
			);

	private final List<String> msgMainMenu = List.of(
			"1. Add DCVI Instrument",
			"2. Add Digital Instrument",
			"3. Add Hybrid Instrument",
			"4. Initialize DCVI Instrument",
			"5. Initialize Digital Instrument",
			"6. Initialize Hybrid Instrument",
			"7. Initialize All Instrument",
			"8. Import From JSON",
			"9. Export To JSON",
			"10. Exit",
			"Enter a choice: "
			);

	private final List<String> msgExitMenu = List.of(
			"Are you sure to exit the application?",
			"1. Yes",
			"2. No",
			"Enter a choice: "
			);

	private final List<String> msgInitializeMenu = List.of(
			"1. In Sequence",
			"2. In Parallel",
			"Enter a choice: "
			);

	private final List<String> msgThankyou = List.of(
			"Thank you!! Press any key!!"
			);

	private final List<String> msgInvalid = List.of(
			"Invalid choice!!"
			);

	private final List<String> msgAddInstrument = List.of(
			"Please enter the instrument name: "
			);

	private final List<String> msgGetFileName = List.of(
			"Please enter the file name: "
			);


	@Nested
	class BasicTest {
		@Test
		void testExit() {
			TestIO io = new TestIO(
					new ListHelper()
					.add(cmdExit)
					.list
					);

			executeClient(io);

			assertEquals(
					new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list,
					io.getOutput()
					);
		}

		@Test
		void testInvalidAndExit() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("dkjcvjwkc") //Invalid input
					.add(cmdExit)
					.list
					);

			executeClient(io);

			assertEquals(
					new ListHelper()
					.add(msgMainMenu).add(msgInvalid)
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list,
					io.getOutput());
		}		
	}

	@Test
	void testAddInstrument() {
		TestIO io = new TestIO(
				new ListHelper()
				.add("1").add("DCVI_1") //Add instrument with name DCVI_1
				.add("2").add("Digital_1") //Add instrument with name Digital_1
				.add("3").add("Hybrid_1") //Add instrument with name Hybrid_1
				.add(cmdExit)
				.list
				);

		executeClient(io);

		assertEquals(
				new ListHelper()
				.add(msgMainMenu).add(msgAddInstrument).add("The instrument DCVI_1 is added successfully")
				.add(msgMainMenu).add(msgAddInstrument).add("The instrument Digital_1 is added successfully")
				.add(msgMainMenu).add(msgAddInstrument).add("The instrument Hybrid_1 is added successfully")
				.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
				.list,
				io.getOutput());
	}

	@Nested
	class InitializeInstrumentSequenceTest {

		@BeforeEach
		void setup() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("Empty")
					.add("1").add("DCVI_1")
					.add("1").add("DCVI_2")
					.add("2").add("Digital_1")
					.add("2").add("Digital_2")
					.add("3").add("Hybrid_1")
					.add("3").add("Hybrid_2")
					.add("9").add("clientAutomatedTest")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			assertEquals(
					new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from Empty file")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument DCVI_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument DCVI_2 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Digital_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Digital_2 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Hybrid_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Hybrid_2 is added successfully")
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are exported successfully to clientAutomatedTest file")
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list,
					io.getOutput());
		}

		@Test
		void testInitializeDCVIInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("4").add("1")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the DCVI instruments?").add(msgInitializeMenu)
					.add("The below DCVI Instruments are initialized sequentially")
					.add("[DCVI_1, DCVI_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeDigitalInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("5").add("1")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the Digital instruments?").add(msgInitializeMenu)
					.add("The below Digital Instruments are initialized sequentially")
					.add("[Digital_1, Digital_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeHybridInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("6").add("1")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the Hybrid instruments?").add(msgInitializeMenu)
					.add("The below Hybrid Instruments are initialized sequentially")
					.add("[Hybrid_1, Hybrid_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeAllInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("7").add("1")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the instruments?").add(msgInitializeMenu)
					.add("The below Instruments are initialized sequentially")
					.add("[DCVI_1, DCVI_2, Digital_1, Digital_2, Hybrid_1, Hybrid_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeInstrumentInvalid() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("7").add("asdf")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			assertEquals(new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the instruments?").add(msgInitializeMenu).add(msgInvalid)
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list,
					io.getOutput()
					);
		}
	}

	@Nested
	class InitializeInstrumentParallelTest {

		@BeforeEach
		void setup() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("Empty")
					.add("1").add("DCVI_1")
					.add("1").add("DCVI_2")
					.add("2").add("Digital_1")
					.add("2").add("Digital_2")
					.add("3").add("Hybrid_1")
					.add("3").add("Hybrid_2")
					.add("9").add("clientAutomatedTest")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			assertEquals(
					new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from Empty file")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument DCVI_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument DCVI_2 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Digital_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Digital_2 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Hybrid_1 is added successfully")
					.add(msgMainMenu).add(msgAddInstrument).add("The instrument Hybrid_2 is added successfully")
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are exported successfully to clientAutomatedTest file")
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list,
					io.getOutput());
		}

		@Test
		void testInitializeDCVIInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("4").add("2")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the DCVI instruments?").add(msgInitializeMenu)
					.add("The below DCVI Instruments are initialized sequentially")
					.add("[DCVI_1, DCVI_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeDigitalInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("5").add("2")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the Digital instruments?").add(msgInitializeMenu)
					.add("The below Digital Instruments are initialized sequentially")
					.add("[Digital_1, Digital_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeHybridInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("6").add("2")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the Hybrid instruments?").add(msgInitializeMenu)
					.add("The below Hybrid Instruments are initialized sequentially")
					.add("[Hybrid_1, Hybrid_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}

		@Test
		void testInitializeAllInstrument() {
			TestIO io = new TestIO(
					new ListHelper()
					.add("8").add("clientAutomatedTest")
					.add("7").add("2")
					.add(cmdExit)
					.list
					);

			executeClient(io);

			Iterator<String> iterator = io.getOutput().iterator();

			List<String> assertSet01 = new ListHelper()
					.add(msgMainMenu).add(msgGetFileName).add("The instruments are imported successfully from clientAutomatedTest file")
					.add(msgMainMenu).add("How would you like to Initialize the instruments?").add(msgInitializeMenu)
					.add("The below Instruments are initialized sequentially")
					.add("[DCVI_1, DCVI_2, Digital_1, Digital_2, Hybrid_1, Hybrid_2]")
					.list;			
			for(var assertLine: assertSet01)
				assertEquals(assertLine, iterator.next());

			String assertSet02 = "Time taken\\(ms\\): [\\d]*";
			assertTrue(iterator.next().matches(assertSet02));

			List<String> assertSet03 = new ListHelper()
					.add(msgMainMenu).add(msgExitMenu).add(msgThankyou)
					.list;
			for(var assertLine: assertSet03)
				assertEquals(assertLine, iterator.next());
		}
	}

	private void executeClient(TestIO io) {
		InstrumentUI client = new InstrumentUI(io);
		client.run();
		client.stop();
	}

	@Nested
	class ListHelperTest
	{
		@Test
		void testListHelper() {
			assertEquals(
					new ListHelper().add(List.of("Item1", "Item2")).add("Item3").add(List.of("Item4")).list,
					new ListHelper().add("Item1").add("Item2").add("Item3").add("Item4").list
					);
		}
	}

	private class TestIO implements LineIO {

		private List<String> output = new ArrayList<String>(10);
		private List<String> input = new ArrayList<String>(10);
		private int count;

		public TestIO(List<String> input) {
			this.input = input;
			count = 0;
		}

		@Override
		public String readLine() {
			return input.get(count++);
		}

		@Override
		public void writeLine(String Message) {
			output.add(Message);			
		}

		public List<String> getOutput() {
			return output;
		}
	}

	private class ListHelper {
		private List<String> list = new ArrayList<String>();

		public ListHelper() {
			list.clear();
		}

		public ListHelper add(List<String> newList) {
			this.list.addAll(newList);
			return this;
		}

		public ListHelper add(String newList) {
			this.list.add(newList);
			return this;
		}
	}
}
