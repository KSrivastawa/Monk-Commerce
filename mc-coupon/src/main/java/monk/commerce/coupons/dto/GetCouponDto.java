package monk.commerce.coupons.dto;

import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.entity.CouponMasterEntity;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;

@Setter
@Getter
public class GetCouponDto {

    private Long couponId;
    private CouponType couponType;
    private Boolean isActive;
    private Double amountValue;
    private Double discount;
    private ProductType productType;
    private Integer productQty;
    private String availCondition;

    public static GetCouponDto getCouponInstance(CouponMasterEntity entity){
        GetCouponDto dto = new GetCouponDto();
        dto.setCouponId(entity.getCouponId());
        dto.setCouponType(entity.getCouponType());
        dto.setDiscount(entity.getDiscountPercent());
        dto.setAmountValue(entity.getAmountValue());
        dto.setIsActive(entity.getIsActive());
        dto.setAvailCondition(entity.getAvailCondition());
        dto.setProductType(entity.getProductType());
        dto.setProductQty(entity.getProductQty());
        return dto;
    }


}
