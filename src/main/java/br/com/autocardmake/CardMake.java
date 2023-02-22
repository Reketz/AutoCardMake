package br.com.autocardmake;

import java.util.List;

public class CardMake {
    private String separator;
    private String pathVideoTarget;
    private String pathOutput;
    private List<String> linesFrom;
    private List<String> linesTo;
    private CutVideo cutVideo;

    public CardMake(String separator, String pathVideoTarget, String pathOutput, List<String> linesFrom, List<String> linesTo, CutVideo cutVideo) {
        this.separator = separator;
        this.pathVideoTarget = pathVideoTarget;
        this.pathOutput = pathOutput;
        this.linesFrom = linesFrom;
        this.linesTo = linesTo;
        this.cutVideo = cutVideo;
    }

    public String getSeparator() {
        return separator;
    }

    public String getPathVideoTarget() {
        return pathVideoTarget;
    }

    public String getPathOutput() {
        return pathOutput;
    }

    public List<String> getLinesFrom() {
        return linesFrom;
    }

    public List<String> getLinesTo() {
        return linesTo;
    }

    public CutVideo getCutVideo() {
        return cutVideo;
    }
}
