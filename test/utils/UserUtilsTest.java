package test.utils;

import org.junit.Assert;
import org.junit.Test;
import src.util.CommonUtils;
import src.util.MerchantUtils;
import src.util.UserUtils;

import static src.util.CommonUtils.*;

public class UserUtilsTest {

    @Test
    public void testProcessUserRequest() throws Exception{
        //Test success condition
        String result= UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        Assert.assertEquals("user1(500.0)",result);
        //Test invalid request format
        result= UserUtils.processUserRequest(new String[]{"new","user","user1@email.com","500"});
        Assert.assertEquals(invalidRequestError,result);
        //Test invalid email format
        result= UserUtils.processUserRequest(new String[]{"new","user","user2","user1ema.com","500"});
        Assert.assertEquals(invalidEmailError,result);
        //Test recreation of existing user - should be rejected
        result= UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        Assert.assertEquals("rejected! (reason: User exists!)",result);
        //Test invalid value
        result= UserUtils.processUserRequest(new String[]{"new","user","user2","user1@email.com","Rs. 500"});
        Assert.assertEquals(invalidValueError,result);

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
        result= UserUtils.processPayback(new String[]{"payback","user7","10h0"});
        Assert.assertEquals(invalidValueError,result);
        //Test invalid user
        result= UserUtils.processPayback(new String[]{"payback","user7","100"});
        Assert.assertEquals(userNotFound,result);

    }

    @Test
    public void testPrintUsersAtCrLimit() {
        UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        UserUtils.processUserRequest(new String[]{"new","user","user2","user2@email.com","200"});
        UserUtils.processUserRequest(new String[]{"new","user","user3","user3@email.com","300"});
        UserUtils.processUserRequest(new String[]{"new","user","user4","user4@email.com","700"});
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});

        //Test to print users who have reached their credit limit - No such case currently
        String[] result=UserUtils.printUsersAtCrLimit();
        Assert.assertArrayEquals(new String[]{"None of the users have reached credit limit!"},result);
        //Processing some transactions
        CommonUtils.processTransactionRequest(new String[]{"new","txn","user2","merchant1","200"});
        CommonUtils.processTransactionRequest(new String[]{"new","txn","user4","merchant1","700"});
        //Test to print users who have reached their credit limit
        result=UserUtils.printUsersAtCrLimit();
        Assert.assertArrayEquals(new String[]{"user2","user4"},result);
    }

    @Test
    public void testPrintUserTotalDues() {

        //Test to print dues of all users and the total due amount- when no user records are present- total due is zero
        String[] result=UserUtils.printUserTotalDues();
        Assert.assertArrayEquals(new String[]{"total: 0.0"},result);

        UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","100"});
        UserUtils.processUserRequest(new String[]{"new","user","user2","user2@email.com","200"});
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});
        CommonUtils.processTransactionRequest(new String[]{"new","txn","user2","merchant1","50"});
        //Test to print dues of all users and the total due amount- when records exist
        result=UserUtils.printUserTotalDues();
        Assert.assertArrayEquals(new String[]{"user1: 0.0","user2: 50.0","total: 50.0"},result);
    }

}