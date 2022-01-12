package oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationEngineer {

	public static void main(String args[]) {


		//Just to keep the code clear to understand, didn't use loops and list for initializing instruments
		
		//Initializing DCVI instruments
		DCVIInstrument dcvi1 = new DCVIInstrument("Instr45");
		DCVIInstrument dcvi2 = new DCVIInstrument("Instr46","DPS4146");
		DCVIInstrument dcvi3 = new DCVIInstrument("Instr47","DPS4147");

		//Initializing Digi instruments
		DigitalInstrument digi1 = new DigitalInstrument("Instr70");
		DigitalInstrument digi2 = new DigitalInstrument("Instr71","NI6571");
		DigitalInstrument digi3 = new DigitalInstrument("Instr71","NI6572");

		//cloning both the instrument types with copy constructor  -- Assignment 1
		DCVIInstrument dcvi4 = new DCVIInstrument(dcvi1);
		DigitalInstrument digi4 = new DigitalInstrument(digi1);

		//trying to change the cloned object and witness the changes
		((Instrument)dcvi4).setInstrumentName("clonedDCVI");
		((Instrument)digi4).setInstrumentName("clonedDigi");
		System.out.println(((Instrument)dcvi1).getInstrumentName()+" , "+((Instrument)dcvi1).getInstrumentType());
		System.out.println(((Instrument)dcvi4).getInstrumentName()+" , "+((Instrument)dcvi4).getInstrumentType());
		System.out.println(((Instrument)digi1).getInstrumentName()+" , "+((Instrument)digi1).getInstrumentType());
		System.out.println(((Instrument)digi4).getInstrumentName()+" , "+((Instrument)digi4).getInstrumentType());
		
		
		List<Instrument> dcviInsList = new ArrayList<Instrument>(4);
		Collections.addAll(dcviInsList, dcvi1, dcvi2, dcvi3, dcvi4);

		List<Instrument> digiInsList = new ArrayList<Instrument>(4);
		Collections.addAll(digiInsList, digi1, digi2, digi3, digi4);
		
		//sending the created instruments to connect via interface    -- Assignment 2
		User dcviInstruments = new User(dcviInsList, new DCVIInstrument());
		dcviInstruments.connect();
		User digiInstruments = new User(digiInsList, new DigitalInstrument());
		digiInstruments.connect();

	}

}
