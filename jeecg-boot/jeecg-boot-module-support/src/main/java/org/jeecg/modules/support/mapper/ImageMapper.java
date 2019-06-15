package org.jeecg.modules.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.support.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: 图片管理
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface ImageMapper extends BaseMapper<Image> {


    boolean deleteByBusinessId(String businessId);

    List<Image> selectByBusinessId(String businessId);

    List<Image> selectByBusinessIdAndType(@Param("businessId")String businessId, @Param("type")Integer type);
}
