package eu.jpereira.trainings.designpatterns.creational.builder.xml;

import java.util.List;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Customer;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SoldItem;

public class XMLReportBodyBuilder implements ReportBodyBuilder {

	private final XMLReportBody reportBody = new XMLReportBody();

	@Override
	public void buildCustomer(Customer customer) {
		reportBody.putContent("<sale><customer><name>");
		reportBody.putContent(customer.getName());
		reportBody.putContent("</name><phone>");
		reportBody.putContent(customer.getPhone());
		reportBody.putContent("</phone></customer>");
	}

	@Override
	public void buildItems(List<SoldItem> items) {
		reportBody.putContent("<items>");
		for (SoldItem soldEntry : items) {
			reportBody.putContent("<item><name>");
			reportBody.putContent(soldEntry.getName());
			reportBody.putContent("</name><quantity>");
			reportBody.putContent(soldEntry.getQuantity());
			reportBody.putContent("</quantity><price>");
			reportBody.putContent(soldEntry.getUnitPrice());
			reportBody.putContent("</price></item>");
		}
		reportBody.putContent("</items></sale>");
	}

	@Override
	public ReportBody getReportBody() {
		return reportBody;
	}
}
