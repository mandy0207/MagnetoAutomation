package com.Jobsity.templates;



import com.Jobsity.models.MagnetoUser.MagnetoUser;
import com.Jobsity.utils.UniqueGenerator;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class MagnetoUserTemplate implements TemplateLoader {

	@Override
	public void load() {

		Fixture.of(MagnetoUser.class).addTemplate("emptyFirstName", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", UniqueGenerator.getPassword());
				add("firstName", "");
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","This is a required field.");
			}
		});

		
		
		Fixture.of(MagnetoUser.class).addTemplate("emptyLastName", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", UniqueGenerator.getPassword());
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", "" );
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","This is a required field.");
			}
		});
		
		Fixture.of(MagnetoUser.class).addTemplate("emptyEmail", new Rule() {
			{
				add("email", "");
				add("password", UniqueGenerator.getPassword());
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName",  UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","This is a required field.");

			}
		});
		
		Fixture.of(MagnetoUser.class).addTemplate("invalidEmail", new Rule() {
			{
				add("email", "abc@yopmail.");
				add("password", UniqueGenerator.getPassword());
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName",  UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","Please enter a valid email address (Ex: johndoe@domain.com).");

			}
		});


		Fixture.of(MagnetoUser.class).addTemplate("emptyPassword", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", "");
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","This is a required field.");

			}
		});
		



		Fixture.of(MagnetoUser.class).addTemplate("emptyConfirmPassword", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", UniqueGenerator.getPassword());
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", "");
				add("errorMsg","This is a required field.");


			}
		});

		
		Fixture.of(MagnetoUser.class).addTemplate("minimumLengthPassword", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", "abc");
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");


			}
		});
		
		Fixture.of(MagnetoUser.class).addTemplate("minimumLengthPassword", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", "abc");
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");


			}
		});
		
		
		Fixture.of(MagnetoUser.class).addTemplate("alphaNumericPassword", new Rule() {
			{
				add("email", UniqueGenerator.generateUniqueEmail());
				add("password", "abcyy123");
				add("firstName", UniqueGenerator.getUniqueString());
				add("lastName", UniqueGenerator.getUniqueString());
				add("confirmPassword", UniqueGenerator.getPassword());
				add("errorMsg","Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.");


			}
		});
		


	}

}
