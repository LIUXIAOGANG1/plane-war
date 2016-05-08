package com.game.plane.war.part;
import java.io.*;
import javax.sound.midi.*;

public class MusicPlayer {
	private File file;
	private Sequence sequence;
	private Sequencer sequencer;
	public MusicPlayer(){
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		}catch(MidiUnavailableException ex){
			
		}
	}
	public void loadMusic(String name){
		file = new File(name);
		try{
			sequence = MidiSystem.getSequence(file);
			
		}catch(InvalidMidiDataException ex){
			
		}catch(IOException ex){
			
		}
	}
	public void playMusic(){
		try {
			sequencer.setSequence(sequence);
		} catch (InvalidMidiDataException e) {
			
			e.printStackTrace();
		}
		sequencer.start();
	}
	public void stopPlaying(){
		sequencer.stop();
	}
	public boolean isStopped(){
		return !sequencer.isRunning(); 
	}

}

