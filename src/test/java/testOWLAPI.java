import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Stream;

import static cn.nju.ws.utility.assistanceTool.FileWriter.printToFile;

/**
 * Created by xinzelv on 17-7-4.
 */
public class testOWLAPI {

    public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException {

        PrintWriter pw = new PrintWriter(new FileOutputStream("./result/TEST.txt"));

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        File file = new File("./testData/example.ttl");

        OWLOntology localAcademic = manager.loadOntologyFromOntologyDocument(file);

        OWLAnnotationValueVisitor annotationValueVisitor = new OWLAnnotationValueVisitor() {

            @Override
            public void visit(IRI iri) {

//                System.out.println("iri: " +iri.getFragment());

            }

            @Override
            public void visit(OWLAnonymousIndividual individual) {

//                System.out.println("individual: "+individual.toString());
            }

            @Override
            public void visit(OWLLiteral node) {
//
//                System.out.println("Datatype: " + node.getDatatype());
//                System.out.println("Literal: " + node.getLiteral());
//                System.out.println("Lang: " + node.getLang());
            }
        };

        OWLAxiomVisitor axiomVisitor = new OWLAxiomVisitor() {

            @Override
            public void visit(OWLDeclarationAxiom axiom) {

                pw.append(axiom.toString() + "\n");
                pw.flush();
            }

            @Override
            public void visit(OWLDatatypeDefinitionAxiom axiom) {

                pw.append(axiom.toString() + "\n");
                pw.flush();
            }

            @Override
            public void visit(SWRLRule swrlRule) {

            }

            @Override
            public void visit(OWLAnnotationAssertionAxiom axiom) {

                OWLAnnotation annotation = axiom.getAnnotation();

                OWLAnnotationSubject subject = axiom.getSubject();
                OWLAnnotationValue value = annotation.getValue();
                OWLAnnotationProperty property = annotation.getProperty();

//                System.out.println("subject: " + subject.toString());
//                System.out.println("property: " + property.toString());
//                System.out.println("value: " + value.toString());

                value.accept(annotationValueVisitor);
                pw.append(axiom.toString() + "\n");
                pw.flush();
            }

            @Override
            public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

                System.out.println("OWLSubAnnotationPropertyOfAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLAnnotationPropertyDomainAxiom axiom) {

                System.out.println("OWLAnnotationPropertyDomainAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLAnnotationPropertyRangeAxiom axiom) {

                System.out.println("OWLAnnotationPropertyRangeAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLSubClassOfAxiom axiom) {

                System.out.println("OWLSubClassOfAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

                System.out.println("OWLNegativeObjectPropertyAssertionAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {

                System.out.println("OWLAsymmetricObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLReflexiveObjectPropertyAxiom axiom) {

                System.out.println("OWLReflexiveObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDisjointClassesAxiom axiom) {

                System.out.println("OWLDisjointClassesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDataPropertyDomainAxiom axiom) {

                System.out.println("OWLDataPropertyDomainAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLObjectPropertyDomainAxiom axiom) {

                System.out.println("OWLObjectPropertyDomainAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

                System.out.println("OWLEquivalentObjectPropertiesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {


                System.out.println("OWLNegativeDataPropertyAssertionAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDifferentIndividualsAxiom axiom) {

                System.out.println("OWLDifferentIndividualsAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDisjointDataPropertiesAxiom axiom) {

                System.out.println("OWLDisjointDataPropertiesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

                System.out.println("OWLDisjointObjectPropertiesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLObjectPropertyRangeAxiom axiom) {


                System.out.println("OWLObjectPropertyRangeAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLObjectPropertyAssertionAxiom axiom) {


                System.out.println("OWLObjectPropertyAssertionAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLFunctionalObjectPropertyAxiom axiom) {

                System.out.println("OWLFunctionalObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLSubObjectPropertyOfAxiom axiom) {

                System.out.println("OWLSubObjectPropertyOfAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDisjointUnionAxiom axiom) {

                System.out.println("OWLDisjointUnionAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLSymmetricObjectPropertyAxiom axiom) {


                System.out.println("OWLSymmetricObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLDataPropertyRangeAxiom axiom) {

                System.out.println("OWLDataPropertyRangeAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLFunctionalDataPropertyAxiom axiom) {

                System.out.println("OWLFunctionalDataPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLEquivalentDataPropertiesAxiom axiom) {

                System.out.println("OWLEquivalentDataPropertiesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLClassAssertionAxiom axiom) {

                pw.append(axiom.toString() + "\n");
                pw.flush();
            }

            @Override
            public void visit(OWLEquivalentClassesAxiom axiom) {

                pw.append(axiom.toString() + "\n");
                pw.flush();
            }

            @Override
            public void visit(OWLDataPropertyAssertionAxiom axiom) {

                System.out.println("OWLDataPropertyAssertionAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void visit(OWLTransitiveObjectPropertyAxiom axiom) {

                System.out.println("OWLTransitiveObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

                System.out.println("OWLIrreflexiveObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLSubDataPropertyOfAxiom axiom) {

                System.out.println("OWLSubDataPropertyOfAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

                System.out.println("OWLInverseFunctionalObjectPropertyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLSameIndividualAxiom axiom) {

//                pw.append(axiom.toString() + "\n");
//                pw.flush();

                System.out.println("****************************************");
                for (OWLIndividual anno : axiom.getIndividuals()) {

                    System.out.println(anno.toStringID());
                }
                System.out.println("****************************************");
            }

            @Override
            public void visit(OWLSubPropertyChainOfAxiom axiom) {

                System.out.println("OWLSubPropertyChainOfAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLInverseObjectPropertiesAxiom axiom) {

                System.out.println("OWLInverseObjectPropertiesAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(OWLHasKeyAxiom axiom) {

                System.out.println("OWLHasKeyAxiom");
                try {
                    printToFile("./result/TEST.txt", axiom.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };


        Stream<OWLAxiom> axiomStream = localAcademic.getAxioms().stream();

        axiomStream.forEach(
                a -> {
                    a.accept(axiomVisitor);
                }
        );
        pw.close();
    }
}
