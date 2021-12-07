/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.solitontech.Benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.openjdk.jmh.annotations.Benchmark;

public class MyBenchmark {
	
	public static final int iteration = 50;

    @Benchmark
    public void testMethod() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
    }
    
    public static void testStreamTransferTo(File fileSource, File fileTarget) {
    	try(
    			FileInputStream source = new FileInputStream(fileSource);
    			FileOutputStream target = new FileOutputStream(fileTarget);
    	){
    		source.transferTo(target);
    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }    
    
    public static void testStreamReadWrite(File fileSource, File fileTarget) {
    	try(
    			FileInputStream source = new FileInputStream(fileSource);
    			FileOutputStream target = new FileOutputStream(fileTarget);
    	){
    		byte[] buffer = new byte[1024];
    		int length;
    		while((length = source.read(buffer)) > 0) {
    			target.write(buffer, 0, length);
    		}
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
    
    public static void testStreamChannel(File fileSource, File fileTarget) {
    	try(
    			var source = new FileInputStream(fileSource).getChannel();
    			var target = new FileOutputStream(fileTarget).getChannel();
    			){
    		source.transferTo(0, source.size(), target);
    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }    
    
    public static void testFilesCopy(File fileSource, File fileTarget) {
    	try {
			Files.copy(fileSource.toPath(), fileTarget.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
    
    public static void main(String args[]) {
    	copySync("testStreamTransferTo", (fileSource, fileTarget) -> testStreamTransferTo(fileSource, fileTarget));
    	copyAsync("testStreamTransferTo", (fileSource, fileTarget) -> testStreamTransferTo(fileSource, fileTarget));
    	copySync("testStreamReadWrite", (fileSource, fileTarget) -> testStreamReadWrite(fileSource, fileTarget));
    	copyAsync("testStreamReadWrite", (fileSource, fileTarget) -> testStreamReadWrite(fileSource, fileTarget));
    	copySync("testStreamChannel", (fileSource, fileTarget) -> testStreamChannel(fileSource, fileTarget));
    	copyAsync("testStreamChannel", (fileSource, fileTarget) -> testStreamChannel(fileSource, fileTarget));
    	copySync("testFilesCopy", (fileSource, fileTarget) -> testFilesCopy(fileSource, fileTarget));
    	copyAsync("testFilesCopy", (fileSource, fileTarget) -> testFilesCopy(fileSource, fileTarget));
    }
    
	private static void copySync(String message, Copyable copyable) {
		cleanUp();
		
		long startTime = System.currentTimeMillis();
    	for(int i = 0; i < iteration; i++)
    		copyable.copy(
    				new File("C:\\test\\sites-assignment\\input\\test.txt"), 
    				new File("C:\\test\\sites-assignment\\test\\target" + i + ".txt")
    				);
    	
    	long endTime = System.currentTimeMillis();
		System.out.println(message + " duration " + (endTime - startTime) + " ms");
	}
	
	private static void copyAsync(String message, Copyable copyable) {
		cleanUp();
		
		long startTime = System.currentTimeMillis();
		ArrayList<CompletableFuture<?>> tasks = new ArrayList<CompletableFuture<?>>(iteration);
		
		for(int i = 0; i < iteration; i++) {
			int count = i;
			Copyable copyable2 = copyable;
			tasks.add(CompletableFuture.runAsync(() -> copyable2.copy(
					new File("C:\\test\\sites-assignment\\input\\test.txt"), 
					new File("C:\\test\\sites-assignment\\test\\target" + count + ".txt")
					)));
		}

		for(var task : tasks)
			task.join();

		long endTime = System.currentTimeMillis();
		System.out.println(message + " duration " + (endTime - startTime) + " ms");
	}
	
	private static void cleanUp() {
		File targetDir = new File("C:\\test\\sites-assignment\\test\\");
		targetDir.mkdirs();
		for(var file : targetDir.listFiles())
			file.delete();
	}
	
	public interface Copyable {
		void copy(File fileSource, File fileTarget);
	}

}


/**
testStreamTransferTo duration 1699 ms
testStreamTransferTo duration 602 ms
testStreamReadWrite duration 6909 ms
testStreamReadWrite duration 2761 ms
testStreamChannel duration 585 ms
testStreamChannel duration 266 ms
testFilesCopy duration 966 ms
testFilesCopy duration 523 ms 

testStreamTransferTo duration 1720 ms
testStreamTransferTo duration 634 ms
testStreamReadWrite duration 6387 ms
testStreamReadWrite duration 2786 ms
testStreamChannel duration 573 ms
testStreamChannel duration 550 ms
testFilesCopy duration 960 ms
testFilesCopy duration 512 ms

testStreamTransferTo duration 1611 ms
testStreamTransferTo duration 581 ms
testStreamReadWrite duration 6667 ms
testStreamReadWrite duration 2506 ms
testStreamChannel duration 592 ms
testStreamChannel duration 429 ms
testFilesCopy duration 816 ms
testFilesCopy duration 439 ms

testStreamTransferTo duration 1649 ms
testStreamTransferTo duration 605 ms
testStreamReadWrite duration 6319 ms
testStreamReadWrite duration 2471 ms
testStreamChannel duration 593 ms
testStreamChannel duration 267 ms
testFilesCopy duration 901 ms
testFilesCopy duration 603 ms

testStreamTransferTo duration 1651 ms
testStreamTransferTo duration 674 ms
testStreamReadWrite duration 6403 ms
testStreamReadWrite duration 2547 ms
testStreamChannel duration 611 ms
testStreamChannel duration 319 ms
testFilesCopy duration 760 ms
testFilesCopy duration 549 ms
 */