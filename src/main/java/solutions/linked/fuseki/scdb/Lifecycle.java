package solutions.linked.fuseki.scdb;


import java.util.logging.Logger;

import org.apache.jena.sparql.core.describe.DescribeHandlerRegistry;
import org.apache.jena.sys.JenaSubsystemLifecycle;

public class Lifecycle implements JenaSubsystemLifecycle {
    Logger log = Logger.getLogger(Lifecycle.class.getName());

    @Override
    public void start() {
        log.info("starting SCBD DESCRIBE Handler");
        DescribeHandlerRegistry.get().add(new ScbdDescribeHandlerFactory());
    }

    @Override
    public void stop() {
    }

    @Override
    public int level() {
        return 41;
    }
}