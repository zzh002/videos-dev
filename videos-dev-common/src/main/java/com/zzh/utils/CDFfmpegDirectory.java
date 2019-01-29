package com.zzh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author ZZH
 * @date 2019/1/29 9:10
 **/
public class CDFfmpegDirectory {

    public static void getCover() throws IOException {
        List<String> command = new java.util.ArrayList<>();
        //cd /developer/ffmpeg/bin
        command.add("bash");
        command.add("-c");
        command.add("cd");
        command.add("/developer/ffmpeg/bin");

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
}
