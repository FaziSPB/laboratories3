package eu.jpereira.trainings.designpatterns.structural.facade;

import eu.jpereira.trainings.designpatterns.structural.facade.model.Book;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Customer;
import eu.jpereira.trainings.designpatterns.structural.facade.model.DispatchReceipt;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Order;
import eu.jpereira.trainings.designpatterns.structural.facade.service.BookDBService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.CustomerDBService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.CustomerNotificationService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.OrderingService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.WharehouseService;

/**
 * Facade: hides the five services behind a single placeOrder call.
 */
public class DefaultBooktoreFacade implements BookstoreFacade {

	private CustomerDBService customerService;
	private BookDBService bookService;
	private OrderingService orderingService;
	private WharehouseService warehouseService;
	private CustomerNotificationService customerNotificationService;

	@Override
	public void placeOrder(String customerId, String isbn) {
		Customer customer = customerService.findCustomerById(customerId);
		Book book = bookService.findBookByISBN(isbn);
		Order order = orderingService.createOrder(customer, book);
		DispatchReceipt receipt = warehouseService.dispatch(order);
		customerNotificationService.notifyClient(receipt);
	}

	public void setCustomerService(CustomerDBService customerService) {
		this.customerService = customerService;
	}

	public void setBookService(BookDBService bookService) {
		this.bookService = bookService;
	}

	public void setOrderingService(OrderingService orderingService) {
		this.orderingService = orderingService;
	}

	public void setWarehouseService(WharehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public void setCustomerNotificationService(CustomerNotificationService customerNotificationService) {
		this.customerNotificationService = customerNotificationService;
	}
}
