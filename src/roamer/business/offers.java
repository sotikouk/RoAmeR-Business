package roamer.business;

public class offers {
    private String productOffer;
    private String offerPrice;

    public String getProductOffer() {
        return productOffer;
    }

    public void setProductOffer(String productOffer) {
        this.productOffer = productOffer;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public offers(String productOffer, String offerPrice) {
        this.offerPrice = offerPrice;
        this.productOffer = productOffer;
    }

    public offers(){

    }
}
