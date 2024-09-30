package monk.commerce.coupons.dto;

import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;


@Setter
@Getter
public class CouponDto {

    private CouponType couponType;
    private Boolean isActive;
    private Double amountValue;
    private Double discount;
    private ProductType productType;
    private Integer productQty;

}
