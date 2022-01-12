package oop;

public class Instrument implements HybridInstrument {

	private String instrumentName;
	private String instrumentType;
	
	
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	@Override
	public void establishChannelBasedCommunication(Instrument ins) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void establisInstrumentBasedCoomunication(Instrument ins) {
		// TODO Auto-generated method stub
		
	}
	
}
