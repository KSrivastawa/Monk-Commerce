package monk.commerce.coupons.dto;


import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.entity.CouponMasterEntity;

@Setter
@Getter
public class UpdateCouponDto {

    private Long couponId;
    private Double amountValue;
    private Double discount;
    private Integer productQty;

    public static CouponMasterEntity getUpdatedCouponInstance(UpdateCouponDto updateCouponDto, CouponMasterEntity entity){
        if(updateCouponDto.getAmountValue() != null){
            entity.setAmountValue(updateCouponDto.getAmountValue());
        }if(updateCouponDto.getDiscount() != null){
            entity.setDiscountPercent(updateCouponDto.getDiscount());
        }if(updateCouponDto.getProductQty() != null){
            entity.setProductQty(updateCouponDto.getProductQty());
        }
        return entity;
    }

}
