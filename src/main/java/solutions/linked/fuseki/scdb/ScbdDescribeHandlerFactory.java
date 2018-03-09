package solutions.linked.fuseki.scdb;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.core.describe.DescribeHandler;
import org.apache.jena.sparql.core.describe.DescribeHandlerFactory;
import org.apache.jena.sparql.util.Context;

public class ScbdDescribeHandlerFactory implements DescribeHandlerFactory {

    Logger log = Logger.getLogger("DESCRIBE");
	@Override
	public DescribeHandler create() {
		return new DescribeHandler(){
        
            private Set<RDFNode> expanded = new HashSet<>();
			private Model accumulateResultModel;

            @Override
            public void start(Model accumulateResultModel, Context qContext) {
                this.accumulateResultModel = accumulateResultModel;
            }
        
            @Override
            public void finish() {
                this.accumulateResultModel = null;
            }
        
            private synchronized void expand(RDFNode rdfNode) {
                if (expanded.contains(rdfNode)) {
                    return;
                } else {
                    expanded.add(rdfNode);
                }
                Model model = rdfNode.getModel();
                if (rdfNode.isResource()) { 
                    StmtIterator iterator = model.listStatements(rdfNode.asResource(), null, (RDFNode) null);
                    while (iterator.hasNext()) {
                        //resurse on bnode objects
                        Statement stmt = iterator.next();
                        accumulateResultModel.add(stmt);
                        RDFNode obj = stmt.getObject();
                        if (obj.isAnon()) {
                            expand(obj);
                        }

                    }
                }
                {
                    StmtIterator iterator = model.listStatements(null, null, rdfNode);
                    while (iterator.hasNext()) {                    
                        Statement stmt = iterator.next();
                        accumulateResultModel.add(stmt);
                        RDFNode subj = stmt.getSubject();
                        if (subj.isAnon()) {
                            expand(subj);
                        }
                    }

                }
                
            }

            @Override
            public void describe(Resource resource) {
                expand(resource);
            }
        };
	}

}