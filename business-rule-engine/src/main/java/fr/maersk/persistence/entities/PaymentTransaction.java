package fr.maersk.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PaymentTransaction")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PaymentTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date date;
	private Long transactionId ;
	private double amountPaid;
	private Long clientId;
	private int orderId;
	private String orderType;
	private int ruleId;
	
	public PaymentTransaction(Date date, Long transactionId, double amountPaid, Long clientId, int orderId,
			String orderType, int ruleId) {
		super();
		this.date = date;
		this.transactionId = transactionId;
		this.amountPaid = amountPaid;
		this.clientId = clientId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.ruleId = ruleId;
	}	

}
