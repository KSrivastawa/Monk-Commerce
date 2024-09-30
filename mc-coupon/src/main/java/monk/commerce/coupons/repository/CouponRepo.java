package monk.commerce.coupons.repository;

import monk.commerce.coupons.entity.CouponMasterEntity;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CouponRepo extends JpaRepository<CouponMasterEntity, Long> {

    @Query("""
            select (count(c) > 0) from CouponMasterEntity c
            where c.couponType = ?1 and c.productType = ?2 and c.amountValue = ?3""")
    boolean existsByCouponTypeAndProductTypeAndAmountValue(CouponType couponType, ProductType productType, Double amountValue);

    @Query("""
            select (count(c) > 0) from CouponMasterEntity c
            where c.couponType = ?1 and c.productType is null and c.amountValue = ?2""")
    boolean existsByCouponTypeAndProductTypeNullAndAmountValue(CouponType couponType, Double amountValue);

    @Query("select c from CouponMasterEntity c order by c.createdDate DESC")
    List<CouponMasterEntity> findByOrderByCreatedDateDesc();

    @Query("select c from CouponMasterEntity c where c.couponId = ?1")
    CouponMasterEntity findByCouponId(Long couponId);


    @Query(value ="SELECT c.coupon_id as couponId, c.coupon_type as couponType, c.discount_percent as discountPercent, " +
            "c.avail_condition as availCondition, c.amount_value as amountValue," +
            "CASE WHEN (c.amount_value <= ?1) THEN 1 ELSE 0 END AS isApplicable FROM coupon_master c where c.coupon_type = 'CART_WISE'" , nativeQuery = true)
    List<Map<String, Object>> findAllCouponsByCouponTypeAndAmountValue(Double amountValue);

    @Query(value = "SELECT c.coupon_id AS couponId, c.coupon_type AS couponType, c.discount_percent AS discountPercent, c.avail_condition AS availCondition, c.product_type AS productType " +
            "FROM coupon_master c " +
            "WHERE c.coupon_type = 'PRODUCT_WISE' AND (?1 IS NULL OR c.product_type = ?1)",
            nativeQuery = true)
    List<Map<String, Object>> findAllCouponsByCouponTypeAndProductType(String productType);

    @Query(value = "SELECT c.coupon_id AS couponId, c.coupon_type AS couponType, c.product_type AS productType, c.discount_percent AS discountPercent, c.avail_condition AS availCondition, c.product_qty AS productQty " +
            "FROM coupon_master c " +
            "WHERE c.coupon_type = 'BxGy' AND c.product_type = ?1 AND (?2 IS NULL OR c.product_qty = ?2)",
            nativeQuery = true)
    List<Map<String, Object>> findAllCouponsByCouponTypeAndProductQty(String productType, Integer productQty);

    @Query("select c from CouponMasterEntity c where c.createdDate < ?1")
    List<CouponMasterEntity> findByCreatedDateBefore(LocalDateTime createdDate);


}
