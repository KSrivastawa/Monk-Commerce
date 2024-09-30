# MONK-COMMERCE

Coupons Management API for an E-commerce Website
------------------------------------------------
Built a RESTful API to manage and apply different types of discount coupons (cart-wise, product-wise, and BxGy) for an e-commerce platform, with the ability to easily add new types of coupons in the future.

Coupons Designed:
----------------
1. Coupon Types:
● Cart-wise: Apply a discount to the entire cart if the total amount exceeds a
certain threshold.
● Product-wise: Apply a discount to specific products.
● BxGy: “Buy X, Get Y” deals with a repetition limit and can be applicable to a set
of products (e.g., Buy 3 of Product X or Product Y, get 1 of Product P and Product
Q free and so on).


- Note: Scheduler implemented to expire coupon on weekly basis.
  

3. API Endpoints:
● POST /coupons: Create a new coupon.

![createCoupon](https://github.com/user-attachments/assets/9fbc39b2-8a9a-4622-8486-5f19d1cea1e2)

● GET /coupons: Retrieve all coupons.

![getCoupons](https://github.com/user-attachments/assets/b7d33cad-89f2-40f8-ac61-b7b6850b9ba9)

● GET /coupons/{id}: Retrieve a specific coupon by its ID.

![getCouponById](https://github.com/user-attachments/assets/9d332c06-ff8d-4706-8248-a24cdc1cc9f4)

● PUT /coupons/{id}: Update a specific coupon by its ID.

![updateCoupon](https://github.com/user-attachments/assets/96e255a0-a63d-477b-a35e-f972dff98977)

● DELETE /coupons/{id}: Delete a specific coupon by its ID.

● POST /applicable-coupons: Fetch all applicable coupons for a given cart and
calculate the total discount that will be applied by each coupon.

![fetchAllApplicableCoupons](https://github.com/user-attachments/assets/7a00bfc2-db64-4167-8abf-b9dc31f49585)

● POST /apply-coupon/{id}: Apply a specific coupon to the cart and return the
updated cart with discounted prices for each item.

![applyCoupon](https://github.com/user-attachments/assets/743e2925-3a24-4591-a3bf-9c6b58fac580)


4. Coupon Structure:
● Each coupon have an ID, type (cart-wise, product-wise, BxGy), discount
details, and conditions for applicability.
● The implementation designed to easily add new types of coupons in
the future.
5. Database:
● Here MySql database used.
● Store and fetch coupon details and conditions.

6. Error Handling:
● Implemented to handled error (e.g., coupon not found, invalid input, conditions
not met).
