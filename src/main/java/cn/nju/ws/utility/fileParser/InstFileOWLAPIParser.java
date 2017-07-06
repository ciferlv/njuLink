package cn.nju.ws.utility.fileParser;

import cn.nju.ws.unit.instance.Obj;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.stream.Stream;

import static cn.nju.ws.utility.ParamDef.AnonymousIndividual_TYPE_INDEX;
import static cn.nju.ws.utility.ParamDef.URI_TYPE_INDEX;
import static cn.nju.ws.utility.assistanceTool.FileWriter.printToFile;

/**
 * Created by xinzelv on 17-7-6.
 */
public class InstFileOWLAPIParser {

    private static Logger logger = LoggerFactory.getLogger(InstFileOWLAPIParser.class);

    private static OWLAnnotationValueVisitor annotationValueVisitor = new OWLAnnotationValueVisitor() {

        Obj obj = new Obj();

        @Override
        public void visit(IRI iri) {

            String iriString = iri.getIRIString();
            String shortForm = iri.getShortForm();

            obj.setValue(iriString);
            obj.setLocalName(shortForm);
            obj.setType(URI_TYPE_INDEX);
        }

        @Override
        public void visit(OWLAnonymousIndividual individual) {

            String anonyIndividualID = individual.toString();
            obj.setValue(anonyIndividualID);
            obj.setType(AnonymousIndividual_TYPE_INDEX);
        }

        @Override
        public void visit(OWLLiteral node) {

            OWLDatatype dataType = node.getDatatype();
            String literal = node.getLiteral();
            String lang = node.getLang();

            System.out.println(dataType.getIRI());
        }
    };

    private static OWLAxiomVisitor axiomVisitor = new OWLAxiomVisitor() {

        @Override
        public void visit(OWLDeclarationAxiom axiom) {

            logger.info("OWLDeclarationAxiom");
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {

            logger.info("OWLDatatypeDefinitionAxiom");
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {

            OWLAnnotation annotation = axiom.getAnnotation();

            OWLAnnotationSubject subject = axiom.getSubject();
            OWLAnnotationValue value = annotation.getValue();
            OWLAnnotationProperty property = annotation.getProperty();


//            System.out.println(property.getIRI().toString());
            value.accept(annotationValueVisitor);
        }

        @Override
        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

            logger.info("OWLSubAnnotationPropertyOfAxiom");

        }

        @Override
        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {

            logger.info("OWLAnnotationPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {

            logger.info("OWLAnnotationPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {

            logger.info("OWLSubClassOfAxiom");
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

            logger.info("OWLNegativeObjectPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {

            logger.info("OWLAsymmetricObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {

            logger.info("OWLReflexiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {

            logger.info("OWLDisjointClassesAxiom");
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {

            logger.info("OWLDataPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {

            logger.info("OWLObjectPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

            logger.info("OWLEquivalentObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {


            logger.info("OWLNegativeDataPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {

            logger.info("OWLDifferentIndividualsAxiom");
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {

            logger.info("OWLDisjointDataPropertiesAxiom");
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

            logger.info("OWLDisjointObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {


            logger.info("OWLObjectPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {


            logger.info("OWLObjectPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {

            logger.info("OWLFunctionalObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {

            logger.info("OWLSubObjectPropertyOfAxiom");
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {

            logger.info("OWLDisjointUnionAxiom");
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {


            logger.info("OWLSymmetricObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {

            logger.info("OWLDataPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {

            logger.info("OWLFunctionalDataPropertyAxiom");
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {

            logger.info("OWLEquivalentDataPropertiesAxiom");
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {

        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {

        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {

            logger.info("OWLDataPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {

            logger.info("OWLTransitiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

            logger.info("OWLIrreflexiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {

            logger.info("OWLSubDataPropertyOfAxiom");
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

            logger.info("OWLInverseFunctionalObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {

        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {

            logger.info("OWLSubPropertyChainOfAxiom");
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {

            logger.info("OWLInverseObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {

            logger.info("OWLHasKeyAxiom");
        }
    };

    public static void parseInstFileByOWLAPI(String filePath) {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        File file = new File(filePath);

        OWLOntology localAcademic = null;
        try {
            localAcademic = manager.loadOntologyFromOntologyDocument(file);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        Stream<OWLAxiom> axiomStream = localAcademic.axioms();

        axiomStream.forEach(axiom -> axiom.accept(axiomVisitor));

    }

    public static void main(String[] args) {

        String testFilePath = "./example.ttl";
        parseInstFileByOWLAPI(testFilePath);

    }
}
