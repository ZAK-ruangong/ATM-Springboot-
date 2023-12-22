package co.fengfeng.service.impl;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.CardInfo;
import co.fengfeng.domain.LivingPay;
import co.fengfeng.mapper.CardInfoMapper;
import co.fengfeng.mapper.LivingPayMapper;
import co.fengfeng.service.CardInfoService;
import co.fengfeng.service.LivingPayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LivingPayServiceImpl extends ServiceImpl<LivingPayMapper, LivingPay> implements LivingPayService {
    @Autowired
    private LivingPayService livingPayService;

    @Autowired
    private CardInfoMapper cardInfoMapper;

    /**
     * 获取所需账单信息
     * @param cardId
     * @param payType
     * @return
     */
    @Override
    public AjaxRes getLivingPays(String cardId, String payType) {
        QueryWrapper<LivingPay> livingPayQueryWrapper = new QueryWrapper<>();
        livingPayQueryWrapper.eq("card_id",cardId);
        livingPayQueryWrapper.eq("pay_type",payType);
        AjaxRes ajaxRes = new AjaxRes();
        LivingPay livingPay = livingPayService.getOne(livingPayQueryWrapper);
        ajaxRes.setRes("success");
        ajaxRes.setMeg("缴费信息如下");
        if (livingPay.getStatus()!="已缴费"){
            ajaxRes.setObject(livingPay);
        }else {
            ajaxRes.setObject("没有需要缴费的账单");
        }

        return ajaxRes;
    }

    /**
     * 支付账单
     * @param cardId
     * @param payType
     * @return
     */
    @Override
    public AjaxRes payLivings(String cardId, String payType) {
        QueryWrapper<LivingPay> livingPayQueryWrapper = new QueryWrapper<>();
        livingPayQueryWrapper.eq("card_id",cardId);
        livingPayQueryWrapper.eq("pay_type",payType);
        AjaxRes ajaxRes = new AjaxRes();
        LivingPay livingPay = livingPayService.getOne(livingPayQueryWrapper);

        QueryWrapper<CardInfo> moneyWrapper = new QueryWrapper<>();
        moneyWrapper.eq("card_id",cardId);
        CardInfo cardInfo = cardInfoMapper.selectOne(moneyWrapper);
        if (cardInfo.getMoney().compareTo(livingPay.getPay()) >= 0){
            BigDecimal money = cardInfo.getMoney().subtract(livingPay.getPay());
            cardInfo.setMoney(money);
            cardInfoMapper.update(cardInfo,moneyWrapper);
            livingPay.setStatus("已缴费");
            livingPayService.update(livingPay,livingPayQueryWrapper);
            ajaxRes.setRes("success");
            ajaxRes.setMeg("缴费成功");
        }else {
            ajaxRes.setRes("error");
            ajaxRes.setMeg("缴费失败");
        }

        return ajaxRes;
    }
}
