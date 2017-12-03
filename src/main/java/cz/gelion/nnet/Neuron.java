package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
	
	@Override
	public String getId() {
		return id;
	}
	
	public Double calc() {
		log.trace(this + ".calc()");
		if (calculated) return calc;
		
		calc  = activationFunction.apply(sum());
		calculated = true;
		log.trace(this + " calculated :" + calc);
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
	
	public Double error(Double target) {
		return (Math.pow(target - calc(), 2) / 2); 
	}
	
	@Override
	public String toString() {
		return String.format("Neuron{%s, bias=%s,calc=%s}", id,bias,calc);
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
			return String.format("NeruonConnection{[%s]->[%s], w=%s}", input.getId(), output.getId(), weight);
		}
		
		Double diff() {
			if (diff  == null)  {
				log.trace("Calculating diff...");
				return diff(()-> {
					Double r = 0d;
					for (NeuronConnection c: output.output) r = r + c.diff();
					return r;
				});
			}
			return diff;
		}
		
		Double diff(Supplier<Double> sup) {
			
			diff = sup.get() * activationFunction.deriv(output.calc()) * input.calc();
			weight = weight -(LEARN_CONST * diff);
			log.trace(this + " calculated diff: " + diff);
			return diff;
		}
		
		Double diff(Double target) {
			log.trace(this + " calculating diff from " + target + "...");
			return diff(() -> {
				return -(target - output.calc());
			});
			
		}
	}
}
