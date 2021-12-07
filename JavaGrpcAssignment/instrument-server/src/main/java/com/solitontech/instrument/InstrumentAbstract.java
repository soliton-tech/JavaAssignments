package com.solitontech.instrument;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.solitontech.instrumentserver.proto.InstrumentType;

@JsonDeserialize(builder = InstrumentAbstract.Builder.class)
public abstract class InstrumentAbstract implements Instrument{
	private String name;
	private final InstrumentType type;
	
	public InstrumentAbstract(String name, InstrumentType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public InstrumentType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + type + "]";
	}
	

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	static class Builder {
		private String name = null;
		private InstrumentType type = null;
		
		public static Builder newBuilder() {
			return new Builder();
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setType(InstrumentType type) {
			this.type = type;
			return this;
		}
		
		public InstrumentAbstract build() {			
			if(name == null)
				throw new IllegalArgumentException("Set valid name using setName()");
			
			if(type == null)
				throw new IllegalArgumentException("Set valid type using setType()");
			
			return switch (type) {
			case DCVI: yield new InstrumentDCVI(name, type);
			case DIGITAL: yield new InstrumentDigital(name, type);
			case HYBRID: yield new InstrumentHybrid(name, type);
			default:
				throw new IllegalArgumentException("Unexpected type: " + type);
			};
		}
	}

}
