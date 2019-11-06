package cn.anitano.sell.service.impl;

import cn.anitano.sell.converter.OrderMasterToOrderDTOConverter;
import cn.anitano.sell.dataobject.OrderDetail;
import cn.anitano.sell.dataobject.OrderMaster;
import cn.anitano.sell.dataobject.ProductInfo;
import cn.anitano.sell.dto.CartDTO;
import cn.anitano.sell.dto.OrderDTO;
import cn.anitano.sell.enums.OrderStatusEnum;
import cn.anitano.sell.enums.PayStatusEnum;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import cn.anitano.sell.repository.OrderDetailRepository;
import cn.anitano.sell.repository.OrderMasterRepository;
import cn.anitano.sell.service.OrderService;
import cn.anitano.sell.service.ProductService;
import cn.anitano.sell.utils.BigDecimalUtil;
import cn.anitano.sell.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: OrderServiceImpl
 * @Author: 杨11352
 * @Date: 2019/10/31 19:59
 */
@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount=new BigDecimal("0");
        String orderId=KeyUtil.getUniqueKey();
        //List<CartDTO> cartDTOList=new ArrayList<>();
        //1、查询商品详细信息
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo=productService.findById(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2、查询订单总价
            BigDecimal productAmount =BigDecimalUtil.mul(productInfo.getProductPrice().doubleValue(),orderDetail.getProductQuantity());
            orderAmount= BigDecimalUtil.add(productAmount.doubleValue(),orderAmount.doubleValue());
            //填充订单详情
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
            //库存
            //CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOList.add(cartDTO);
        }
        //3、插入数据库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //4、减少库存
        //使用lambda
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page getList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        PageImpl<OrderDTO> orderDTOPage=new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1、查看订单的状态，该订单已经完结，则不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            logger.error("[取消订单] 订单状态错误，orderDTO {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2、修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            logger.error("[取消订单] 修改订单状态错误，orderMaster {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3、返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            logger.error("[取消订单] 订单中商品详情为空，orderDTO {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //4、如果已支付则退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public OrderDTO finish(OrderDTO orderDTO) {
        //1、判断订单状态，是不是新下订单， TODO 是不是已经支付
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            logger.error("[完结订单] 订单状态错误， orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2、修改订单状态
        OrderMaster orderMaster =new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            logger.error("[完结订单] 修改订单状态错误， orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public OrderDTO pay(OrderDTO orderDTO) {
        //1、判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            logger.error("[支付订单] 订单状态错误,不是新下订单， orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2、判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            logger.error("[支付订单] 不是等待支付状态 orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //3、修改订单支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            logger.error("[支付订单] 更新失败 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
