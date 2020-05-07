package src.merchants;


interface Merchant{
    public Merchants updateDiscountRate(float discountRate);

}
public class Merchants implements Merchant{
    private String name;
    private String email;
    private float discountRate;

    private Merchants(String name,String email,float discountRate){
        this.name=name;
        this.email=email;
        this.discountRate=discountRate;
    }

    public float getDiscountRate(){
        return this.discountRate;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public static Merchants createMerchant(String name,String email,float discountRate){
            return new Merchants( name, email, discountRate);
    }
    @Override
    public Merchants updateDiscountRate(float discountRate) {
        // TODO Auto-generated method stub
        this.discountRate=discountRate;
        return this;
    }

    
}