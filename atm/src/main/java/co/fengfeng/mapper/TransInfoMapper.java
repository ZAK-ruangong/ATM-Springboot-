package co.fengfeng.mapper;

import co.fengfeng.domain.TransInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
@Mapper
public interface TransInfoMapper extends BaseMapper<TransInfo> {

}
