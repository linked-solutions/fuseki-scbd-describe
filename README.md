# fuseki scbd describe

This projects provides a Jena DescribeHandler so that SPARQL DESCRIBE queries are answerded with a Symmetric Concise Bounded Description of the resource.

If the RDF Graph is connected with this handler the graph can be fully explored by DESCRIBE queries, making this handler essential for SPARQL backed Linked Data aplications.

## Compiling

    mvn clean package
    
The resulting jar will be in the `target` folder.

## Usage

Start Fuseki as follows:

On Unix/Linux:

    java -cp "fuseki-server.jar:fuseki-scdb-describe-1.0.0-SNAPSHOT.jar" org.apache.jena.fuseki.cmd.FusekiCmd

On Windows:

    java -cp "fuseki-server.jar;fuseki-scdb-describe-1.0.0-SNAPSHOT.jar" org.apache.jena.fuseki.cmd.FusekiCmd
    
Alternatively you can also use the fuseki Docker image mantained in [this](https://github.com/linked-solutions/jena-docker/tree/master/jena-fuseki) repository.

## Example

This shows an answer retunred by fuseki when using this handler. Note how the resource is recusively expanded in both direction

DESCRIBE <http://ex.org/>
```
<http://ex.org/charlie>
        <http://xmlns.com/foaf/0.1/knows>
                _:b0 .

<http://ex.org/bob>  <http://xmlns.com/foaf/0.1/knows>
                [ <http://xmlns.com/foaf/0.1/knows>
                          <http://ex.org/danny> , <http://ex.org/> ] .

_:b0    <http://xmlns.com/foaf/0.1/name>
                "Alice" .

<http://ex.org/>  <http://xmlns.com/foaf/0.1/knows>
                _:b0 .
```
