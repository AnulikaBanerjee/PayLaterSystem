package src.util;

import src.merchants.Merchants;
import src.users.Users;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	public static Map<String, Users> userRecords = new HashMap<>();
	public static Map<String, Merchants> merchantRecords = new HashMap<>();
	public static Map<String, Double> discountRecords = new HashMap<>();

	public static final String invalidRequestError= "Invalid request! Try command 'help' for list of valid commands.";
	public static final String invalidValueError= "Invalid value! Change value and try again";
	public static final String invalidEmailError= "Invalid email format! Sample email: abc@asd.tg";
	public static final String userNotFound= "User not found in records!";
	public static final String merchantNotFound= "Merchant not found in records!";

	public static boolean validateEmail(String email) {
		String emailRegex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			return true;
		}
		return false;
	}


	public static String processTransactionRequest(String[] input) {
    	if(input.length<5){
			return (invalidRequestError);
		}
		String userName= input[2];
		String merchantName= input[3];
		double transactionAmount ;

		try{
			transactionAmount= Double.parseDouble(input[4]);
			//check user and merchant are valid
			Users user=userRecords.get(userName);
			if(user==null){
				return  ("User "+userName+" doesn't exist in records");
			}
			Merchants merchant= merchantRecords.get(merchantName);
			if(merchant==null){
				return ("Merchant "+merchantName+" doesn't exist in records");
			}
			//check transaction possible or not
			if(user.getBalance()>=transactionAmount){
				userRecords.put(userName,user.processTransaction(transactionAmount));
				double discount=(transactionAmount*merchantRecords.get(merchantName).getDiscountRate())/100;

				if(discountRecords.get(merchantName)!=null){
					discountRecords.put(merchantName,discountRecords.get(merchantName)+discount);
				}
				else {
					discountRecords.put(merchantName, discount);
				}

				return ("success!");
			}
			else{
				return ("rejected! (reason: credit limit)");
			}
		}catch(NumberFormatException e){
			return invalidValueError;
		}


	}

}