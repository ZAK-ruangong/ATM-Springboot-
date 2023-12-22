package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.LivingPay;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LivingPayService extends IService<LivingPay> {
    AjaxRes getLivingPays(String cardId, String payType);

    AjaxRes payLivings(String cardId, String payType);
}
