package com.solitontech.instrument;

import com.solitontech.instrumentserver.proto.InstrumentType;

public class InstrumentHybrid extends InstrumentAbstract {

	public InstrumentHybrid(String name, InstrumentType type) {
		super(name, type);
	}

	@Override
	public String initialize() {		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			return "Failed to initialize";
		}
		
		return getName();
	}

}
