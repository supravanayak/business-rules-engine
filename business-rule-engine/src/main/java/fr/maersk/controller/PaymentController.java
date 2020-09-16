package fr.maersk.controller;

import javax.validation.Valid;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.maersk.exception.RuleNotFoundException;
import fr.maersk.model.OrderData;
import fr.maersk.model.PaymentTransactionRequest;
import fr.maersk.model.PaymentTransactionResponse;
import fr.maersk.model.Response;
import fr.maersk.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;	

	@PostMapping("/transactions")
	public ResponseEntity<PaymentTransactionResponse> processTransaction(@Valid @RequestBody PaymentTransactionRequest paymentTransaction) throws RuleNotFoundException{
		PaymentTransactionResponse paymentTransactionResponse = paymentService.processTrasaction(paymentTransaction);
		return ResponseEntity.ok().body(paymentTransactionResponse);
	}
	
	// For Individual Transaction order 
	@PostMapping("/physical_product_order/{transactionId}")
	public ResponseEntity<Response> physicalProductTransaction(@PathVariable(value = "transactionId") Long transactionId,@Valid @RequestBody OrderData orderData){
		Response res =paymentService.savePhysicalProductTransaction(transactionId,orderData);
		logger.info("physical Product TransactionCreated Sucessfully", res);
		return ResponseEntity.ok().body(res);
	}

	@PostMapping("/book_order/{transactionId}")
	public ResponseEntity<Response> bookOrderTransaction(@PathVariable(value = "transactionId") Long transactionId,@Valid @RequestBody OrderData orderData){
		Response res =paymentService.savebook_orderTransaction(transactionId,orderData);
		logger.info("book order payment Transaction completed Sucessfully", res);
		return ResponseEntity.ok().body(res);
	}
	
	@PostMapping("/membership_order/{transactionId}")
	public ResponseEntity<Response> membershipOrderTransaction(@PathVariable(value = "transactionId") Long transactionId,@Valid @RequestBody OrderData orderData){
		Response res =paymentService.savemembership_orderTransaction(transactionId,orderData);
		logger.info("membership order payment Transaction completed Sucessfully", res);
		return ResponseEntity.ok().body(res);
	}
	
	@PostMapping("/upgarde_membership_order/{transactionId}")
	public ResponseEntity<Response> upgardeMembershipOrderTransaction(@PathVariable(value = "transactionId") Long transactionId,@Valid @RequestBody OrderData orderData){
		Response res =paymentService.saveUpgardeMembershipOrderTransaction(transactionId,orderData);
		logger.info("membership order payment Transaction completed Sucessfully", res);
		return ResponseEntity.ok().body(res);
	}
	
	@PostMapping("/video_order")
	public ResponseEntity<Response> videoOrderTransaction(@PathVariable(value = "transactionId") Long transactionId,@Valid @RequestBody OrderData orderData){
		Response res =paymentService.savevideo_orderTransaction(transactionId,orderData);
		logger.info("video Order payment Transaction completed Sucessfully", res);
		return ResponseEntity.ok().body(res);
	}
		

}
