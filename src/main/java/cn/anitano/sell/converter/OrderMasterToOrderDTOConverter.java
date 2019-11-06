package cn.anitano.sell.converter;

import cn.anitano.sell.dataobject.OrderMaster;
import cn.anitano.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: OrderMasterToOrderDTOConverter
 * @Author: 杨11352
 * @Date: 2019/11/1 21:54
 */
public class OrderMasterToOrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(
                e -> convert(e)
        ).collect(Collectors.toList());
    }

}
