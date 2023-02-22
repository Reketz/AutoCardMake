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

public class GeneratorCardMake {

    public static String SEPARATOR_TIME;
    public static void toGenerate(CardMake cardMake, int sceneNumber, int generateTo) throws IOException {
        SEPARATOR_TIME = cardMake.getSeparator().isEmpty() ? " --> " : cardMake.getSeparator();
        generatorCardMake(cardMake, sceneNumber, generateTo);
    }
    private static void generatorCardMake(CardMake cardMake, int sceneNumber, int generateTo) throws IOException {
        StringBuilder content = new StringBuilder();
        cardMake.getLinesFrom().add("");
        for (int i = 0; i < cardMake.getLinesFrom().size(); i++) {
            String line = cardMake.getLinesFrom().get(i);
            int number = convertLineToNumber(line);

            if (number == sceneNumber) {
                String[] time = cardMake.getLinesFrom().get(i + 1).split(cardMake.getSeparator());

                float startSeconds = getSecondsAndNano(time, 0);
                float endSeconds = getSecondsAndNano(time, 1);

                float key = getSeconds(time, 0);

                String textComplete = getTextComplete(cardMake.getLinesFrom(), i);

                String nameFile  = cardMake.getCutVideo().cut(cardMake.getPathVideoTarget(), cardMake.getPathOutput(), startSeconds, endSeconds);
                System.out.println("Corte criado: " + nameFile);
                content.append(createTextToFileTxt(cardMake.getLinesTo(), key, nameFile, textComplete));

                sceneNumber++;
            }

            if (number == generateTo){
                BufferedWriter br = new BufferedWriter(new FileWriter(cardMake.getPathVideoTarget() + ".txt"));
                br.write(content.toString());
                br.close();
                System.out.println("Finalizado a execução!");
                break;
            }
        }
    }
    private static String getTextComplete(List<String> lines, int i) {
        String textComplete = "";
        for (int j = i + 2; j < i + 10; j++) {
            String text = lines.get(j);
            if (text.isEmpty()) {
                break;
            }
            textComplete += text + " ";
        }
        return textComplete;
    }
    private static String createTextToFileTxt(List<String> linesTranslated, float key, String nameFile, String textComplete) throws IOException {
        String legendBr = getSubtitleTranslated(linesTranslated, key);
        return String.format("[sound:%s]|%s|%s\n", nameFile, textComplete.trim(), legendBr.trim());
    }
    private static float getSecondsAndNano(String[] time, int index) {
        String lineTime = time[index];
        LocalTime lineLocalTime = LocalTime.parse(lineTime, DateTimeFormatter.ofPattern("HH:mm:ss,SSS"));
        float seconds = new BigDecimal(lineLocalTime.toSecondOfDay() + "." + lineLocalTime.getNano()).floatValue();
        return seconds;
    }
    private static float getSeconds(String[] time, int index) {
        String lineTime = time[index];
        LocalTime lineLocalTime = LocalTime.parse(lineTime, DateTimeFormatter.ofPattern("HH:mm:ss,SSS"));
        float seconds = new BigDecimal(lineLocalTime.toSecondOfDay()).floatValue();
        return seconds;
    }
    private static String getSubtitleTranslated(List<String> linesTranslated, float key) {
        String textComplete = "";
        for (int i = 0; i < linesTranslated.size(); i++) {
            String line = linesTranslated.get(i);
            if(line.contains(SEPARATOR_TIME)) {
                String[] time = line.split(SEPARATOR_TIME);
                float secondsKey = getSeconds(time, 0);
                if(secondsKey == key) {
                    textComplete = getTextComplete(linesTranslated, i - 1);
                    return textComplete.trim();
                }
            }
        }

        return textComplete.trim();
    }
    private static int convertLineToNumber(String line) {
        int number = 0;
        try {
            number = Integer.parseInt(line.replaceAll("\\p{C}", ""));
        } catch (Exception e) {
        }
        return number;
    }
}
