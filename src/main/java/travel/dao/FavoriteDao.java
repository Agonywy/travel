package travel.dao;

import travel.domain.Favorite;

public interface FavoriteDao {
    Favorite findByUidAndRid(int rid, int uid);

    int findCountByRid(int rid);
}
