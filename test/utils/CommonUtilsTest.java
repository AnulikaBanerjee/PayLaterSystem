package test.utils;

import org.junit.Assert;
import org.junit.Test;
import src.util.CommonUtils;
import src.util.MerchantUtils;
import src.util.UserUtils;

import static org.junit.Assert.*;
import static src.util.CommonUtils.*;

public class CommonUtilsTest {

    @Test
    public void testValidateEmail() {
        //Valid email id format test
        Assert.assertTrue(CommonUtils.validateEmail("user@domain.com"));

        //Invalid email format test'
        Assert.assertFalse(CommonUtils.validateEmail("j#hh@hh!"));
    }

    @Test
    public void testProcessTransactionRequest() {
        UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});

        //Test a valid transaction request
        String result=CommonUtils.processTransactionRequest(new String[]{"new","txn","user1","merchant1","200"});
        Assert.assertEquals("success!",result);
        //Test: Transaction value exceeds user credit limit - Rejection
        result=CommonUtils.processTransactionRequest(new String[]{"new","txn","user1","merchant1","400"});
        Assert.assertEquals("rejected! (reason: credit limit)",result);
        //Test: Insufficient request data
        result=CommonUtils.processTransactionRequest(new String[]{"new","user1","merchant1","400"});
        Assert.assertEquals(invalidRequestError,result);
        //Test: User Not in record
        result=CommonUtils.processTransactionRequest(new String[]{"new","txn","user3","merchant1","400"});
        Assert.assertEquals(userNotFound,result);
        //Test: Merchant not in record
        result=CommonUtils.processTransactionRequest(new String[]{"new","txn","user1","merchant3","400"});
        Assert.assertEquals(merchantNotFound,result);
        //Test: Invalid value
        result=CommonUtils.processTransactionRequest(new String[]{"new","txn","user1","merchant1","Rs. 400"});
        Assert.assertEquals(invalidValueError,result);

    }
}