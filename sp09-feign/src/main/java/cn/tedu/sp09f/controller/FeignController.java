package cn.tedu.sp09f.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp09f.feign.ItemFeignClient;
import cn.tedu.sp09f.feign.OrderFeignClient;
import cn.tedu.sp09f.feign.UserFeignClient;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeignController {

    private ItemFeignClient itemFeignClient;
    @Autowired(required = false)
    public void setItemFeignClient(ItemFeignClient itemFeignClient){
        this.itemFeignClient = itemFeignClient;
    }
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;


    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        return itemFeignClient.getItems(orderId);
    }
    @GetMapping("/item-service/decreaseNumber")
    JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        return itemFeignClient.decreaseNumber( items);
    }
    @GetMapping("/user-service/{userId}")
    JsonResult<User> getUser(@PathVariable Integer userId){
        return userFeignClient.getUser(userId);
    }

    @GetMapping("/user-service/{userId}/score")
    JsonResult<?> addScore (@PathVariable Integer userId,@RequestParam Integer score){
        return userFeignClient.addScore(userId, score);
    }
    @GetMapping("/order-service/{orderId}")
    JsonResult<Order> getOrder(@PathVariable String orderId){
        return orderFeignClient.getOrder(orderId);
    }

    @GetMapping("/order-service/")
    JsonResult<?> addOrder(){
        return orderFeignClient.addOrder();
    }
}
