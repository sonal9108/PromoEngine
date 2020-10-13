package com.example.PromotionEngine.PromotionEngine.Entity;

import com.example.PromotionEngine.PromotionEngine.Model.PromoDetails;
import com.example.PromotionEngine.PromotionEngine.PromoResponseConverter;

import javax.persistence.*;

@Entity
@Table(name = "promo_engine")
public class PromoEngine {

    @Id
    private Integer promoId;

    @Column(name = "promodetails")
    @Convert(converter = PromoResponseConverter.class)
    private PromoDetails promoDetails;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public PromoDetails getPromoDetails() {
        return promoDetails;
    }

    public void setPromoDetails(PromoDetails promoDetails) {
        this.promoDetails = promoDetails;
    }

}
