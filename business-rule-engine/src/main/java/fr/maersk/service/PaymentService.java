/**
 * 
 */
package fr.maersk.service;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.maersk.exception.RuleNotFoundException;
import fr.maersk.model.OrderData;
import fr.maersk.model.PaymentTransactionRequest;
import fr.maersk.model.PaymentTransactionResponse;
import fr.maersk.model.Response;
import fr.maersk.persistence.entities.PaymentTransaction;
import fr.maersk.persistence.entities.RulesEngine;
import fr.maersk.persistence.repositories.PaymentTransactionRepository;
import fr.maersk.persistence.repositories.RuleEngineRepository;

/**
 * @author supra
 *
 */
public class PaymentService {

	private static final String PHYSICAL_PRODUCT = "physical_Product";
	private static final String BOOK = "book";
	private static final String MEMBERSHIP = "membership";
	private static final String UPGRADE_MEMBERSHIP = "upgrade_embership";
	private static final String VIDEO_LEARNING_TO_SKI = "video_learning_to_ski";
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	PaymentTransactionRepository paymentTransactionRepository;

	@Autowired
	RuleEngineRepository ruleEngineRepository;

	public PaymentTransactionResponse processTrasaction(@Valid PaymentTransactionRequest paymentTransaction) throws RuleNotFoundException {		
		PaymentTransactionResponse transactionResponse = savePaymentTransaction(paymentTransaction);		
		return transactionResponse;
	}

	public PaymentTransactionResponse savePaymentTransaction(@Valid PaymentTransactionRequest paymentTransaction){
		Long transactionId = paymentTransaction.getTransactionId();
		List<OrderData> ordersList = new ArrayList<OrderData>();
		//List<PaymentTransactionResponse> transactionList = new ArrayList<PaymentTransactionResponse>();
		PaymentTransactionResponse paymentTransactionRes = new PaymentTransactionResponse();
		ordersList = paymentTransaction.getOrders();
		ordersList.stream().forEach(order ->{
			if(order.getOrderType().equalsIgnoreCase(PHYSICAL_PRODUCT)) {
				savePhysicalProductTransaction(transactionId,order);
			}
			else if(order.getOrderType().equalsIgnoreCase(BOOK)) {
				savebook_orderTransaction(transactionId,order);
			}
			else if(order.getOrderType().equalsIgnoreCase(MEMBERSHIP)) {
				savemembership_orderTransaction(transactionId,order);
			}
			else if(order.getOrderType().equalsIgnoreCase(UPGRADE_MEMBERSHIP)) {
				saveUpgardeMembershipOrderTransaction(transactionId,order);
			}
			else if(order.getOrderType().equalsIgnoreCase(VIDEO_LEARNING_TO_SKI)) {
				savevideo_orderTransaction(transactionId,order);
			}
			else {
				logger.error("Rules Not defined"+order.getOrderType());
				try {
					throw new RuleNotFoundException(order.getOrderId());
				} catch (RuleNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		paymentTransactionRes.setOrdersStatus("Payment Transaction for All order has been completed");
		paymentTransactionRes.setTransactionId(transactionId);

		return paymentTransactionRes;
	}

	public Response saveUpgardeMembershipOrderTransaction(Long transactionId, @Valid OrderData orderData) {
		RulesEngine rules = getRules(orderData);
		savePaymentTransaction(transactionId, orderData, rules.getId());
		// TODO apply the upgrade.
		// TODO e-mail the owner and inform them of the activation/upgrade.
		logger.info("Payment completed generate a packing slip for shipping. :"+orderData.getOrderId()+""+orderData.getOrderType());
		return new Response("Payment completed for UpgardeMember", true);
	}	

	public Response savePhysicalProductTransaction(Long transactionId, @Valid OrderData orderData) {
		RulesEngine rules = getRules(orderData);
		savePaymentTransaction(transactionId, orderData,rules.getId());
		//TODO generate a packing slip for shipping.
		logger.info("Payment completed :"+orderData.getOrderId()+""+orderData.getOrderType());
		return new Response("Payment completed for Physical Product", true);
	}

	public Response savebook_orderTransaction(Long transactionId, @Valid OrderData orderData) {		
		RulesEngine rules = getRules(orderData);
		savePaymentTransaction(transactionId, orderData, rules.getId());
		// TODO create a duplicate packing slip for the royalty department.
		logger.info("Payment completed :"+orderData.getOrderId()+""+orderData.getOrderType());
		return new Response("Payment completed for book order", true);
	}

	public Response savemembership_orderTransaction(Long transactionId, @Valid OrderData orderData) {	
		RulesEngine rules = getRules(orderData);
		savePaymentTransaction(transactionId, orderData, rules.getId());
		// TODO activate that membership.
		// TODO e-mail the owner and inform them of the activation/upgrade.
		logger.info("Payment completed :"+orderData.getOrderId()+""+orderData.getOrderType());
		return new Response("Payment completed for membership order", true);
	}

	public Response savevideo_orderTransaction(Long transactionId, @Valid OrderData orderData) {
		RulesEngine rules = getRules(orderData);
		savePaymentTransaction(transactionId, orderData,rules.getId());
		// TODO video “Learning to Ski,” add a free “First Aid” video to the packing slip
		logger.info("Payment completed :"+orderData.getOrderId()+""+orderData.getOrderType());
		return new Response("Payment completed for video order", true);
	}

	private void savePaymentTransaction(Long transactionId, OrderData orderData, int ruleId) {
		PaymentTransaction transaction = new PaymentTransaction(new Date(), transactionId, orderData.getAmount(),null, orderData.getOrderId(), orderData.getOrderType(), 1);
		paymentTransactionRepository.save(transaction);
	}

	private RulesEngine getRules(OrderData orderData) {
		RulesEngine rules = ruleEngineRepository.findByOrderType(orderData.getOrderType());
		return rules;
	}

}
