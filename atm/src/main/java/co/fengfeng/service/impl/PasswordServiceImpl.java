package co.fengfeng.service.impl;

import co.fengfeng.common.enums.ExceptionEnum;
import co.fengfeng.common.exception.AtmException;
import co.fengfeng.config.RedisConfig;
import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.CardInfo;
import co.fengfeng.mapper.LoginMapper;
import co.fengfeng.service.LoginService;
import co.fengfeng.service.PasswordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisConfig redisConfig;

    String updateNumber;
    String updateTimes;

    /**
     * 根据账户密码判断用户信息是否正确(输入就旧密码阶段,先判断密码是否准确)
     *
     * @param cardId
     * @param password
     * @return
     */
    @Override
    public AjaxRes checkPassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        //判断账户当天是否能继续更新

        if (redisConfig.getCacheObject("updateNumber")!=null){
            updateNumber=redisConfig.getCacheObject("updateNumber");
            System.out.println(updateNumber);
        }else {
            updateNumber="0";
            System.out.println(updateNumber);
        }
        if (Integer.parseInt(updateNumber)>=1){
            if (redisConfig.getCacheObject("updateTimes")!=null){
                updateTimes = redisConfig.getCacheObject("updateTimes");
                redisConfig.getAndSet("updateTimes",updateTimes,10);
            }else {
                updateTimes="1";
            }
            ajaxRes.setRes("error");
            ajaxRes.setMeg("该账户今天已经更新过密码，请改天再来");
            return ajaxRes;
        }
        try {
            QueryWrapper<CardInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("card_id",cardInfo.getCardId());
            CardInfo card = loginMapper.selectOne(queryWrapper);
            //CardInfo card = loginMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(card)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("密码为空");
            } else if (!password.equals(card.getPassword())) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("密码输入错误");
            } else {
                ajaxRes.setRes("success");
                ajaxRes.setMeg("成功");
            }
        } catch (Exception e) {
            log.error("密码修改失败");
            throw new AtmException(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 根据卡号修改密码,输入的是新密码
     *
     * @param cardId
     * @param password
     * @return
     */
    @Override
    public AjaxRes updatePassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            QueryWrapper<CardInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("card_id",cardInfo.getCardId());
            CardInfo card = new CardInfo();
            //CardInfo card = loginMapper.selectOne(queryWrapper);
            System.out.println("****************"+card);
            //CardInfo card = loginMapper.selectOne(cardInfo);
            card.setPassword(password);
            int i = loginMapper.update(card,queryWrapper);
            if (i > 0) {
                Integer result = Integer.parseInt(updateNumber) + 1;
                updateNumber=result.toString();
                if (redisConfig.getCacheObject("updateNumber")!=null){
                    redisConfig.getAndSet("updateNumber",updateNumber,10);
                }else {
                    redisConfig.setCacheObject("updateNumber",updateNumber);
                }
                ajaxRes.setRes("success");
                ajaxRes.setMeg("密码修改成功");


            } else {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("修改密码失败");
            }
        } catch (Exception e) {
            log.error("密码修改失败");
            throw new AtmException(ExceptionEnum.UPDATE_PASSWORD_ERROR);
        }
        return ajaxRes;
    }

}
