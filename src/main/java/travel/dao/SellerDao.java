package travel.dao;

import travel.domain.Seller;

public interface SellerDao {
    public Seller findSellerBySid(int sid);
}
