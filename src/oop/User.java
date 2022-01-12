package oop;

import java.util.List;

public class User {

	private List<Instrument> insList;
	private HybridInstrument hybridIns;

	public User(List<Instrument> insList, HybridInstrument hybIns) {
		this.insList = insList;
		this.hybridIns = hybIns;
	}
	
	public void connect() {
		for(Instrument insItem:insList) {
		  establishConnection(insItem);
		}
	}
	

	public void establishConnection(Instrument ins) {
		hybridIns.establishChannelBasedCommunication(ins);
		hybridIns.establisInstrumentBasedCoomunication(ins);
		
	}
	
}
