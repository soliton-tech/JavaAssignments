package com.solitontech.instrument;

import com.solitontech.instrumentserver.proto.InstrumentType;

public class InstrumentDCVI extends InstrumentAbstract {

	public InstrumentDCVI(String name, InstrumentType type) {
		super(name, type);
	}

	@Override
	public String initialize() {		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return "Failed to initialize";
		}
		
		return getName();
	}

}
