package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /**
     * 该方法是通过 categoryTypeList 查找；该方法的命名是有语法规则的
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
