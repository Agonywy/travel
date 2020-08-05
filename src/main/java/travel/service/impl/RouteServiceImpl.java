package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.RouteDao;
import travel.dao.RouteImgDao;
import travel.dao.SellerDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.dao.impl.RouteDaoImpl;
import travel.dao.impl.RouteImgDaoImpl;
import travel.dao.impl.SellerDaoImpl;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.RouteImg;
import travel.domain.Seller;
import travel.service.RouteService;

import java.util.List;


public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();
    private RouteImgDao imgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pageBean = new PageBean<Route>();
        //设置当前页面
        pageBean.setCurrentPage(currentPage);
        //设置每页显示的条数
        pageBean.setPageSize(pageSize);
        //设置总记录数
        int totalCount = dao.finTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int toatlPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        pageBean.setTotalPage(toatlPage);
        //设置数据集合
        int start = (currentPage-1)*pageSize;
        pageBean.setList(dao.findByPage(cid,start,pageSize,rname));
        return pageBean;
    }

    //查询路线详情信息(包括查询收藏次数)
    public Route findOne(String rid){
        //根据rid去tab_route中查询一个Route对象
        Route route = dao.findOne(Integer.parseInt(rid));
        //根据rid去查询商家sid
        //根据sid去查询商家信息
        //设置商家信息
        int sid = route.getSid();
        Seller seller = sellerDao.findSellerBySid(sid);
        route.setSeller(seller);
        //根据rid去查询图片集合信息
        //设置商品详情图片列表
        List<RouteImg> imgList = imgDao.findByRid(Integer.parseInt(rid));
        route.setRouteImgList(imgList);
        //查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }




}
