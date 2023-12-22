package co.fengfeng.service.impl;

import co.fengfeng.domain.TransInfo;
import co.fengfeng.mapper.TransInfoMapper;
import co.fengfeng.service.TransInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class TranInfoServiceImpl extends ServiceImpl<TransInfoMapper, TransInfo> implements TransInfoService {
    @Autowired
    private TransInfoService transInfoService;

    /**
     * 获取用户凭条
     * @param cardId
     * @param session
     * @return
     */
    @Override
    public List<TransInfo> getUserRecepits(String cardId, HttpSession session) {
        LambdaQueryWrapper<TransInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TransInfo> list = lambdaQueryWrapper.eq(TransInfo::getCardId, cardId);
        List<TransInfo> list1 = transInfoService.list(list);

        return list1;
    }
}
