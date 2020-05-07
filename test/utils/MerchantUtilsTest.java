package test.utils;

import org.junit.Assert;
import org.junit.Test;
import src.util.CommonUtils;
import src.util.MerchantUtils;
import src.util.UserUtils;

import static org.junit.Assert.*;

import static src.util.CommonUtils.*;

public class MerchantUtilsTest {

    @Test
    public void testProcessMerchantRequest() {
        //Test merchant creation
        String result= MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});
        Assert.assertEquals("merchant1(5.0%)",result);
        //Test Insufficient request data
        result= MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant2","mer1gmail.com"});
        Assert.assertEquals(invalidRequestError,result);
        //Test invalid email
        result= MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant2","mer1gmail.com","5%"});
        Assert.assertEquals(invalidEmailError,result);
        //Test invalid data
        result= MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant2","mer1@gmail.com","5s%"});
        Assert.assertEquals(invalidValueError,result);
        //Test recreation of existing merchant - should be rejected
        result= MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","5%"});
        Assert.assertEquals("rejected! (reason: Merchant exists!)",result);


    }


    @Test
    public void testPrintDiscountByMerchant() {
        UserUtils.processUserRequest(new String[]{"new","user","user1","user1@email.com","500"});
        UserUtils.processUserRequest(new String[]{"new","user","user2","user2@email.com","200"});
        UserUtils.processUserRequest(new String[]{"new","user","user3","user3@email.com","300"});
        UserUtils.processUserRequest(new String[]{"new","user","user4","user4@email.com","700"});
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","10%"});
        //Test with 0 discount from merchants
        String result=MerchantUtils.printDiscountByMerchant(new String[]{"report","discount","merchant1"});
        Assert.assertEquals("No records/Invalid merchant",result);
        //Test Insufficient request data
        result=MerchantUtils.printDiscountByMerchant(new String[]{"report","discount"});
        Assert.assertEquals(invalidRequestError,result);

        CommonUtils.processTransactionRequest(new String[]{"new","txn","user2","merchant1","200"});
        CommonUtils.processTransactionRequest(new String[]{"new","txn","user4","merchant1","700"});
        //Test with invalid merchants
        result=MerchantUtils.printDiscountByMerchant(new String[]{"report","discount","merchant3"});
        Assert.assertEquals("No records/Invalid merchant",result);
        //Test to print total discount earned from given merchant
        result=MerchantUtils.printDiscountByMerchant(new String[]{"report","discount","merchant1"});
        Assert.assertEquals("90.0",result);
    }


    @Test
    public void testUpdateMerchantDiscount() {
        MerchantUtils.processMerchantRequest(new String[]{"new","merchant","merchant1","mer1@gmail.com","10%"});
        //Test Insufficient request data
        String result=MerchantUtils.updateMerchantDiscount(new String[]{"update","merchant1","5%"});
        Assert.assertEquals(invalidRequestError,result);
        //Test invalid value
        result=MerchantUtils.updateMerchantDiscount(new String[]{"update","merchant","merchant1","5s%"});
        Assert.assertEquals(invalidValueError,result);
        //Test unknown record updation attempt
        result=MerchantUtils.updateMerchantDiscount(new String[]{"update","merchant","merchant2","5%"});
        Assert.assertEquals(merchantNotFound,result);
        //Test success condition
        result=MerchantUtils.updateMerchantDiscount(new String[]{"update","merchant","merchant1","5%"});
        Assert.assertEquals("merchant1(5.0%)",result);

    }
}