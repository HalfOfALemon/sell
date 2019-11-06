package cn.anitano.sell.converter;

import cn.anitano.sell.dataobject.OrderDetail;
import cn.anitano.sell.dto.OrderDTO;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import cn.anitano.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OrderFormToOrderDTOConverter
 * @Author: 杨11352
 * @Date: 2019/11/3 21:28
 */
public class OrderFormToOrderDTOConverter {
    private static Logger logger = LoggerFactory.getLogger(OrderFormToOrderDTOConverter.class);
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson=new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList= gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            logger.error("[gson对象转换出错] 错误 string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
