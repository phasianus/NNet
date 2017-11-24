package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import cz.gelion.nnet.Neuron.NeuronConnection;

public class Brain {

	
	Collection<Sensor> input;

	Collection<Neuron> output;	
	
	
	private void init(Integer... i) {
		
	}
	
	private void init(Collection<Sensor> sensors, Collection<Neuron> neurons) {
		input = sensors;
		for (Neuron n: neurons) {
			for (Sensor s: sensors) {
				NeuronConnection c = n.new NeuronConnection();
				s.output.add(c);
				n.input.add(c);
			}
		}
		output = neurons;
	}
	
	private void add(Collection<Neuron> neurons) {
		for (Neuron n: neurons) {
			for (Neuron i: output) {
				NeuronConnection c = n.new NeuronConnection();
				i.output.add(c);
				n.input.add(c);
			}
			
		}
		output = neurons;
	}
	
	
	
	static class BrainFactory {
		static Brain create() {

			Integer[] shape = new Integer[] {2,2,2};
			
			Brain b = new Brain();
			b.input = new ArrayList<>();
			
			
			for (int i=0; i<shape[0]; i++) {
				Sensor s  = new Sensor();
				b.input.add(s);
				
			}
			
			
			
			for (int i=1; i<shape.length; i++) {
				
			}
			
			
			return b;
		}
	}
	
	
}
