package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.NoAutoStartUtil;

public class Neuron implements Cell {
	

	static final double LEARN_CONST = .5d;
	
	static Logger log = LoggerFactory.getLogger(Neuron.class);
	
	String id;

	Collection<NeuronConnection> input = new ArrayList<>();
	
	Collection<NeuronConnection> output = new ArrayList<>();
	
	ActivationFunction activationFunction;
	
	Double bias = Math.random();
	
	boolean calculated;
	Double calc;
	
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
		if (calculated) return calc;
		
		calc  = activationFunction.apply(sum());
		calculated = true;
		log.trace("\t...calculated :" + calc);
		return calc;
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
	
	
	
	
	Double error(Double target) {
		return (Math.sqrt(target - calc()) / 2); 
	}
	
	
	@Override
	public String toString() {
		return String.format("Neuron{%s}", id);
	}
	
	
	
	
	class NeuronConnection {
		
		Double diff;
		Double calc;
		
		Boolean computed = false;
		
		public NeuronConnection(Cell input, Neuron output) {
			this.input = input;
			this.output = output;
		}
		
		Double weight = Math.random();
		Cell input;
		Neuron output;
		
		
		public Double sum() {
			log.trace(this + ".sum()");
			return input.calc() * weight;
		}
		
		@Override
		public String toString() {
			return String.format("NeruonConnection{[%s]->[%s]", input,output);
		}
		
		Double diff() {
			if (diff  == null)  {
				Double r = 0d;
				for (NeuronConnection c: output.output) r = r + c.diff();
				diff = r * activationFunction.deriv(output.calc()) * input.calc();
				log.trace(this + " calcualted diff: " + diff);
				weight = weight -LEARN_CONST * diff;
			}
			return diff;
		}
		
		Double diff(Double target) {
			log.trace(this + "calculating diff from " + target);
			diff = -(target * output.calc()) * activationFunction.deriv(output.calc()) * input.calc();
			weight = weight -LEARN_CONST * diff;
			return diff;
		}
	
		
	
	}
}
