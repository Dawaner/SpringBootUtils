package com.dawan.lesson.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dawan.lesson.controller.vo.CourseSelectionStudentVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Dawan
 * @date 2023/3/8
 * 使用方法：
 * PageUtils pageUtils
 * Page<VO> page = PageUtils.page(pageUtils);
 * 注意事项：
 * 排序字段名称使用sql名称，例如:user_id而不是userId
 * 字段名称错误，抛出SQLSyntaxErrorException
 */
public class PageUtils<T> {

    /**
     * 每页显示条数，默认 10
     */
    private long size;

    /**
     * 当前页，起始页 1
     */
    private long current;

    /**
     * 排序字段信息
     */
    private List<String> orders;

    /**
     * 排序规则,DESC或者ASC
     * 要求数量与排序字段信息(orders)相同
     */
    private List<String> orderType;

    /**
     * 全部按DESC或者ASC排序
     */
    private String allOrderBy;

    public PageUtils() {
        this.size = 10;
        this.current = 1;
        this.orders = new ArrayList();
        this.orderType = new ArrayList();
        this.allOrderBy = null;
    }

    public PageUtils(long size, long current) {
        this.size = size;
        this.current = current;
    }

    public PageUtils(long size, long current, List<String> orders) {
        this.size = size;
        this.current = current;
        this.orders = orders;
    }

    public PageUtils(long size, long current, List<String> orders, List<String> orderType) {
        this.size = size;
        this.current = current;
        this.orders = orders;
        this.orderType = orderType;
    }

    public PageUtils(long size, long current, List<String> orders, String allOrderBy) {
        this.size = size;
        this.current = current;
        this.orders = orders;
        this.allOrderBy = allOrderBy;
    }

    /**
     * 默认排序
     */
    public static <T> Page<T> page(long size, long current) {
        Page<T> page = new Page<>(current, size);
        return page;
    }

    /**
     * 默认全按ASC排序
     */
    public static <T> Page<T> page(long size, long current, List<String> orders) {
        Page<T> page = new Page<>(current, size);
        for (String order: orders) {
            page.addOrder(OrderItem.asc(order));
        }
        return page;
    }

    /**
     * 按规则排序
     */
    public static <T> Page<T> page(long size, long current, List<String> orders, List<String> orderType) {
        Page<T> page = new Page<>(current, size);
        if (orders.size()==orderType.size()){
            for (int i = 0; i < orders.size(); i++) {
                if (Objects.equals(orderType.get(i), "DESC")){
                    page.addOrder(OrderItem.desc(orders.get(i)));
                } else if (Objects.equals(orderType.get(i), "ASC")) {
                    page.addOrder(OrderItem.asc(orders.get(i)));
                } else {
                    throw new RuntimeException("规则名称应为DESC或ASC");
                }
            }
        }
        else {
            throw new RuntimeException("请传入相等数量的字段名称与规则");
        }
        return page;
    }

    /**
     * 全部按DESC或ASC排序
     */
    public static <T> Page<T> page(long size, long current, List<String> orders, String allOrderBy) {
        Page<T> page = new Page<>(current, size);
        if (Objects.equals(allOrderBy, "DESC")){
            for (String order: orders) {
                page.addOrder(OrderItem.desc(order));
            }
        } else if (Objects.equals(allOrderBy, "ASC")) {
            for (String order: orders) {
                page.addOrder(OrderItem.asc(order));
            }
        } else {
            throw new RuntimeException("规则名称应为DESC或ASC");
        }
        return page;
    }

    /**
     * 主要方法
     * 建议在Impl中使用
     */
    public static <T> Page<T> page(PageUtils pageUtils) {
        if (pageUtils.allOrderBy==null){
            if (pageUtils.orderType==null) {
                if (pageUtils.orders==null) {
                    return page(pageUtils.size,pageUtils.current);
                } else {
                    return page(pageUtils.size,pageUtils.current,pageUtils.orders);
                }
            } else {
                return page(pageUtils.size,pageUtils.current,pageUtils.orders,pageUtils.orderType);
            }
        } else {
            return page(pageUtils.size,pageUtils.current,pageUtils.orders,pageUtils.allOrderBy);
        }
    }


    public PageUtils<?> pageData(long size, long current){
        this.size = size;
        this.current = current;
        return this;
    }

    public PageUtils<?> pageData(long size, long current, List<String> orders){
        this.size = size;
        this.current = current;
        this.orders = orders;
        return this;
    }
    public PageUtils<?> pageData(long size, long current, List<String> orders, List<String> orderType){
        this.size = size;
        this.current = current;
        this.orders = orders;
        this.orderType = orderType;
        return this;
    }
    public PageUtils<?> pageData(long size, long current, List<String> orders, String allOrderBy){
        this.size = size;
        this.current = current;
        this.orders = orders;
        this.allOrderBy = allOrderBy;
        return this;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public List<String> getOrderType() {
        return orderType;
    }

    public void setOrderType(List<String> orderType) {
        this.orderType = orderType;
    }

    public String getAllOrderBy() {
        return allOrderBy;
    }

    public void setAllOrderBy(String allOrderBy) {
        this.allOrderBy = allOrderBy;
    }
}
