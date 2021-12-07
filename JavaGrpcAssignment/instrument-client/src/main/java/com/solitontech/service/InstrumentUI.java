package com.solitontech.service;

import com.solitontech.instrumentserver.proto.InitializeOrder;
import com.solitontech.instrumentserver.proto.InstrumentType;
import com.solitontech.service.io.ConsoleIO;
import com.solitontech.service.io.LineIO;

public class InstrumentUI {
	
	private final LineIO io;
	private final InstrumentClient client;

	public InstrumentUI(LineIO io) {
		this.io = io;
		client = new InstrumentClient();
	}

	public static void main(String[] args) {
		InstrumentUI UI = new InstrumentUI(new ConsoleIO());
		UI.run();
		UI.stop();
	}
	
	public void run() {
		boolean stop = false;
		while(!stop) {
			switch (displayMainMenu()) {
			case INVALID:
				io.writeLine("Invalid choice!!");
				break;
			case EXIT:
				stop = displayExitScreen();
				break;
			case ADD_DCVI_INSTRUMENT:
				displayAddNewInstrument(InstrumentType.DCVI);
				break;
			case ADD_DIGITAL_INSTRUMENT:
				displayAddNewInstrument(InstrumentType.DIGITAL);
				break;
			case ADD_HYBRID_INSTRUMENT:
				displayAddNewInstrument(InstrumentType.HYBRID);
				break;
			case INITIALIZE_DCVI_INSTRUMENT:
				displayInitializeScreen(InstrumentType.DCVI);
				break;
			case INITIALIZE_DIGITAL_INSTRUMENT:
				displayInitializeScreen(InstrumentType.DIGITAL);
				break;
			case INITIALIZE_HYBRID_INSTRUMENT:
				displayInitializeScreen(InstrumentType.HYBRID);
				break;
			case INITIALIZE_ALL_INSTRUMENT:
				displayInitializeScreen();
				break;
			case IMPORT_FROM_JSON:
				importFromJSON();
				break;
			case EXPORT_TO_JSON:
				exportToJSON();
				break;

			default:
				break;
			}
		}
	}
	
	private MainChoices displayMainMenu() {
		io.writeLine("1. Add DCVI Instrument");
		io.writeLine("2. Add Digital Instrument");
		io.writeLine("3. Add Hybrid Instrument");
		io.writeLine("4. Initialize DCVI Instrument");
		io.writeLine("5. Initialize Digital Instrument");
		io.writeLine("6. Initialize Hybrid Instrument");
		io.writeLine("7. Initialize All Instrument");
		io.writeLine("8. Import From JSON");
		io.writeLine("9. Export To JSON");
		io.writeLine("10. Exit");
		io.writeLine("Enter a choice: ");
		
		return switch (io.readLine()) {
		case "1": yield MainChoices.ADD_DCVI_INSTRUMENT;
		case "2": yield MainChoices.ADD_DIGITAL_INSTRUMENT;
		case "3": yield MainChoices.ADD_HYBRID_INSTRUMENT;
		case "4": yield MainChoices.INITIALIZE_DCVI_INSTRUMENT;
		case "5": yield MainChoices.INITIALIZE_DIGITAL_INSTRUMENT;
		case "6": yield MainChoices.INITIALIZE_HYBRID_INSTRUMENT;
		case "7": yield MainChoices.INITIALIZE_ALL_INSTRUMENT;
		case "8": yield MainChoices.IMPORT_FROM_JSON;
		case "9": yield MainChoices.EXPORT_TO_JSON;
		case "10": yield MainChoices.EXIT;
		default: yield MainChoices.INVALID;
		};
	}
	
	private void displayAddNewInstrument(InstrumentType type) {
		String instrumentName = io.readLine("Please enter the instrument name: ");
		client.addInstrument(instrumentName, type);
		io.writeLine("The instrument " + instrumentName + " is added successfully");
	}
	
	private void displayInitializeScreen() {
		io.writeLine("How would you like to Initialize the instruments?");
		switch (displayInitializeMenu()) {
		case INVALID:
			io.writeLine("Invalid choice!!");
			break;
		case IN_SEQUENCE:
			clientInitializeInstrument(InitializeOrder.SEQUENCE);	
			break;		
		case IN_PARALLEL:
			clientInitializeInstrument(InitializeOrder.PARALLEL);
			break;
		default:
			break;
		}
	}

