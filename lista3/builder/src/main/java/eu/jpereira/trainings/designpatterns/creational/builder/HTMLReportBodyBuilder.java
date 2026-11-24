package eu.jpereira.trainings.designpatterns.creational.builder;

import java.util.List;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Customer;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SoldItem;

public class HTMLReportBodyBuilder implements ReportBodyBuilder {

	private final HTMLReportBody reportBody = new HTMLReportBody();

	@Override
	public void buildCustomer(Customer customer) {
		reportBody.putContent("<span class=\"customerName\">");
		reportBody.putContent(customer.getName());
		reportBody.putContent("</span><span class=\"customerPhone\">");
		reportBody.putContent(customer.getPhone());
		reportBody.putContent("</span>");
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
		reportBody.putContent("</items>");
	}

	@Override
	public ReportBody getReportBody() {
		return reportBody;
	}
}
