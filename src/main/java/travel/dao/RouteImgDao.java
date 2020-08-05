package travel.dao;

import travel.domain.RouteImg;
import java.util.List;
public interface RouteImgDao {
    public List<RouteImg> findByRid(int rid);
}
