package eu.jpereira.trainings.designpatterns.creational.builder.model;

import java.util.List;

/**
 * Builds a ReportBody step by step; one implementation per report format.
 */
public interface ReportBodyBuilder {

	void buildCustomer(Customer customer);

	void buildItems(List<SoldItem> items);

	ReportBody getReportBody();
}
