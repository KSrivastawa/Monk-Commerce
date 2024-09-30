package monk.commerce.coupons.dto;

import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;

@Setter
@Getter
public class ApplicableInputDto {

    private CouponType couponType;
    private Double amountValue;
    private ProductType productType;
    private Integer productQty;

}