	private void displayInitializeScreen(InstrumentType type) {
		io.writeLine("How would you like to Initialize the " + instrumentTypeToString(type) + " instruments?");
		switch (displayInitializeMenu()) {
		case INVALID:
			io.writeLine("Invalid choice!!");
			break;
		case IN_SEQUENCE:
			clientInitializeInstrument(type, InitializeOrder.SEQUENCE);	
			break;		
		case IN_PARALLEL:
			clientInitializeInstrument(type, InitializeOrder.PARALLEL);
			break;
		default:
			break;
		}
	}

	private void clientInitializeInstrument(InitializeOrder order) {
		io.writeLine("The below Instruments are initialized sequentially");
		long startTime = System.currentTimeMillis();
		io.writeLine(client.initializeInstrument(order).toString());
		long endTime = System.currentTimeMillis();
		io.writeLine("Time taken(ms): " + (endTime - startTime));
	}

	private void clientInitializeInstrument(InstrumentType type, InitializeOrder order) {
		io.writeLine("The below " + instrumentTypeToString(type) + " Instruments are initialized sequentially");
		long startTime = System.currentTimeMillis();
		io.writeLine(client.initializeInstrument(type, order).toString());
		long endTime = System.currentTimeMillis();
		io.writeLine("Time taken(ms): " + (endTime - startTime));
	}	
	
	private InitializeInstrumentChoices displayInitializeMenu() {
		io.writeLine("1. In Sequence");
		io.writeLine("2. In Parallel");
		io.writeLine("Enter a choice: ");
		
		return switch (io.readLine()) {
		case "1": yield InitializeInstrumentChoices.IN_SEQUENCE;
		case "2": yield InitializeInstrumentChoices.IN_PARALLEL;
		default: yield InitializeInstrumentChoices.INVALID;
		};
	}
	
	private void importFromJSON() {
		String fileName = io.readLine("Please enter the file name: ");
		client.importFromJSON(fileName);
		io.writeLine("The instruments are imported successfully from " + fileName + " file");	
	}
	
	private void exportToJSON() {
		String fileName = io.readLine("Please enter the file name: ");
		client.exportToJSON(fileName);
		io.writeLine("The instruments are exported successfully to " + fileName + " file");	
	}
	
	private boolean displayExitScreen() {
		return switch (displayExitMenu()) {
		case INVALID :
			io.writeLine("Invalid choice!!");
			yield false;
		case NO :
			yield false;
		case YES : 
			io.readLine("Thank you!! Press any key!!");
			yield true;
		default:
			yield false;
		};
	}
	
	private ExitChoices displayExitMenu() {
		io.writeLine("Are you sure to exit the application?");
		io.writeLine("1. Yes");
		io.writeLine("2. No");
		io.writeLine("Enter a choice: ");
		
		return switch (io.readLine()) {
		case "1": yield ExitChoices.YES;
		case "2": yield ExitChoices.NO;
		default: yield ExitChoices.INVALID;
		};
	}
	
	public void stop() {
		client.stop();
	}
	
	private String instrumentTypeToString(InstrumentType instrumentType) {
		return switch (instrumentType) {
		case DCVI: yield "DCVI";
		case DIGITAL: yield "Digital";
		case HYBRID: yield "Hybrid";
		default: yield "";		
		};
	}
	
	enum MainChoices {
		INVALID,
		ADD_DCVI_INSTRUMENT,
		ADD_DIGITAL_INSTRUMENT,
		ADD_HYBRID_INSTRUMENT,
		INITIALIZE_DCVI_INSTRUMENT,
		INITIALIZE_DIGITAL_INSTRUMENT,
		INITIALIZE_HYBRID_INSTRUMENT,
		INITIALIZE_ALL_INSTRUMENT,
		IMPORT_FROM_JSON,
		EXPORT_TO_JSON,	
		EXIT
	}
	
	enum InitializeInstrumentChoices {
		INVALID,
		IN_SEQUENCE,
		IN_PARALLEL
	}
	
	enum ExitChoices {
		INVALID,
		YES,
		NO
	}

}
