package src.util;

import src.merchants.Merchants;

import static src.util.CommonUtils.*;

public class MerchantUtils {


	public static String printDiscountByMerchant(String[] input) {
    	String merchant=input[2];
    	if(discountRecords.containsKey(merchant)) {
			return Double.toString(discountRecords.get(merchant));
		}
    	else{
    		return ("Invalid merchant");
		}


	}


	public static String processMerchantRequest(String[] input) {
		if(input.length<5){
			return (invalidRequestError);
		}

		String name=input[2];
		String email=input[3];
		if(!validateEmail(email)){
			return invalidEmailError;
		}
		if(merchantRecords.containsKey(name)){
			return ("rejected! (reason: Merchant exists!)");

		}
		float discountRate;
		try {
			discountRate = Float.parseFloat(input[4].split("%")[0]);
			Merchants merchant=Merchants.createMerchant(name,email,discountRate);
			if(merchant!=null) {
				merchantRecords.put(name, merchant);
				return (merchant.getName() + "(" + merchant.getDiscountRate() + "%)");
			}
			//handle negative input
		}catch(NumberFormatException e){
			return invalidValueError;
		}
		return "";
	}

	public static String updateMerchantDiscount(String[] input) {
    	if(input.length<4){
			return (invalidRequestError);
		}
    	String name=input[2];
    	try {
			float discountRate = Float.parseFloat(input[3].split("%")[0]);
			Merchants merchant = merchantRecords.get(name);
			if (merchantRecords.containsKey(name)) {
				merchantRecords.put(name, merchant.updateDiscountRate(discountRate));
				return (name + "(" + discountRate + ")");
			} else {
				return ("Merchant doesn't exist");
			}
		}
    	catch(NumberFormatException e){
    		return invalidValueError;
		}
    	catch (NullPointerException e){
    		return "";
		}
	}
}