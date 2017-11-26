package cz.gelion.nnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.BasicConfigurator;

public class Run {
	
	static Logger log = LoggerFactory.getLogger(Run.class);

	public static void main(String[] args) {
		
		
		Brain brain = new Brain();
		brain.init(2,3,3,1);
		log.trace("Brain constructed: " + brain);

		brain.output.forEach(n -> {
			log.trace("output: " + n);
			n.calc();
		});
		
	}

}
