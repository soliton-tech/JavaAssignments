package com.solitontech.instrument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solitontech.instrumentserver.proto.InstrumentInfo;
import com.solitontech.instrumentserver.proto.InstrumentType;

public class InstrumentCollection implements Iterable<InstrumentAbstract> {

	private ArrayList<InstrumentAbstract> instruments = new ArrayList<>();
	
	public void addInstrument(InstrumentInfo instrumentInfo) {
		instruments.add(
				InstrumentAbstract.Builder.newBuilder()
				.setName(instrumentInfo.getName())
				.setType(instrumentInfo.getType())
				.build()
				);
	}
	
	public List<String> initializeInstrument() {
		return instruments.stream()
				.map(InstrumentAbstract::initialize)
				.toList();
	}

	public List<String> initializeInstrument(InstrumentType instrumentType) {
		return instruments.stream()
				.filter(instrument -> instrument.getType().equals(instrumentType))
				.map(InstrumentAbstract::initialize)
				.toList();
	}
	
	public List<String> initializeInstrumentAsync() {
		var futures = instruments.stream()
				.map(instrument -> CompletableFuture.supplyAsync(instrument::initialize))
				.toList();

		return futures.stream()
				.map(future -> future.join())
				.toList();
	}
	
	public List<String> initializeInstrumentAsync(InstrumentType instrumentType) {
		var futures = instruments.stream()
				.filter(instrument -> instrument.getType().equals(instrumentType))
				.map(instrument -> CompletableFuture.supplyAsync(instrument::initialize))
				.toList();
		
		return futures.stream()
				.map(future -> future.join())
				.toList();
	}
	
	public void exportInstruments(String fileName) {
		JsonMapper mapper = new JsonMapper();
		try {
			mapper.writeValue(new File(getFileName(fileName)), instruments);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void importInstruments(String fileName) {
		JsonMapper mapper = new JsonMapper();
		try {
			instruments = mapper.readValue(new File(getFileName(fileName)), new TypeReference<ArrayList<InstrumentAbstract>>() {});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Iterator<InstrumentAbstract> iterator() {
		return instruments.iterator();
	}

	@Override
	public String toString() {
		return instruments.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n"));
	}
	
	private String getFileName(String fileName) {
		String dir = "saved instruments" + File.separator;
		File file = new File(dir);
		file.mkdirs();
		return dir + fileName + ".instrument";
	}

}
