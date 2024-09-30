package monk.commerce.coupons.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import monk.commerce.coupons.enums.CouponType;
import monk.commerce.coupons.enums.ProductType;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "coupon_master")
public class CouponMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_type")
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "amount_value")
    private Double amountValue;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "product_qty")
    private Integer productQty;

    @Column(name = "avail_condition")
    private String availCondition;

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }


}
