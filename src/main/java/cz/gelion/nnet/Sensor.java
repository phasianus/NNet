package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Collection;

import cz.gelion.nnet.Neuron.NeuronConnection;

public class Sensor implements Cell {

	String id;
	
	public Sensor(String id) {
		this.id  = id;
	}
	
	Collection<NeuronConnection> output = new ArrayList<>();
	
	Double input = 0d;
	
	public Double calc() {
		return input;
	}
	
	
	@Override
	public String toString() {
		return String.format("Sensor{%s}",  id);
	}
	
}