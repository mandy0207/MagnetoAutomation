package com.Jobsity.models.MagnetoUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MagnetoUser {

	public String email, password, firstName,lastName,confirmPassword, errorMsg;

}
