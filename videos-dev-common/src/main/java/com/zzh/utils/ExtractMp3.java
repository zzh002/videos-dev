package com.zzh.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExtractMp3 {

	private String ffmpegEXE;

	public ExtractMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void convertor(String videoInputPath, String mp3OutputPath) throws Exception {
//		ffmpeg -i input.mp4 -f mp3 output.mp3

		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		command.add("-i");
		command.add(videoInputPath);
		command.add("-f");
		command.add("mp3");
		command.add(mp3OutputPath);
		
		for (String c : command) {
			System.out.print(c + " ");
		}
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
		
	}

	public static void main(String[] args) {
		ExtractMp3 ffmpeg = new ExtractMp3("D:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmpeg.convertor("D:\\短视频\\4.mp4", "D:\\短视频\\14.mp3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
