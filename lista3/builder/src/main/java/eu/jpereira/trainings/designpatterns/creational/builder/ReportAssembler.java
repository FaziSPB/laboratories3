/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.model.ReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;

/**
 * Director: drives a ReportBodyBuilder without knowing the concrete format.
 */
public class ReportAssembler {

	private SaleEntry saleEntry;

	public void setSaleEntry(SaleEntry saleEntry) {
		this.saleEntry = saleEntry;
	}

	public Report getReport(ReportBodyBuilder builder) {
		builder.buildCustomer(saleEntry.getCustomer());
		builder.buildItems(saleEntry.getSoldItems());
		Report report = new Report();
		report.setReportBody(builder.getReportBody());
		return report;
	}
}
