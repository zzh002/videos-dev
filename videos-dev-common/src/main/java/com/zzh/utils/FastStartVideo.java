package com.zzh.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FastStartVideo {

	private String ffmpegEXE;

	public FastStartVideo(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void convertor(String videoInputPath, String videoOutputPath) throws Exception {
//		ffmpeg -i input.mp4 -movflags faststart -acodec copy -vcodec copy output.mp4

		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		command.add("-i");
		command.add(videoInputPath);
		command.add("-movflags");
		command.add("faststart");
		command.add("-acodec");
		command.add("copy");
		command.add("-vcodec");
		command.add("copy");
		command.add(videoOutputPath);
		
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
		FastStartVideo ffmpeg = new FastStartVideo("D:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmpeg.convertor("D:\\短视频\\3.mp4", "D:\\短视频\\123.mp4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
