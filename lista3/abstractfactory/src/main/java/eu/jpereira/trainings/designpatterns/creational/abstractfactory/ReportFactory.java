package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

/**
 * Abstract factory for a family of matching report elements.
 */
public interface ReportFactory {

	ReportHeader createHeader();

	ReportBody createBody();

	ReportFooter createFooter();
}
