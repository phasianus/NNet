package cz.gelion.nnet;

import java.util.Collection;

public class Neuron {

	Collection<NeuronConnection> input;
	
	Collection<NeuronConnection> output;
	
	ActivationFunction activationFunction;
	
	Double bias = Math.random();
	
	public Double calc() {
		return activationFunction.apply(sum());
	}
	
	public Double sum() {
		Double o = 0d;
		for (NeuronConnection c: input) {
			o = o + c.sum();
		};
		return o + bias;
	}
	
	class NeuronConnection {
		Double weight = Math.random();
		Neuron input;
		public Double sum() {
			return input.sum() * weight;
		}
	}
	
	interface ActivationFunction {
		Double apply(Double c);
	}
	
	
	
}
