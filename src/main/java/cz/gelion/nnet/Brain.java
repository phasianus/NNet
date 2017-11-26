package cz.gelion.nnet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.gelion.nnet.Neuron.ActivationFunction;
import cz.gelion.nnet.Neuron.NeuronConnection;

public class Brain {

	static Logger log = LoggerFactory.getLogger(Brain.class);
	
	Collection<Sensor> input;

	Collection<Neuron> output;	
	
	ActivationFunction activationFuction = new ActivationFunction() {
		
		@Override
		public Double apply(Double c) {
			log.trace(String.format("AtivationFunction.apply(%s)", c));
			return 1/Math.pow(Math.E, -c);
		}
		
		public Double deriv(Double c) {
			return apply(c) * (1 - apply(c));
		};
	};
	
	
	public void init(Integer... shape) {
		List<Sensor> sensors = new ArrayList<>(shape[0]);
		List<Neuron> neurons = new ArrayList<>(shape[1]);
		
		int x =1,y =1;

		log.trace("Creating sensors...");
		for (int i=0; i<shape[0]; i++) {
			sensors.add(new Sensor(String.valueOf(i+1)));
		}
		log.trace("\tcreated: "  + sensors.size());
		log.trace("Creating first neurons...");
		for (int i=0; i<shape[1]; i++) {
			
			Neuron n = new Neuron(String.format("%d,%d", x,y++),activationFuction);
			neurons.add(n);
		}
		log.trace("\tcreated: " + neurons.size());
		
	
		init(sensors,neurons);
		
		
		for (int i=2; i<shape.length; i++) {
			x++;y=1;
			log.trace("Creating neurons...");
			List<Neuron> nn = new ArrayList<>(shape[i]);
			
			for (int j=0; j<shape[i]; j++) nn.add(new Neuron(String.format("%d,%d", x,y++), activationFuction));
			log.trace("\tcreated: " + nn.size());
			add(nn);
		}
	}
	
	private void init(Collection<Sensor> sensors, Collection<Neuron> neurons) {
		input = sensors;
		for (Neuron n: neurons) {
			for (Sensor s: sensors) {
				NeuronConnection c = n.new NeuronConnection(s,n);
				s.output.add(c);
				n.input.add(c);
			}
		}
		output = neurons;
	}
	
	private void add(Collection<Neuron> neurons) {
		for (Neuron n: neurons) {
			for (Neuron i: output) {
				NeuronConnection c = n.new NeuronConnection(i, n);
				i.output.add(c);
				n.input.add(c);
			}
			
		}
		output = neurons;
	}
	
	
	Double error(Double target, Neuron out) {
		return Math.sqrt(target - out.calc()) / 2;
	}
	
	
	Double error(Double...d) {
		Iterator<Neuron> i = output.iterator();
		Double o = 0d;
		for(Double dd: d) o = o+error(dd, i.next());
		return o;
	}
	
	
	Double correction(Double target, Neuron n, Double input) {
		return ((target - n.calc()) * -1) * (n.calc() * (1 - n.calc())) * (input) ;
	}
	
	
}
