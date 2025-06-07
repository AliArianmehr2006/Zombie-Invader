package com.example.project;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundThread extends Thread {
    public void run() {

        while (true) {

            File soundFile = new File("src/main/resources/com/example/music/Inspiring Ambient Tune.wav");
            AudioInputStream audioIn = null;
            try {
                audioIn = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = null;
            try {
                line = (SourceDataLine) AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            try {
                line.open(format);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            line.start();
            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;
            while (true) {
                try {
                    if (!((bytesRead = audioIn.read(buffer, 0, buffer.length)) != -1)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                line.write(buffer, 0, bytesRead);
            }


            line.drain();
            line.stop();
            line.close();
        }


    }
}
