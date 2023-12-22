package co.fengfeng.controller;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.service.LivingPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivingPayController {
    @Autowired
    private LivingPayService livingPayService;

    /**
     * 获取索要缴费的账单
     * @param cardId
     * @param payType
     * @return
     */
    @PostMapping("/getLivingPay")
    public ResponseEntity<AjaxRes> getLivingPays(String cardId,String payType){
        AjaxRes ajaxRes = livingPayService.getLivingPays(cardId,payType);
        return ResponseEntity.ok(ajaxRes);

    }

    @PostMapping("/payLiving")
    public ResponseEntity<AjaxRes> payLivings(String cardId,String payType){
        AjaxRes ajaxRes = livingPayService.payLivings(cardId,payType);
        return ResponseEntity.ok(ajaxRes);
    }

}
