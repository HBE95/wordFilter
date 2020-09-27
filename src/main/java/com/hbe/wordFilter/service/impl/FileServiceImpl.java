package com.hbe.wordFilter.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hbe.wordFilter.service.FileService;
import com.hbe.wordFilter.utils.Constants;

@Service("fileService")
public class FileServiceImpl implements FileService {

	private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

	/**
	 * Returns a list of words contained in a file
	 * 
	 * @param inputPath the absolute path to the file
	 * @return the list of words contained in the file
	 * 
	 * */
	public List<String> readFile(String inputPath) throws IOException {
		logger.info("reading file from path : " + inputPath);
		List<String> words = new ArrayList<String>();
		FileInputStream inputStream = null;
		Scanner scanner = null;
		try {
			inputStream = new FileInputStream(inputPath);
			scanner = new Scanner(inputStream);
			while (scanner.hasNextLine()) {
				words.add(scanner.nextLine());
			}
			logger.info("The list of extracted words :" + words);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw (e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return words;
	}
	
	/**
	 * Returns a filtered list of words
	 * 
	 * @param words a string list 
	 * @param element the element to be filtered out
	 * @return the filtered list of words
	 * 
	 * */
	public List<String> filterElement(List<String> words, String element) {
		List<String> filteredWords = words.stream().filter(word -> !word.contains(element))
				.collect(Collectors.toList());
		logger.info("The list of filtered words :" + filteredWords);
		return filteredWords;
	}
	
	/**
	 * Writes the output file 
	 * each word per line
	 * 
	 * @param filteredWords a string list of words
	 * @param outputPath the absolute path of the output file
	 * 
	 * */
	public void writeFile(List<String> filteredWords, String outputPath) throws IOException {
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		PrintWriter writer = null;
		try {
			fileOutputStream = new FileOutputStream(outputPath);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, Constants.ENCODING);
			writer = new PrintWriter(outputStreamWriter);
			for (String word : filteredWords) {
				writer.println(word);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw (e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Reads lines from a file
	 * filters some elements and writes the output file
	 * 
	 * @param params the input file path the output file path and the filter
	 * 
	 * */
	
	public void processFile(HashMap<String, String> params) throws IOException {
		List<String> words = this.readFile(params.get(Constants.INPUT_FILE_KEY));
		List<String> filteredWords = this.filterElement(words, params.get(Constants.FILTER_KEY));
		this.writeFile(filteredWords, params.get(Constants.OUTPUT_FILE_KEY));
	}

	/**
	 * Initializes the program parameters : input path - output path - filter
	 * 
	 * @param args the program arguments
	 * 
	 * */
	public HashMap<String, String> initFileParams(String[] args) throws IllegalArgumentException {
		HashMap<String, String> params = new HashMap<String, String>();
		// Working inside IDE case
		if (args.length == 0) {
			params.put(Constants.INPUT_FILE_KEY, Constants.DEFAULT_INPUT_FILE);
			params.put(Constants.OUTPUT_FILE_KEY, Constants.DEFAULT_OUTPUT_FILE);
			params.put(Constants.FILTER_KEY, Constants.DEFAULT_FILTER);
		}
		// Working from JAR file case
		else if (args.length == 3) {
			params.put(Constants.INPUT_FILE_KEY, args[0]);
			params.put(Constants.OUTPUT_FILE_KEY, args[1]);
			params.put(Constants.FILTER_KEY, args[2]);
		} else {
			throw new IllegalArgumentException("The number of arguments does not match the program requirements");
		}
		return params;
	}

}
