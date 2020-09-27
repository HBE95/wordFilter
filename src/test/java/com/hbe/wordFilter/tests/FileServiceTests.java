package com.hbe.wordFilter.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.hbe.wordFilter.service.FileService;
import com.hbe.wordFilter.service.impl.FileServiceImpl;
import com.hbe.wordFilter.utils.Constants;

public class FileServiceTests {

	private FileService fileService = new FileServiceImpl();

	@Test
	public void readFileShouldReturnWordsContainedInInputFile() throws IOException {
		List<String> words = fileService.readFile(Constants.DEFAULT_INPUT_TEST_FILE);
		assertEquals(26, words.size());
		assertEquals("Hello", words.get(0));
		assertEquals("developer", words.get(words.size() - 2));
	}

	@Test
	public void filterElementShouldFilterElementsFromListOfWords() {
		List<String> words = Arrays.asList("APPLE", "BANANA", "ORANGE", "KIWI");
		List<String> filteredWords = fileService.filterElement(words, "AN");
		assertEquals(2, filteredWords.size());
		assertEquals(filteredWords.get(0), "APPLE");
		assertEquals(filteredWords.get(filteredWords.size() - 1), "KIWI");
	}

	@Test
	public void writeFileShouldWriteFilteredWordsInOutputFile() throws IOException {
		List<String> filteredWords = Arrays.asList("APPLE", "KIWI");
		fileService.writeFile(filteredWords, Constants.DEFAULT_OUTPUT_TEST_FILE);
		List<String> words = fileService.readFile(Constants.DEFAULT_OUTPUT_TEST_FILE);
		assertEquals(2, words.size());
		assertEquals(filteredWords.get(0), "APPLE");
		assertEquals(filteredWords.get(filteredWords.size() - 1), "KIWI");
	}

	@Test
	public void processFileShouldReadFileFilterWordsAndWriteOutputFile() throws IOException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(Constants.INPUT_FILE_KEY, Constants.DEFAULT_INPUT_TEST_FILE);
		params.put(Constants.OUTPUT_FILE_KEY, Constants.DEFAULT_OUTPUT_TEST_FILE);
		params.put(Constants.FILTER_KEY, Constants.DEFAULT_FILTER);
		fileService.processFile(params);
		List<String> words = fileService.readFile(Constants.DEFAULT_OUTPUT_TEST_FILE);
		assertEquals(25, words.size());
	}

	@Test
	public void initFileParamsShouldReturnCorrectAppParams() throws Exception {
		String[] args = { "/inputPath", "/outputPath", "myFilter" };
		HashMap<String, String> params = fileService.initFileParams(args);
		assertEquals(3, params.size());
		assertEquals("/inputPath", params.get(Constants.INPUT_FILE_KEY));
		assertEquals("/outputPath", params.get(Constants.OUTPUT_FILE_KEY));
		assertEquals("myFilter", params.get(Constants.FILTER_KEY));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void initFileParamsShouldThrowIllegalArgumentExceptionWhenUnmatchedNumberOfArguments() throws Exception {
		String[] args = {"input"};
		fileService.initFileParams(args);
	}
}
