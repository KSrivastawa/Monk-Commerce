package monk.commerce.coupons.controller;

import monk.commerce.coupons.dto.*;
import monk.commerce.coupons.exception.CustomException;
import monk.commerce.coupons.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;


    // Create Coupon
    @PostMapping("/createCoupon")
    public ResponseEntity<String> createMasterCouponHandler(@RequestBody CouponDto couponDto) throws CustomException {
        String result = couponService.createMasterCoupon(couponDto);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    // Get All Coupons
    @GetMapping("/getCoupons")
    public ResponseEntity<List<GetCouponDto>> getAllCouponsHandler() throws CustomException {
        List<GetCouponDto> result = couponService.getAllCoupons();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    // Get Coupon
    @GetMapping("/getCoupon")
    public ResponseEntity<GetCouponDto> getCouponByIdHandler(@RequestParam Long couponId) throws CustomException {
        GetCouponDto result = couponService.getCouponById(couponId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    // Update Coupon
    @PutMapping("/updateCoupon")
    public ResponseEntity<String> updateMasterCouponHandler(@RequestBody UpdateCouponDto updateCouponDto) throws CustomException {
        String result = couponService.updateMasterCoupon(updateCouponDto);
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }


    // Delete Coupon
    @DeleteMapping("/deleteCoupon")
    public ResponseEntity<String> deleteCouponByIdHandler(@RequestParam Long couponId) throws CustomException {
        String result = couponService.deleteCouponById(couponId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    // Fetch All Applicable Coupons
    @PostMapping("/fetchApplicableCoupons")
    public ResponseEntity<List<Map<String, Object>>> fetchAllApplicableCouponsHandler(@RequestBody ApplicableInputDto applicableInputDto) throws CustomException {
        List<Map<String, Object>> result = couponService.fetchAllApplicableCoupons(applicableInputDto);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    // Apply Coupon
    @PostMapping("/applyCoupon")
    public ResponseEntity<ApplyCouponDto> applyCouponHandler(@RequestParam Long couponId, @RequestParam Double totalAmount) throws CustomException {
        ApplyCouponDto result = couponService.applyCoupon(couponId, totalAmount);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


}
