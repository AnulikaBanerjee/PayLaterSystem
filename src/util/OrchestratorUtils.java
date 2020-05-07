package src.util;

import static src.util.CommonUtils.invalidRequestError;
import static src.util.CommonUtils.processTransactionRequest;
import static src.util.MerchantUtils.printDiscountByMerchant;
import static src.util.MerchantUtils.processMerchantRequest;
import static src.util.UserUtils.*;

public class OrchestratorUtils {
    public static String processNewRequests(String[] input){
        String response="";
        if("user".equalsIgnoreCase(input[1])){
            response= processUserRequest(input);}
        else if("merchant".equalsIgnoreCase(input[1]))
            response= processMerchantRequest(input);
        else if("txn".equalsIgnoreCase(input[1]))
            response= processTransactionRequest(input);
        else{
            return invalidRequestError;
        }
        return response;
    }

    public static String[] processReportRequests(String[] input){
        String[] response;
        if("total-dues".equalsIgnoreCase(input[1]))
            response= printUserTotalDues();
        else if("discount".equalsIgnoreCase(input[1]))
            response=new String[]{printDiscountByMerchant(input)};
        else if("users-at-credit-limit".equalsIgnoreCase(input[1]))
            response= printUsersAtCrLimit();
        else{
            response= new String[]{invalidRequestError};
        }
        return response;
    }

    public static String[] printHelp(){
        final String[] helpCommands= new String[]{
                "List of valid commands:",
                "new user [userName] [email] [creditLimit]",
                "new merchant [merchantName] [email] [discount%]",
                "new txn [userName] [merchantName] [transactionAmount]",
                "update merchant [merchantName] [discount%]",
                "payback [userName] [amount]",
                "report discount [merchantName]",
                "report dues [userName]",
                "report users-at-credit-limit",
                "report total-dues"

        };
        return helpCommands;
    }


}
