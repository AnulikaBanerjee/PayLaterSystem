package src.util;

import src.users.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static src.util.CommonUtils.*;

public class UserUtils {


    public static String processUserRequest(String[] input){

		if(input.length<5){
			return (invalidRequestError);
		}
		String name=input[2];
		String email=input[3];
		if(!validateEmail(email)){
			return invalidEmailError;
		}
		if(userRecords.containsKey(name)){
			return ("rejected! (reason: User exists!)");
		}
		double creditLimit;
		try {
			creditLimit = Double.parseDouble(input[4]);
			Users user=Users.createUser(name,email,creditLimit);
			if(user!=null) {
				userRecords.put(name, user);
				return (user.getName() + "(" + user.getCreditLimit() + ")");
			}
			//handle negative input
		}catch(NumberFormatException e){
			// invalid amount value
			return invalidValueError;
		}
		return "";
    }

	public static String processPayback(String[] input) {

    	if(input.length<3)
    		return invalidRequestError;
    	String name=input[1];
    	try {
			double payback = Double.parseDouble(input[2]);

			if(payback>userRecords.get(name).getDues()){
				return ("Pending amount is "+userRecords.get(name).getDues()+", but you are attempting to pay more!");
			}
			userRecords.put(name, userRecords.get(name).clearDues(payback));
			return (name + "(dues: " + userRecords.get(name).getDues() + ")");
		}
    	catch (NumberFormatException e){
			return invalidValueError;
		}
    	catch (NullPointerException e){
    		return userNotFound;
		}

	}

	public static String[] printUsersAtCrLimit() {
    	boolean crLimitUserExists=false;
    	List<String> usersAtCrLimit= new ArrayList<>();

		for(Map.Entry<String,Users> entry:userRecords.entrySet()){
			if(entry.getValue().getDues()==entry.getValue().getCreditLimit()) {
				usersAtCrLimit.add(entry.getKey());
				crLimitUserExists = true;
			}
		}
		if(!crLimitUserExists)
			return new String[]{"None of the users have reached credit limit!"};
		else
			return usersAtCrLimit.toArray(new String[usersAtCrLimit.size()]);
	}

	public static String[] printUserTotalDues() {
//    	if(userRecords.size()==0){
//    		return new String[]{"No user records available"};
//    	}
    	String[] dueList=new String[userRecords.size()+1];
    	int index=0;
    	double dues=0;
    	double totalDues=0;
		for(Map.Entry<String,Users> entry:userRecords.entrySet()){
			dues=entry.getValue().getDues();
			dueList[index++]=(entry.getKey()+": "+dues);
			totalDues+=dues;
		}
		dueList[index++]=("total: "+totalDues);
		return dueList;
	}

}