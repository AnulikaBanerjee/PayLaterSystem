package test.utils;

import org.junit.Assert;
import org.junit.Test;
import src.util.CommonUtils;
import src.util.MerchantUtils;
import src.util.UserUtils;

import static org.junit.Assert.*;

public class UserUtilsTest {

    @Test
    public void testProcessUserRequest() throws Exception{
        String result= UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        Assert.assertEquals("user1(500.0)",result);
    }

    @Test
    public void testProcessPayback() throws Exception{
        UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});
        CommonUtils.processTransactionRequest(new String[]{"new","txn","user1","merchant1","300"});
        //Test partial due clearance - Should reduce pending dues in user account
        String result= UserUtils.processPayback(new String[]{"payback","user1","200"});
        Assert.assertEquals("user1(dues: 100.0)",result);
        //Test due clearance of amount greater than pending due - Should reject with message
        result= UserUtils.processPayback(new String[]{"payback","user1","200"});
        Assert.assertEquals("Pending amount is 100.0, but you are attempting to pay more!",result);
        //Test complete due clearance
        result= UserUtils.processPayback(new String[]{"payback","user1","100"});
        Assert.assertEquals("user1(dues: 0.0)",result);
        //Test invalid data type
        result= UserUtils.processPayback(new String[]{"payback","user1","100"});
        Assert.assertEquals("user1(dues: 0.0)",result);

    }

    @Test
    public void printUsersAtCrLimit() {
    }

    @Test
    public void printUserTotalDues() {
    }
}