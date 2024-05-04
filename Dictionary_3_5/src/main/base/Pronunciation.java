package base;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Pronunciation {
    static Voice voice;
    public void pronunciation(String word) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(160);
                voice.setStyle("casual");
                voice.setVolume(5);// Setting the volume of the voice
                voice.speak(word);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
    public static void main(String[] args) {
        Pronunciation p = new Pronunciation();
        p.pronunciation("amazing");

    }
}

