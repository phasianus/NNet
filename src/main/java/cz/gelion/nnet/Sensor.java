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
	
	@Override
	public String getId() {
		return id;
	}
	
	public Double calc() {
		return input;
	}
	
	
	public void setValue(Double d) {
		input =d;
	}
	
	@Override
	public String toString() {
		return String.format("Sensor{%s}",  id);
	}
	
}
