package controller;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
public enum SoundEffect {
   EXPLODE("src/controller/audio/Explode.wav"),
   FUSE("src/controller/audio/Fuse.wav"),
   ERROR("src/controller/audio/Error.wav"),
   CORRECT("src/controller/audio/Correct.wav"),
   SPIN("src/controller/audio/Spin.wav"),
   HINT("src/controller/audio/Hint.wav"),
   START("src/controller/audio/Start.wav"),
   WIN("src/controller/audio/Win.wav"),
   LOSE("src/controller/audio/Lose.wav");
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         File fin = new File(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fin);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
    	  if(clip.isRunning()){
        	 clip.stop();   // Stop the player if it is still running
         }
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }
   
   // Play the sound for a duration
   public void loop(int dur) {
	      if (volume != Volume.MUTE) {
	    	  if(clip.isRunning()){
	        	 clip.stop();   // Stop the player if it is still running
	         }
	         clip.setFramePosition(0); // rewind to the beginning
	         if(dur > 0){
	        	 clip.loop(dur);
	         } else {
	        	 clip.loop(Clip.LOOP_CONTINUOUSLY);
	         }
	      }
	   }
   
   // Stop the current sound
   public void stop(){
	   clip.stop();
   }
}