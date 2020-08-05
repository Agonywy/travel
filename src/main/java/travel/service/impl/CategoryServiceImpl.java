package travel.service.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import travel.dao.CategoryDao;
import travel.dao.impl.CategoryDaoImpl;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    public List<Category> findAll(){
        //1.从redis中查询
        //1.1获取Jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可以使用sortedSet来排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询category中的分数cid和值cname
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //2.判断查询集合是否为空
        List<Category> all = null;
        //3.如果为null,需要从数据库中拆线呢,并将数据存入redis
        if(categorys == null || categorys.size()==0){
            all = dao.findAll();
            //将集合数据存储到redis中的 category的key
            for (int i = 0; i < all.size(); i++) {
                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
            }
        }else{
                all = new ArrayList<Category>();
            for (Tuple tuple:categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                all.add(category);
            }
        }

        //4.如果不为空,直接返回集合
        return all;
    }
}
