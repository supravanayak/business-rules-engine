package fr.maersk.interview.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.maersk.controller.PaymentController;
import fr.maersk.model.OrderData;
import fr.maersk.model.PaymentTransactionRequest;
import fr.maersk.service.PaymentService;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PaymentService paymentService;

	private final ObjectMapper mapper = new ObjectMapper();	
	
	@Test
	public void processTransactionTest() throws Exception{
		List<OrderData> orders = new ArrayList<OrderData>();
		OrderData order1 = new OrderData(1, "physical Product", 100.00);
		OrderData order2 = new OrderData(2, "book", 200.2);
		OrderData order3 = new OrderData(3, "membership", 300.3);
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		
		PaymentTransactionRequest transaction = new PaymentTransactionRequest(1L, "Client1", orders);
		String jsonRequest = mapper.writeValueAsString(transaction);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/payment/transactions").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn(); }

	/*
	 * mockMvc.perform(MockMvcRequestBuilders.post("/payment/transactions").content(
	 * jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
	 * .andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); }
	 */

}
