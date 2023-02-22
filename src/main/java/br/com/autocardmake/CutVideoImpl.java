package br.com.autocardmake;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class CutVideoImpl implements CutVideo{
    @Override
    public String cut(String sourceFile, String pathOutput, float start, float end) {
        try {
            File source = new File(sourceFile);
            String fileNameWithoutExtension = source.getName().replaceAll("\\.\\w+$", "");
            String fileNameOutput = fileNameWithoutExtension+start+end;
            File target = new File(pathOutput.trim()+"/"+fileNameOutput+".mp4");

            AudioAttributes audio = new AudioAttributes();
            VideoAttributes video = new VideoAttributes();

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setOffset(start);
            attrs.setDuration(end - start + 0.300f);
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);

            return target.getName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
