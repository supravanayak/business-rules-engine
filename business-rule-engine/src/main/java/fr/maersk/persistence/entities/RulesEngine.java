/**
 * 
 */
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

/**
 * @author supra
 *
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RulesEngine")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RulesEngine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String bussiness_engine_rule;
	private String order_type;

}
