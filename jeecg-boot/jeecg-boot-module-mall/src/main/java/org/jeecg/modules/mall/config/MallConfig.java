package org.jeecg.modules.mall.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class MallConfig {
    @Value("${mall.image.prefix}")
    private String picPrefix;

}
