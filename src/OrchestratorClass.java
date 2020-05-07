package src;

import java.io.*;

import static src.util.CommonUtils.*;
import static src.util.UserUtils.*;
import static src.util.MerchantUtils.*;


public class OrchestratorClass {

    public static final String invalidRequestError= "Invalid request! Try command 'help' for list of valid commands.";

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
            response= printUserTotalDues(input);
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


    public static void main(String args[]) throws IOException {
        BufferedReader ioReader = new BufferedReader(new InputStreamReader(System.in));
        String[] input;
        BufferedWriter ioWriter= new BufferedWriter(new OutputStreamWriter(System.out));
        try {
            ioWriter.write("Hi PayLater Team, have a great day and don't forget to sign off. Just type 'logoff'! \n For command help, type 'help' \n ");
            ioWriter.flush();
        }catch (IOException e){
            //writing failed
        }finally {
           // ioWriter.close();
        }

        String inputLine;
        String response;
        String[] responseArr;
        while (true) {

           ioWriter.write(">> ");
           ioWriter.flush();

            try {
                inputLine=ioReader.readLine();
                if (inputLine.equalsIgnoreCase("logoff")) {
                    System.exit(0);
                }else{

                    input=inputLine.trim().split(" ");

                    if(input.length==0)
                        continue;
                    switch(input[0]){
                        case "new":
                            response=processNewRequests(input);
                            ioWriter.write(response+"\n");
                            break;
                        case "report":
                            responseArr=processReportRequests(input);
                            for(int i=0;i<responseArr.length;i++){
                                ioWriter.write(responseArr[i]+"\n");
                            }
                            break;
                        case "update":
                            response=updateMerchantDiscount(input);
                            ioWriter.write(response+"\n");
                            break;
                        case "payback":
                            response=processPayback(input);
                            ioWriter.write(response+"\n");
                            break;
                        default:
                            responseArr=printHelp();
                            for(int i=0;i<responseArr.length;i++){
                                ioWriter.write(responseArr[i]+"\n");
                            }
                            break;
                    }
                    ioWriter.flush();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    
    }
}