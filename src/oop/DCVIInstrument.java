package oop;

public class DCVIInstrument extends Instrument {


	public DCVIInstrument() {
		// TODO Auto-generated constructor stub
	}

	public DCVIInstrument(String instrumentName) {
		this.setInstrumentName(instrumentName);
		this.setInstrumentType("DPS4145");
	}

	public DCVIInstrument(String instrumentName, String intrumentType) {
		this.setInstrumentName(instrumentName);
		this.setInstrumentType(intrumentType);
	}

	DCVIInstrument(DCVIInstrument dObj) {
		this.setInstrumentName(dObj.getInstrumentName());
		this.setInstrumentType(dObj.getInstrumentType());
	}

	@Override
	public void establishChannelBasedCommunication(Instrument ins) {
		System.out.println("Established Channel Based Communication for the DCVI Instrument - "+ins.getInstrumentName());		
	}

	@Override
	public void establisInstrumentBasedCoomunication(Instrument ins) {
		// TODO Auto-generated method stub
		
	}

}
