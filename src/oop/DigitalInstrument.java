package oop;

public class DigitalInstrument extends Instrument {
	

	public DigitalInstrument(String instrumentName) {
		this.setInstrumentName(instrumentName);
		this.setInstrumentType("NI6570");
	}

	public DigitalInstrument(String instrumentName, String intrumentType) {
		
		this.setInstrumentName(instrumentName);
		this.setInstrumentType(intrumentType);
	}

	DigitalInstrument(DigitalInstrument dObj) {
		this.setInstrumentName(dObj.getInstrumentName());
		this.setInstrumentType(dObj.getInstrumentType());
	}

	public DigitalInstrument() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void establishChannelBasedCommunication(Instrument ins) {
	  //TODO
	}

	@Override
	public void establisInstrumentBasedCoomunication(Instrument ins) {
		System.out.println("Established Instrument Based Communication for the Digital Instrument - "+ins.getInstrumentName());		
	}
}
