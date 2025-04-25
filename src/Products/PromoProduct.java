package Products;

import java.util.Date;

public class PromoProduct extends Product{
    private double promoPercentage;
    private Date endDate;

    public PromoProduct(String name, int price, String description, double promoPercentage, Date endDate) {
        super(name, price, description);
        this.promoPercentage = promoPercentage;
        this.endDate = endDate;
    }

    public double getPromoPercentage() {
        return promoPercentage;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setPromoPercentage(double promoPercentage) {
        this.promoPercentage = promoPercentage;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
