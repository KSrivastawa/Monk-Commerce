package monk.commerce.coupons.dto;


import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;

@Setter
@Getter
public class ApplyCouponDto {

    private CouponType couponType;
    private ProductType productType;
    private Integer productQty;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;

}
