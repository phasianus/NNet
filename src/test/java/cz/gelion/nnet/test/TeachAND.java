package cz.gelion.nnet.test;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cz.gelion.nnet.Brain;

public class TeachAND {

	static Logger log = LoggerFactory.getLogger(TeachAND.class);
	
	public static void main(String[] args) {
		
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.ERROR);;
	
		loggerContext.getLogger(TeachAND.class).setLevel(Level.TRACE);
		
		Brain brain = new Brain();
		brain.init(2,3,3,1);
		
		
		Double[] data  = new Double[] { 
				0d,0d,0d,
				0d,1d,0d,
				1d,0d,0d,
				1d,1d,1d
		};
		
		
		int n = 100;
		while (n > 0) {
		for (int i=0; i<data.length;) {
			Collection<Double> input = Arrays.asList(data[i++], data[i++]);
			Double target  = data[i++];
			Collection<Double> output = Arrays.asList(target);
			log.trace(String.format("Teaching by %s, %s", input,output));
			brain.learn(input, output);
			brain.output(o -> {
				log.trace("output: " + o.calc() + ", target:" + target + ", err: " + brain.error(target, o));
			});
			log.trace("error: " +  brain.error(target));
			
		}
		n--;
		}
	}
	
	
}
