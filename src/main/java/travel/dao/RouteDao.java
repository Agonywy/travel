package travel.dao;

import travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询总记录数
     * @param cid
     */
    public int finTotalCount(int cid,String rname);

    /**
     * 根据cid,start,pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize,String rname);

    //根据rid查询一个Route对象
    Route findOne(int rid);
}
