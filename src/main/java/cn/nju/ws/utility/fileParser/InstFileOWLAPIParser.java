package cn.nju.ws.utility.fileParser;

import cn.nju.ws.unit.instance.Doc;
import cn.nju.ws.unit.instance.Value;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.stream.Stream;

import static cn.nju.ws.utility.ParamDef.recordDataType;
import static cn.nju.ws.utility.ParamDef.recordAxiomType;
import static cn.nju.ws.utility.ParamDef.souDoc;

/**
 * Created by xinzelv on 17-7-6.
 */
public class InstFileOWLAPIParser {

    private static Logger logger = LoggerFactory.getLogger(InstFileOWLAPIParser.class);

    private static String subString, propString;

    private static Doc currentDoc;

    private static OWLAnnotationValueVisitor annotationValueVisitor = new OWLAnnotationValueVisitor() {

        Value value;

        @Override
        public void visit(IRI iri) {

            value = new Value();
            value.setIsURI(true);
            value.setIsAnonID(false);

            String iriString = iri.toString();
//            String localName = iri.getFragment();
//
//            if (localName == null) {
//
//                localName = iriString.split("/")[iriString.split("/").length - 1];
//            }

            String localName = iriString.split("/")[iriString.split("/").length - 1];
            value.setLiteral(iriString);
            value.setLocalName(localName);

            currentDoc.addSubPropValToGraph(subString, propString, value);
        }

        @Override
        public void visit(OWLAnonymousIndividual individual) {

            value = new Value();
            value.setIsURI(false);
            value.setIsAnonID(true);

            String anonyIndividualID = individual.toString();
            value.setLiteral(anonyIndividualID);

            currentDoc.addSubPropValToGraph(subString, propString, value);
        }

        @Override
        public void visit(OWLLiteral node) {

            value = new Value();
            value.setIsURI(false);
            value.setIsAnonID(false);

            OWLDatatype dataType = node.getDatatype();
            String literal = node.getLiteral();
            String lang = node.getLang();

            if (dataType == null) logger.info("There is value without DataType!!!");

            value.setDataType(dataType);
            value.setLiteral(literal);
            if (lang != null && !lang.equals("")) value.setLang(lang);

            if (!recordDataType.contains(dataType)) recordDataType.add(dataType);

            currentDoc.addSubPropValToGraph(subString, propString, value);
        }
    };

