package lyzzcw.stupid.spring.statemachine.controller;

import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.service.OrderService;
import lyzzcw.stupid.spring.statemachine.service.OrderStateMachineService;
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
@RequestMapping("/orderState")
public class OrderStateMachineController {
    @Resource
    private OrderStateMachineService orderStateMachineService;

    /**
     * 根据id查询订单状态
     */
    @RequestMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        //根据id查询当前状态
        return Result.ok(orderStateMachineService.read(id).getDesc());
    }

    /**
     * 根据id重置订单状态
     */
    @RequestMapping("/setById")
    public Result setById(@RequestParam("id") String id,
                          @RequestParam("state") int state) {
        orderStateMachineService.write(id, OrderState.getByKey(state));
        return Result.ok();
    }

    /**
     * 导出UML文件
     */
    @GetMapping("/exportToUml")
    public Result exportToUml() {
        orderStateMachineService.exportToUml();
        return Result.ok();
    }
}