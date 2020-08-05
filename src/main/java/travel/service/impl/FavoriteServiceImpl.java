package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.domain.Favorite;
import travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByUidAndRid(Integer.parseInt(rid), uid);
        if(favorite == null){
            return false;
        }
        return true;
    }
}
