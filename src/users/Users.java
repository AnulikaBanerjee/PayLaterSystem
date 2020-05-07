package src.users;


interface User {
    public Users updateCrLimit(double newCrLimit);
    public Users clearDues(double due);
    public Users processTransaction(double transactionAmount);



}

public class Users implements User{
    private String name;
    private String email;
    private double creditLimit;
    // maintaining different variables
    private double dues;
    private double balance;
    //Users user=null;

    private Users(String name,String email,double creditLimit){
        this.name=name;
        this.email=email;
        this.creditLimit=creditLimit;
        this.dues=0;
        this.balance=creditLimit;
    }

    public double getCreditLimit(){
        return this.creditLimit;
    }

    public double getDues() {
        return dues;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public double getBalance() {
        return balance;
    }

    public static Users createUser(String name, String email, double creditLimit){
        return new Users( name, email, creditLimit);
    }



    @Override
    public Users updateCrLimit(double newCrLimit) {
        // TODO Auto-generated method stub
        this.creditLimit=newCrLimit;
        return this;
    }

    @Override
    public Users clearDues(double payback) {
        // TODO Auto-generated method stub

        this.dues=this.dues-payback;
        this.balance=this.creditLimit-this.dues;
        return this;
    }

    @Override
    public Users processTransaction(double transactionAmount) {
        this.balance=balance-transactionAmount;
        this.dues=dues+transactionAmount;
        return this;
    }


}

