package com.payment.mapper;

import com.payment.model.Order;

import java.util.List;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order
     *
     * @mbg.generated Thu Jan 05 02:42:05 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order
     *
     * @mbg.generated Thu Jan 05 02:42:05 CST 2017
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order
     *
     * @mbg.generated Thu Jan 05 02:42:05 CST 2017
     */
    List<Order> selectAll();
}