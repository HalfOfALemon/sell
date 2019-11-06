package cn.anitano.sell.controller;

import cn.anitano.sell.converter.OrderFormToOrderDTOConverter;
import cn.anitano.sell.dataobject.OrderDetail;
import cn.anitano.sell.dto.OrderDTO;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import cn.anitano.sell.form.OrderForm;
import cn.anitano.sell.service.BuyerService;
import cn.anitano.sell.service.OrderService;
import cn.anitano.sell.utils.ResultVOUtil;
import cn.anitano.sell.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BuyerOrderController
 * @Author: 杨11352
 * @Date: 2019/11/3 15:37
 */
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderService orderService;
    @Autowired
    BuyerService buyerService;
    /** 创建订单*/
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //1、验证参数
        if(bindingResult.hasErrors()){
            logger.error("[创建订单] 参数不正确 orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //2、将表单的信息转到OrderDTO,
        OrderDTO orderDTO= OrderFormToOrderDTOConverter.convert(orderForm);
        //3、验证购物车信息
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            logger.error("[创建订单] 购物车为空 orderForm={}",orderForm);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        //调用service
        OrderDTO createResult = orderService.create(orderDTO);
        if(createResult == null){
            logger.error("[创建订单] 创建订单错误 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_CREATE_FAIL);
        }

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    /** 订单列表*/
    @GetMapping("list")
    public ResultVO<OrderDTO> list(@RequestParam("openid") String openid,
                                      @RequestParam(value = "page",defaultValue = "0")Integer page,
                                      @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            logger.error("[订单列表] 参数错误 openid={}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest =PageRequest.of(page,size);
        Page<OrderDTO> result = orderService.getList(openid, pageRequest);
        return ResultVOUtil.success(result.getContent());
    }
    /** 查看单个订单*/
    @GetMapping("detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
    /** 取消订单*/
    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
