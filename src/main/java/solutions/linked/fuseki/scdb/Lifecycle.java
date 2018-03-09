package solutions.linked.fuseki.scdb;


import java.util.logging.Logger;

import org.apache.jena.sparql.core.describe.DescribeHandlerRegistry;
import org.apache.jena.system.JenaSubsystemLifecycle;

public class Lifecycle implements JenaSubsystemLifecycle {
    Logger log = Logger.getLogger(Lifecycle.class.getName());

    @Override
    public void start() {
        System.out.println("Starting lifecycle");
        log.severe("starting");
        DescribeHandlerRegistry.get().add(new ScbdDescribeHandlerFactory());
    }

    @Override
    public void stop() {
        System.out.println("Stopping lifecycle");
    }

    @Override
    public int level() {
        return 41;
    }
}