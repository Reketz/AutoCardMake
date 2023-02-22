package br.com.autocardmake;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Guilherme Estevao
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String resourcesPath = "C:\\temp\\generator\\03\\";

        String subtitleUS = "C:\\temp\\generator\\03\\S01E03US.srt";
        String subtitleBr = "C:\\temp\\generator\\03\\S01E03PT.srt";

        List<String> linesFrom = getFileAsList(subtitleUS, null);
        List<String> linesTo = getFileAsList(subtitleBr, null);

        CutVideo cutVideo = new CutVideoImpl();

        String pathVideo = resourcesPath + "S1E3.mkv";
        String pathOutput = "C:\\temp\\generator\\03\\output";
        String separator_time =  " --> ";

        CardMake cardMake = new CardMake(separator_time, pathVideo, pathOutput, linesFrom, linesTo, cutVideo);
        GeneratorCardMake.toGenerate(cardMake, 1, 353);
    }

    private static List<String> getFileAsList(String subtitlePath, Charset encode) throws IOException {
        Path subtitle = Paths.get(subtitlePath);
        List<String> lines = null;
        lines = encode == null ? Files.readAllLines(subtitle) : Files.readAllLines(subtitle, encode) ;
        return lines;
    }

}