    private static OWLAxiomVisitor axiomVisitor = new OWLAxiomVisitor() {

        @Override
        public void visit(OWLDeclarationAxiom axiom) {

            if (!recordAxiomType.contains("OWLDeclarationAxiom")) recordAxiomType.add("OWLDeclarationAxiom");
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {

            if (!recordAxiomType.contains("OWLDatatypeDefinitionAxiom"))
                recordAxiomType.add("OWLDatatypeDefinitionAxiom");
        }

        @Override
        public void visit(SWRLRule swrlRule) {

            if (!recordAxiomType.contains("SWRLRule")) recordAxiomType.add("SWRLRule");
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLAnnotationAssertionAxiom"))
                recordAxiomType.add("OWLAnnotationAssertionAxiom");

            OWLAnnotation annotation = axiom.getAnnotation();

            OWLAnnotationSubject subject = axiom.getSubject();
            OWLAnnotationValue value = annotation.getValue();
            OWLAnnotationProperty property = annotation.getProperty();

            subString = subject.toString();
            propString = property.getIRI().toString();

            value.accept(annotationValueVisitor);

        }

        @Override
        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

            if (!recordAxiomType.contains("OWLSubAnnotationPropertyOfAxiom"))
                recordAxiomType.add("OWLSubAnnotationPropertyOfAxiom");

        }

        @Override
        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {

            if (!recordAxiomType.contains("OWLAnnotationPropertyDomainAxiom"))
                recordAxiomType.add("OWLAnnotationPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {

            if (!recordAxiomType.contains("OWLAnnotationPropertyRangeAxiom"))
                recordAxiomType.add("OWLAnnotationPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {

            if (!recordAxiomType.contains("OWLSubClassOfAxiom")) recordAxiomType.add("OWLSubClassOfAxiom");
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLNegativeObjectPropertyAssertionAxiom"))
                recordAxiomType.add("OWLNegativeObjectPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLAsymmetricObjectPropertyAxiom"))
                recordAxiomType.add("OWLAsymmetricObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLReflexiveObjectPropertyAxiom"))
                recordAxiomType.add("OWLReflexiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {

            if (!recordAxiomType.contains("OWLDisjointClassesAxiom")) recordAxiomType.add("OWLDisjointClassesAxiom");
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {

            if (!recordAxiomType.contains("OWLDataPropertyDomainAxiom"))
                recordAxiomType.add("OWLDataPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {

            if (!recordAxiomType.contains("OWLObjectPropertyDomainAxiom"))
                recordAxiomType.add("OWLObjectPropertyDomainAxiom");
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

            if (!recordAxiomType.contains("OWLEquivalentObjectPropertiesAxiom"))
                recordAxiomType.add("OWLEquivalentObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLNegativeDataPropertyAssertionAxiom"))
                recordAxiomType.add("OWLNegativeDataPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {

            if (!recordAxiomType.contains("OWLDifferentIndividualsAxiom"))
                recordAxiomType.add("OWLDifferentIndividualsAxiom");
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {

            if (!recordAxiomType.contains("OWLDisjointDataPropertiesAxiom"))
                recordAxiomType.add("OWLDisjointDataPropertiesAxiom");
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

            if (!recordAxiomType.contains("OWLDisjointObjectPropertiesAxiom"))
                recordAxiomType.add("OWLDisjointObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {

            if (!recordAxiomType.contains("OWLObjectPropertyRangeAxiom"))
                recordAxiomType.add("OWLObjectPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLObjectPropertyAssertionAxiom"))
                recordAxiomType.add("OWLObjectPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLFunctionalObjectPropertyAxiom"))
                recordAxiomType.add("OWLFunctionalObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {

            if (!recordAxiomType.contains("OWLSubObjectPropertyOfAxiom"))
                recordAxiomType.add("OWLSubObjectPropertyOfAxiom");
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {

            if (!recordAxiomType.contains("OWLDisjointUnionAxiom")) recordAxiomType.add("OWLDisjointUnionAxiom");
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLSymmetricObjectPropertyAxiom"))
                recordAxiomType.add("OWLSymmetricObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {

            if (!recordAxiomType.contains("OWLDataPropertyRangeAxiom"))
                recordAxiomType.add("OWLDataPropertyRangeAxiom");
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLFunctionalDataPropertyAxiom"))
                recordAxiomType.add("OWLFunctionalDataPropertyAxiom");
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {

            if (!recordAxiomType.contains("OWLEquivalentDataPropertiesAxiom"))
                recordAxiomType.add("OWLEquivalentDataPropertiesAxiom");
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLClassAssertionAxiom")) recordAxiomType.add("OWLClassAssertionAxiom");

            String myClass = axiom.getClassExpression().toString();

            if (myClass.startsWith("<")) myClass = myClass.substring(1, myClass.length() - 1);

            String subject = axiom.getIndividual().toStringID();

            currentDoc.addTypeToInst(subject, myClass);
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {

            if (!recordAxiomType.contains("OWLEquivalentClassesAxiom"))
                recordAxiomType.add("OWLEquivalentClassesAxiom");
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {

            if (!recordAxiomType.contains("OWLDataPropertyAssertionAxiom"))
                recordAxiomType.add("OWLDataPropertyAssertionAxiom");
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLTransitiveObjectPropertyAxiom"))
                recordAxiomType.add("OWLTransitiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLIrreflexiveObjectPropertyAxiom"))
                recordAxiomType.add("OWLIrreflexiveObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {

            if (!recordAxiomType.contains("OWLSubDataPropertyOfAxiom"))
                recordAxiomType.add("OWLSubDataPropertyOfAxiom");
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

            if (!recordAxiomType.contains("OWLInverseFunctionalObjectPropertyAxiom"))
                recordAxiomType.add("OWLInverseFunctionalObjectPropertyAxiom");
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {

            if (!recordAxiomType.contains("OWLSameIndividualAxiom")) recordAxiomType.add("OWLSameIndividualAxiom");

            String individual1 = null, individual2 = null;
            int cnt = 1;
            for (OWLIndividual anno : axiom.getIndividuals()) {

                if (cnt == 1) {

                    individual1 = anno.toStringID();
                    cnt++;
                } else {

                    individual2 = anno.toStringID();
                }
            }

            if (individual1 != null && individual1 != "" && individual2 != null && individual2 != "") {

                currentDoc.addSameAsToInst(individual1, individual2);
                currentDoc.addSameAsToInst(individual2, individual1);
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {

            if (!recordAxiomType.contains("OWLSubPropertyChainOfAxiom"))
                recordAxiomType.add("OWLSubPropertyChainOfAxiom");
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {

            if (!recordAxiomType.contains("OWLInverseObjectPropertiesAxiom"))
                recordAxiomType.add("OWLInverseObjectPropertiesAxiom");
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {

            if (!recordAxiomType.contains("OWLHasKeyAxiom")) recordAxiomType.add("OWLHasKeyAxiom");
        }
    };

    public static void parseInstFileByOWLAPI(String filePath, Doc doc) {

        currentDoc = doc;
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        File file = new File(filePath);

        OWLOntology localAcademic = null;
        try {
            localAcademic = manager.loadOntologyFromOntologyDocument(file);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        Stream<OWLAxiom> axiomStream = localAcademic.getAxioms().stream();

        axiomStream.forEach(axiom -> axiom.accept(axiomVisitor));

    }

    public static void main(String[] args) {

        String testFilePath = "./example.ttl";
        parseInstFileByOWLAPI(testFilePath, souDoc);

        System.out.println(souDoc.graphToString());
    }
}
