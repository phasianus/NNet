package cz.gelion.nnet.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cz.gelion.nnet.Brain;

public class TeachAND {

	static Logger log = LoggerFactory.getLogger(TeachAND.class);
	
	public static void main(String[] args) {
		
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.TRACE);
		loggerContext.getLogger(TeachAND.class).setLevel(Level.TRACE);
		
		Brain brain = new Brain();
		brain.init(2,2,1);
		
		
		Double[] data  = new Double[] { 
				1d,1d,1d,
				0d,0d,0d,
				0d,1d,0d,
				1d,0d,0d
		};
		
		
		int n = 2;
		while (n > 0) {
			int i=0;
			Double err = 0d;
			StringBuilder sb = new StringBuilder();
			for (i=0; i<data.length;) {
				Collection<Double> input = Arrays.asList(data[i++], data[i++]);
				Double target  = data[i++];
				Collection<Double> output = Arrays.asList(target);
				sb.append(String.format(" %s=>%s:", input,output));
				brain.learn(input, output);
				Iterator<Double> ti = output.iterator();
				brain.output(o -> {
					sb.append(String.format(" [%s, %s]", o.calc(), o.error(ti.next())));
			//		log.trace("output: " + o.calc() + ", target:" + target + ", err: " + brain.error(target));
				});
				err = err + brain.error(target);
			}
			log.trace("Error: " +  err/i + ", " + sb.toString());
			n--;
		}
		
		
	}
	
	
	
}
