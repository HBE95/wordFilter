package com.hbe.wordFilter.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface FileService {

	public List<String> readFile(String inputPath) throws IOException;

	public void writeFile(List<String> filteredWords, String outputPath) throws IOException;

	public void processFile(HashMap<String, String> params) throws IOException;

	public List<String> filterElement(List<String> words, String filter);

	public HashMap<String, String> initFileParams(String[] args) throws Exception;

}
