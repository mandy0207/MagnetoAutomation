package com.Jobsity.models.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OrderReturn {

	public String orderID, billingLastName, email, zipCode, errorMsg;

}
