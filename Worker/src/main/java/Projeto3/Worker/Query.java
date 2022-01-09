package Projeto3.Worker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class Query {



    public static ArrayList runQuery(){

      File owlFile = new File("./ADS.owl");
      ArrayList names = new ArrayList<String>();

      try {
        // Loading an OWL ontology using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology =  ontologyManager.loadOntologyFromOntologyDocument(owlFile);
  
        // Create SQWRL query engine using the SWRLAPI
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
  
        // Create and execute a SQWRL query using the SWRLAPI
        String numberOfObjectives = "3";
        String query = "Algorithm(?alg) ^ "   
          + "minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min,"+numberOfObjectives+")"
          + "maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max,"+numberOfObjectives+")"
          + " -> sqwrl:select(?alg) ^ sqwrl:orderBy(?alg)";  
          SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);
  
        // Process the SQWRL result
        System.out.println("Query: \n" + query + "\n");
        while (result.next()) {
            names.add(result.getNamedIndividual("alg").getShortName());
        }        
      } catch (OWLOntologyCreationException e) {
        System.err.println("Error creating OWL ontology: " + e.getMessage());
        System.exit(-1);
      } catch (SWRLParseException e) {
        System.err.println("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
        System.exit(-1);
      } catch (SQWRLException e) {
        System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
        System.exit(-1);
      } catch (RuntimeException e) {
        System.err.println("Error starting application: " + e.getMessage());
        System.exit(-1);
      }
      return names;
    }
}
