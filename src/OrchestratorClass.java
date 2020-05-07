package src;

import java.io.*;

import static src.util.MerchantUtils.updateMerchantDiscount;
import static src.util.OrchestratorUtils.*;
import static src.util.UserUtils.processPayback;


public class OrchestratorClass {

    public static void main(String args[]) throws IOException {
        BufferedReader ioReader = new BufferedReader(new InputStreamReader(System.in));
        String[] input;
        BufferedWriter ioWriter= new BufferedWriter(new OutputStreamWriter(System.out));
        OrchestratorClass orchObj= new OrchestratorClass();
        try {
            ioWriter.write("Hi PayLater Team, have a great day and don't forget to sign off. Just type 'logoff'! \n For command help, type 'help' \n ");
            ioWriter.flush();
        }catch (IOException e){
            //writing failed
            System.exit(1);
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
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.exit(1);
            }
        }
    
    }
}