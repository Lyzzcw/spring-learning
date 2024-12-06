package lyzzcw.stupid.spring.statemachine.controller;

import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.service.OrderService;
import lyzzcw.work.component.domain.common.entity.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:51
 * Description: No Description
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 根据id查询订单
     */
    @RequestMapping("/getById")
    public Result getById(@RequestParam("id") Long id) {
        //根据id查询订单
        return Result.ok(orderService.getById(id));
    }

    /**
     * 确认下单
     */
    @PostMapping("/confirm")
    public Result confirm(@RequestBody Order order) {
        //确认下单
        return Result.ok(orderService.confirm(order));
    }

    /**
     * 对订单取消支付
     */
    @PostMapping("/cancel")
    public Result cancel(@RequestParam("id") Long id) {
        //对订单取消支付
        orderService.cancel(id);
        return Result.ok();
    }

    /**
     * 对订单进行支付
     */
    @PostMapping("/pay")
    public Result pay(@RequestParam("id") Long id) {
        //对订单进行支付
        orderService.pay(id);
        return Result.ok();
    }

    /**
     * 对订单进行发货
     */
    @RequestMapping("/deliver")
    public Result deliver(@RequestParam("id") Long id) {
        //对订单进行确认收货
        orderService.deliver(id);
        return Result.ok();
    }

    /**
     * 对订单进行确认收货
     */
    @RequestMapping("/receive")
    public Result receive(@RequestParam("id") Long id) {
        //对订单进行确认收货
        orderService.receive(id);
        return Result.ok();
    }
}