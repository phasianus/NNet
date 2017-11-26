package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neuron implements Cell {
	

	static final double LEARN_CONST = .5d;
	
	static Logger log = LoggerFactory.getLogger(Neuron.class);
	
	String id;

	Collection<NeuronConnection> input = new ArrayList<>();
	
	Collection<NeuronConnection> output = new ArrayList<>();
	
	ActivationFunction activationFunction;
	
	Double bias = Math.random();
	
	Double target;
	
	public Neuron(String id, ActivationFunction f) {
		this(f);
		log.trace("^this(" + id + ")");
		this.id = id;
	}
	
	public Neuron(ActivationFunction f)  {
		this.activationFunction = f;
	}
	
	public Double calc() {
		log.trace(this + ".calc()");
		return activationFunction.apply(sum());
	}
	
	public Double sum() {
		log.trace(this + ".sum()");
		Double o = 0d;
		for (NeuronConnection c: input) {
			o = o + c.sum();
		};
		return o + bias;
	}
	
	
	
	
	Double endCorr(Double target, NeuronConnection conn) {
		return conn.weight - (LEARN_CONST * (activationFunction.deriv(calc()) * conn.input.calc()));
	}
	
	
	Double error() {
		return error(target);
	}
	
	
	Double error(Double target) {
		return (Math.sqrt(target - calc()) / 2); 
	}
	
	
	@Override
	public String toString() {
		return String.format("Neuron{%s}", id);
	}
	
	class NeuronConnection {
		
		
		public NeuronConnection(Cell input, Cell output) {
			this.input = input;
			this.output = output;
		}
		
		Double weight = Math.random();
		Cell input, output;
		public Double sum() {
			log.trace(this + ".sum()");
			return input.calc() * weight;
		}
		
		@Override
		public String toString() {
			return String.format("NeruonConnection{[%s]->[%s]", input,output);
		}
		
	
		
	}
	
	interface ActivationFunction {
		Double apply(Double c);
		
		Double deriv(Double c);
	}
	
	
	
	
	
}
