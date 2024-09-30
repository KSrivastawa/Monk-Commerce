package monk.commerce.coupons.service;


import monk.commerce.coupons.dto.*;
import monk.commerce.coupons.exception.CustomException;

import java.util.List;
import java.util.Map;

public interface CouponService {

    public String createMasterCoupon(CouponDto createCouponDto) throws CustomException;

    public List<GetCouponDto> getAllCoupons() throws CustomException;

    public GetCouponDto getCouponById(Long couponId) throws CustomException;

    public String updateMasterCoupon(UpdateCouponDto updateCouponDto) throws CustomException;

    public String deleteCouponById(Long couponId) throws CustomException;

    public List<Map<String, Object>> fetchAllApplicableCoupons(ApplicableInputDto applicableInputDto) throws CustomException;

    public ApplyCouponDto applyCoupon(Long couponId, Double totalAmount) throws CustomException;


}
