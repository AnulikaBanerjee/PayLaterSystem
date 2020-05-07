package test.utils;

import org.junit.Assert;
import org.junit.Test;
import src.util.MerchantUtils;
import src.util.OrchestratorUtils;
import src.util.UserUtils;

import static org.junit.Assert.*;
import static src.util.CommonUtils.invalidRequestError;

public class OrchestratorUtilsTest {


    @Test
    public void testProcessNewRequests() {

        String[] userInput=new String[]{"new","user","user1","user1@email.com","500"};
        String[] merchantInput=new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"};
        String[] transactionInput=new String[]{"new","txn","user1","merchant1","200"};
        String[] invalidInput=new String[]{"new","txns","user1","merchant1","200"};  //txns instead of txn

        Assert.assertEquals("user1(500.0)", OrchestratorUtils.processNewRequests(userInput));
        Assert.assertEquals("merchant1(5.0%)",OrchestratorUtils.processNewRequests(merchantInput));
        Assert.assertEquals("success!",OrchestratorUtils.processNewRequests(transactionInput));
        Assert.assertEquals(invalidRequestError,OrchestratorUtils.processNewRequests(invalidInput));



    }

    @Test
    public void testProcessReportRequests() {
        //Test report total dues
        String[] result= OrchestratorUtils.processReportRequests(new String[]{"report","total-dues"});
        Assert.assertArrayEquals(new String[]{"total: 0.0"},result);
        //Test report discount
        result= OrchestratorUtils.processReportRequests(new String[]{"report","discount","merchant1"});
        Assert.assertEquals("No records/Invalid merchant",result[0]);

        //Test users at credit limit
        result=OrchestratorUtils.processReportRequests(new String[]{"report","users-at-credit-limit"});
        Assert.assertArrayEquals(new String[]{"None of the users have reached credit limit!"},result);
        result=OrchestratorUtils.processReportRequests(new String[]{"report","user-at-credit-limit"}); //user instead of users
        Assert.assertEquals(invalidRequestError,result[0]);


    }

    @Test
    public void testPrintHelp() {
        String[] helpCommands= new String[]{
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
        Assert.assertArrayEquals(helpCommands,OrchestratorUtils.printHelp());
    }

}