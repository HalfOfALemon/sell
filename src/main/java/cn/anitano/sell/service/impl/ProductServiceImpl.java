package cn.anitano.sell.service.impl;

import cn.anitano.sell.dataobject.ProductInfo;
import cn.anitano.sell.dto.CartDTO;
import cn.anitano.sell.enums.ProductStatusEnum;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import cn.anitano.sell.repository.ProductInfoRepository;
import cn.anitano.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 22:23
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findById(String productId) {
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).get();
            if(productInfo ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int productStock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(productStock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int resultNum = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(resultNum < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(resultNum);
            repository.save(productInfo);
        }
    }
}
