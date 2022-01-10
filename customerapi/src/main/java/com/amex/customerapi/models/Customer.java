package com.amex.customerapi.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Amex_Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Customer_Id")
    @ApiModelProperty(position = 1, required = true, hidden=true, notes = "Auto generated column")
	private long customerId;
    @Column(name="First_Name",nullable = false,length = 45)
    @ApiModelProperty(example = "Param")
	private String firstName;
    
    @Column(name="Last_Name",nullable = false,length = 45)
    @ApiModelProperty(example = "Bala")
	private String lastName;
    
    @DateTimeFormat(iso = ISO.DATE)
    @Column(name="DOB")
    @ApiModelProperty(example = "1970-12-02")
	private LocalDate dob;
    @Column(name="Email",nullable = false)
    @ApiModelProperty(example = "param@gmail.com")
	private String email;
	
}
