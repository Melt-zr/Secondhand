package com.trade.triorderservice.controller;

import com.trade.triorderservice.domain.dto.OrderDTO;
import com.trade.triorderservice.domain.po.Order;
import com.trade.triorderservice.domain.vo.ResultVO;
import com.trade.triorderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
    * 创建订单
    * @param orderDTO 订单信息
    * @return 创建结果
    */
    @RequestMapping("/create")
    public ResultVO<String> createOrder(@Validated @RequestBody OrderDTO orderDTO) {
        try {
            orderService.createOrder(orderDTO);
            return ResultVO.success("下单成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("下单失败，请稍后重试");
        }
    }

    /**
    * 订单已支付，未发货
    * @param orderNo 订单号
    * @return 订单支付结果
    */
    @RequestMapping("/paid/{orderNo}")
    public ResultVO<String> paidOrder(@Validated @PathVariable("orderNo") String orderNo) {
        try {
            orderService.paidOrder(orderNo);
            return ResultVO.success("订单已支付");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("订单支付失败，请稍后重试");
        }
    }

    /**
    * 订单已发货，未收货
    * @param orderNo 订单号，trackingNumber 快递单号
    * @return 订单发货结果
    * */
    @RequestMapping("/delivered/{orderNo}/{trackingNumber}")
    public ResultVO<String> deliveredOrder(@Validated @PathVariable("orderNo") String orderNo, @Validated @PathVariable("trackingNumber") String trackingNumber) {
        try {
            orderService.deliveredOrder(orderNo, trackingNumber);
            return ResultVO.success("订单已发货");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("订单发货失败，请稍后重试");
        }
    }

    /**
    * 订单已收货，未评价
    * @param orderNo 订单号
    * @return 订单收货结果
    * */
    @RequestMapping("/received/{orderNo}")
    public ResultVO<String> receivedOrder(@Validated @PathVariable("orderNo") String orderNo) {
        try {
            orderService.receivedOrder(orderNo);
            return ResultVO.success("订单已收货");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("订单收货失败，请稍后重试");
        }
    }
    /**
    * 取消订单
    * @param orderNo 订单号
    * @return 订单取消结果
    * */
    @RequestMapping("/cancel/{orderNo}")
    public ResultVO<String> cancelOrder(@Validated @PathVariable("orderNo") String orderNo) {
        try {
            orderService.cancelOrder(orderNo);
            return ResultVO.success("订单已取消");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("订单取消失败，请稍后重试");
        }
    }

    /**
     * 获取用户的购买商品列表
     *
     * @param buyerId
     * @return 订单Id列表
     */
    @RequestMapping("/list/{buyerId}")
    public List<Order> listOrders(@Validated @PathVariable("buyerId") Integer buyerId) {
        return orderService.getListOrders(buyerId);
    }
}
