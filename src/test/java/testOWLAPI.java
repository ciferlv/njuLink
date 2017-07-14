import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by xinzelv on 17-7-4.
 */
public class testOWLAPI {

    public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException {

        PrintWriter pw = new PrintWriter(new FileOutputStream("./result/TEST.txt"));

        Set<String> axiomTypeSet = new HashSet<>();
        Set<String> annotationTypeSet = new HashSet<>();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        File file = new File("./FORTH/Spatial/WITHIN/Abox1.nt");

        OWLOntology localAcademic = manager.loadOntologyFromOntologyDocument(file);

        OWLAnnotationValueVisitor annotationValueVisitor = new OWLAnnotationValueVisitor() {

            @Override
            public void visit(IRI iri) {

                annotationTypeSet.add("IRI");
            }

            @Override
            public void visit(OWLAnonymousIndividual individual) {

                annotationTypeSet.add("OWLAnonymousIndividual");
            }

            @Override
            public void visit(OWLLiteral node) {

                annotationTypeSet.add("OWLLiteral");
                System.out.println("DataType: " + node.getDatatype());
                System.out.println("Literal: " + node.getLiteral());

            }
        };

        OWLAxiomVisitor axiomVisitor = new OWLAxiomVisitor() {

            @Override
            public void visit(OWLDeclarationAxiom axiom) {

                axiomTypeSet.add("OWLDeclarationAxiom");
            }

            @Override
            public void visit(OWLDatatypeDefinitionAxiom axiom) {

                axiomTypeSet.add("OWLDatatypeDefinitionAxiom");
            }

            @Override
            public void visit(SWRLRule swrlRule) {

                axiomTypeSet.add("SWRLRule");
            }

            @Override
            public void visit(OWLAnnotationAssertionAxiom axiom) {

                axiomTypeSet.add("OWLAnnotationAssertionAxiom");

                OWLAnnotation annotation = axiom.getAnnotation();

                OWLAnnotationSubject subject = axiom.getSubject();
                OWLAnnotationValue value = annotation.getValue();
                OWLAnnotationProperty property = annotation.getProperty();

                System.out.println("subject: " + subject.toString());
                System.out.println("property: " + property.toString());
//                System.out.println("value: " + value.toString());

                value.accept(annotationValueVisitor);
            }

            @Override
            public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

                axiomTypeSet.add("OWLSubAnnotationPropertyOfAxiom");
            }

            @Override
            public void visit(OWLAnnotationPropertyDomainAxiom axiom) {

                axiomTypeSet.add("OWLAnnotationPropertyDomainAxiom");
            }

            @Override
            public void visit(OWLAnnotationPropertyRangeAxiom axiom) {

                axiomTypeSet.add("OWLAnnotationPropertyRangeAxiom");
            }

            @Override
            public void visit(OWLSubClassOfAxiom axiom) {

                axiomTypeSet.add("OWLSubClassOfAxiom");
            }

            @Override
            public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {

                axiomTypeSet.add("OWLNegativeObjectPropertyAssertionAxiom");
            }

            @Override
            public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLAsymmetricObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLReflexiveObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLReflexiveObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLDisjointClassesAxiom axiom) {

                axiomTypeSet.add("OWLDisjointClassesAxiom");
            }

            @Override
            public void visit(OWLDataPropertyDomainAxiom axiom) {

                axiomTypeSet.add("OWLDataPropertyDomainAxiom");
            }

            @Override
            public void visit(OWLObjectPropertyDomainAxiom axiom) {

                axiomTypeSet.add("OWLObjectPropertyDomainAxiom");
            }

            @Override
            public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

                axiomTypeSet.add("OWLEquivalentObjectPropertiesAxiom");
            }

            @Override
            public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {

                axiomTypeSet.add("OWLNegativeDataPropertyAssertionAxiom");
            }

            @Override
            public void visit(OWLDifferentIndividualsAxiom axiom) {

                axiomTypeSet.add("OWLDifferentIndividualsAxiom");
            }

            @Override
            public void visit(OWLDisjointDataPropertiesAxiom axiom) {

                axiomTypeSet.add("OWLDisjointDataPropertiesAxiom");
            }

            @Override
            public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

                axiomTypeSet.add("OWLDisjointObjectPropertiesAxiom");
            }

            @Override
            public void visit(OWLObjectPropertyRangeAxiom axiom) {

                axiomTypeSet.add("OWLObjectPropertyRangeAxiom");
            }

            @Override
            public void visit(OWLObjectPropertyAssertionAxiom axiom) {

                axiomTypeSet.add("OWLObjectPropertyAssertionAxiom");
            }

            @Override
            public void visit(OWLFunctionalObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLFunctionalObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLSubObjectPropertyOfAxiom axiom) {

                axiomTypeSet.add("OWLSubObjectPropertyOfAxiom");
            }

            @Override
            public void visit(OWLDisjointUnionAxiom axiom) {

                axiomTypeSet.add("OWLDisjointUnionAxiom");
            }

            @Override
            public void visit(OWLSymmetricObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLSymmetricObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLDataPropertyRangeAxiom axiom) {

                axiomTypeSet.add("OWLDataPropertyRangeAxiom");
            }

            @Override
            public void visit(OWLFunctionalDataPropertyAxiom axiom) {

                axiomTypeSet.add("OWLFunctionalDataPropertyAxiom");
            }

            @Override
            public void visit(OWLEquivalentDataPropertiesAxiom axiom) {

                axiomTypeSet.add("OWLEquivalentDataPropertiesAxiom");
            }

            @Override
            public void visit(OWLClassAssertionAxiom axiom) {

                axiomTypeSet.add("OWLClassAssertionAxiom");
            }

            @Override
            public void visit(OWLEquivalentClassesAxiom axiom) {

                axiomTypeSet.add("OWLEquivalentClassesAxiom");
            }

            @Override
            public void visit(OWLDataPropertyAssertionAxiom axiom) {

                axiomTypeSet.add("OWLDataPropertyAssertionAxiom");
            }

            @Override
            public void visit(OWLTransitiveObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLTransitiveObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLIrreflexiveObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLSubDataPropertyOfAxiom axiom) {

                axiomTypeSet.add("OWLSubDataPropertyOfAxiom");
            }

            @Override
            public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

                axiomTypeSet.add("OWLInverseFunctionalObjectPropertyAxiom");
            }

            @Override
            public void visit(OWLSameIndividualAxiom axiom) {

                axiomTypeSet.add("OWLSameIndividualAxiom");
            }

            @Override
            public void visit(OWLSubPropertyChainOfAxiom axiom) {

                axiomTypeSet.add("OWLSubPropertyChainOfAxiom");
            }

            @Override
            public void visit(OWLInverseObjectPropertiesAxiom axiom) {

                axiomTypeSet.add("OWLInverseObjectPropertiesAxiom");
            }

            @Override
            public void visit(OWLHasKeyAxiom axiom) {

                axiomTypeSet.add("OWLHasKeyAxiom");
            }
        };


        Stream<OWLAxiom> axiomStream = localAcademic.getAxioms().stream();

        axiomStream.forEach(
                a -> {
                    a.accept(axiomVisitor);
                }
        );


        for (String axiomType : axiomTypeSet) {

            System.out.println(axiomType);
        }

        for (String annotationType : annotationTypeSet) {

            System.out.println(annotationType);
        }

        pw.close();
    }
}
