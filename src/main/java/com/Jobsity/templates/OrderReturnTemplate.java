package com.Jobsity.templates;



import com.Jobsity.models.Address.OrderReturn;
import com.Jobsity.utils.UniqueGenerator;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class OrderReturnTemplate implements TemplateLoader {

	@Override
	public void load() {

		Fixture.of(OrderReturn.class).addTemplate("emptyOrderID", new Rule() {
			{
				add("orderID", "");
				add("billingLastName", UniqueGenerator.getUniqueString());
				add("email", UniqueGenerator.generateUniqueEmail());
				add("zipCode", Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("errorMsg","This is a required field.");
			}
		});
		
		Fixture.of(OrderReturn.class).addTemplate("emptyBillingLastName", new Rule() {
			{
				add("orderID", Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("billingLastName", "");
				add("email", UniqueGenerator.generateUniqueEmail());
				add("zipCode", Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("errorMsg","This is a required field.");
			}
		});
		
		Fixture.of(OrderReturn.class).addTemplate("emptyEmail", new Rule() {
			{
				add("orderID",Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("billingLastName", UniqueGenerator.getUniqueString());
				add("email", "");
				add("zipCode", Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("errorMsg","This is a required field.");
			}
		});
		
		Fixture.of(OrderReturn.class).addTemplate("invalidEmail", new Rule() {
			{
				add("orderID",Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("billingLastName", UniqueGenerator.getUniqueString());
				add("email", "abc@yopmail.");
				add("zipCode",Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("errorMsg","Please enter a valid email address (Ex: johndoe@domain.com).");
			}
		});
		
		Fixture.of(OrderReturn.class).addTemplate("emptyZipCode", new Rule() {
			{
				add("orderID",Integer.toString(UniqueGenerator.getRandomNumber(1, 100000)));
				add("billingLastName", UniqueGenerator.getUniqueString());
				add("email", "abc@yopmail.com");
				add("zipCode", "");
				add("errorMsg","This is a required field.");
			}
		});
		
		
		
		
		
		
		
		

		
	}	
		
}
