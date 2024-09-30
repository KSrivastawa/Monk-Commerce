package monk.commerce.coupons.service.impl;

import monk.commerce.coupons.dto.*;
import monk.commerce.coupons.entity.CouponMasterEntity;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.exception.CustomException;
import monk.commerce.coupons.repository.CouponRepo;
import monk.commerce.coupons.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static monk.commerce.coupons.dto.UpdateCouponDto.getUpdatedCouponInstance;


@Service
@EnableScheduling
@Configuration
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepo;


    @Override
    public String createMasterCoupon(CouponDto couponDto) throws CustomException {
        // Check points
        checkPointsForCreateCoupon(couponDto);
        checkCouponTypeAndAmountValueOrProductTypeAndProductQty(couponDto);

        // save to entity
        try{
            CouponMasterEntity entity = new CouponMasterEntity();
            entity.setCouponType(couponDto.getCouponType());
            entity.setAmountValue(couponDto.getAmountValue());
            entity.setDiscountPercent(couponDto.getDiscount());
            entity.setIsActive(couponDto.getIsActive());
            entity.setProductType(couponDto.getProductType());
            entity.setProductQty(couponDto.getProductQty());
            // get avail condition message
            entity.setAvailCondition(getAvailConditionMessage(couponDto));

            couponRepo.save(entity);

            return "Coupon created successfully";
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }

    }

    private static String getAvailConditionMessage(CouponDto couponDto) {
        String condition = "";
        if(couponDto.getCouponType().equals(CouponType.CART_WISE)){
            condition = Math.round(couponDto.getDiscount()) + "% off on carts over Rs. "+ Math.round(couponDto.getAmountValue());
        }else if(couponDto.getCouponType().equals(CouponType.PRODUCT_WISE)){
            condition = Math.round(couponDto.getDiscount()) + "% off on "+ couponDto.getProductType()+" products";
        }else if(couponDto.getCouponType().equals(CouponType.BxGy)){
            condition = "Buy "+ couponDto.getProductQty()+" Get "+ Math.round(couponDto.getDiscount());
        }
        return condition;
    }

    private void checkCouponTypeAndAmountValueOrProductTypeAndProductQty(CouponDto couponDto){
        boolean isExistsCouponProductAmount = couponRepo.existsByCouponTypeAndProductTypeAndAmountValue(couponDto.getCouponType(), couponDto.getProductType(), couponDto.getAmountValue());
        if(isExistsCouponProductAmount){
            throw new CustomException("Coupon-product-amount combination is already exists");
        }

        boolean isExistsCouponAmount = couponRepo.existsByCouponTypeAndProductTypeNullAndAmountValue(couponDto.getCouponType(), couponDto.getAmountValue());
        if(isExistsCouponAmount){
            throw new CustomException("Coupon-amount combination is already exists");
        }
    }

    // This check Points to be implemented through UI side for real time response
    private void checkPointsForCreateCoupon(CouponDto couponDto){
        String message = " can't be null";
        if(couponDto.getIsActive()==null){
            throw new CustomException("IsActive"+message);
        }if(couponDto.getCouponType()==null){
            throw new CustomException("Coupon type"+message);
        }if(couponDto.getDiscount()==null){
            throw new CustomException("Discount"+message);
        }if(couponDto.getAmountValue()==null && !couponDto.getCouponType().equals(CouponType.BxGy)){
            throw new CustomException("Amount value"+message);
        }if(couponDto.getCouponType().equals(CouponType.BxGy) && couponDto.getProductQty()==null){
            throw new CustomException("Product qty"+message);
        }
    }

    @Override
    public List<GetCouponDto> getAllCoupons() throws CustomException {
        try{
            List<CouponMasterEntity> list = couponRepo.findByOrderByCreatedDateDesc();
            if(!list.isEmpty()){
                return list.stream().map(GetCouponDto::getCouponInstance).toList();
                // Here instead of using DTO, can be use Map<String, Object> as well to convert data from entities.
            }else {
                return new ArrayList<>();
            }
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public GetCouponDto getCouponById(Long couponId) throws CustomException {
        try{
            CouponMasterEntity entity = couponRepo.findByCouponId(couponId);
            if(Objects.nonNull(entity)){
                return GetCouponDto.getCouponInstance(entity);
            }else {
                throw new CustomException("Coupon not available with this ID: "+couponId);
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public String updateMasterCoupon(UpdateCouponDto updateCouponDto) throws CustomException {
        try{
            CouponMasterEntity existingEntity = couponRepo.findByCouponId(updateCouponDto.getCouponId());
            if(Objects.nonNull(existingEntity)){
                checkForUpdateCouponTypeAndAmountValueOrProductTypeAndProductQty(updateCouponDto, existingEntity);
                getUpdatedCouponInstance(updateCouponDto, existingEntity);
                couponRepo.save(existingEntity);
                return "Coupon updated successfully";
            }else {
                throw new CustomException("Coupon not available with this ID: "+updateCouponDto.getCouponId());
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    private void checkForUpdateCouponTypeAndAmountValueOrProductTypeAndProductQty(UpdateCouponDto couponDto, CouponMasterEntity entity){
        boolean isExistsCouponProductAmount = couponRepo.existsByCouponTypeAndProductTypeAndAmountValue(entity.getCouponType(), entity.getProductType(), couponDto.getAmountValue());
        if(isExistsCouponProductAmount){
            throw new CustomException("Coupon-product-amount combination is already exists");
        }
        boolean isExistsCouponAmount = couponRepo.existsByCouponTypeAndProductTypeNullAndAmountValue(entity.getCouponType(), couponDto.getAmountValue());
        if(isExistsCouponAmount){
            throw new CustomException("Coupon-amount combination is already exists");
        }
    }

    @Override
    public String deleteCouponById(Long couponId) throws CustomException {
        try{
            CouponMasterEntity existingEntity = couponRepo.findByCouponId(couponId);
            if(Objects.nonNull(existingEntity)){
                couponRepo.delete(existingEntity);
                return "Coupon deleted successfully";
            }else {
                throw new CustomException("Coupon doesn't exists");
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> fetchAllApplicableCoupons(ApplicableInputDto applicableInputDto) throws CustomException {
        try {
            return getListOfAllCouponsToBeSelect(applicableInputDto);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    // Discount calculation for Distributor on Order Value
    public List<Map<String, Object>> getListOfAllCouponsToBeSelect(ApplicableInputDto applicableInputDto){
        List<Map<String, Object>> map = new ArrayList<>();
        if(applicableInputDto.getCouponType().equals(CouponType.CART_WISE)){
            map = couponRepo.findAllCouponsByCouponTypeAndAmountValue(applicableInputDto.getAmountValue());
        }else if(applicableInputDto.getCouponType().equals(CouponType.PRODUCT_WISE)){
            map = couponRepo.findAllCouponsByCouponTypeAndProductType(applicableInputDto.getProductType()==null?null:applicableInputDto.getProductType().toString());
        }else if(applicableInputDto.getCouponType().equals(CouponType.BxGy)){
            map = couponRepo.findAllCouponsByCouponTypeAndProductQty(applicableInputDto.getProductType()==null?null:applicableInputDto.getProductType().toString(), applicableInputDto.getProductQty());
        }
        return map;
    }


    @Override
    public ApplyCouponDto applyCoupon(Long couponId, Double totalAmount) throws CustomException {
        try{
            CouponMasterEntity existingEntity = couponRepo.findByCouponId(couponId);
            if(Objects.nonNull(existingEntity)){
                double discountAmount = (totalAmount/100)*existingEntity.getDiscountPercent();
                ApplyCouponDto dto = getTotalDetailsAfterCouponApplied(discountAmount, existingEntity.getCouponType(), totalAmount);
                return dto;
            }else {
                throw new CustomException("Coupon not Found");
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    private ApplyCouponDto getTotalDetailsAfterCouponApplied(Double discountAmount, CouponType couponType, Double totalAmount){
        ApplyCouponDto dto = new ApplyCouponDto();
        dto.setCouponType(couponType);
        dto.setTotalAmount(totalAmount);
        dto.setDiscountAmount(discountAmount);
        dto.setFinalAmount(totalAmount-discountAmount);
        return dto;
    }


    // Each coupon expired after 7 days from the created date
    // Scheduler will check once in a day at specific time and delete that coupon if expired
    @Scheduled(cron = "11 11 11 * * *")
    public void expiredCouponAfterWeeklyBasis(){
        try{
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
            List<CouponMasterEntity> expiredCoupons = couponRepo.findByCreatedDateBefore(sevenDaysAgo);
            if(!expiredCoupons.isEmpty()){
                for(CouponMasterEntity entity : expiredCoupons){
                    couponRepo.delete(entity);
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }


}
