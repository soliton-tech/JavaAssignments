package com.solitontech.instrument;

import com.solitontech.instrumentserver.proto.InstrumentType;

public class InstrumentDigital extends InstrumentAbstract {

	public InstrumentDigital(String name, InstrumentType type) {
		super(name, type);
	}

	@Override
	public String initialize() {		
		try {
			Thread.sleep(1250);
		} catch (InterruptedException e) {
			return "Faild to initialize";
		}
		
		return getName();
	}

}
