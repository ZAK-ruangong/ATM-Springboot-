package co.fengfeng.service.impl;


import co.fengfeng.common.enums.ExceptionEnum;
import co.fengfeng.common.exception.AtmException;
import co.fengfeng.config.RedisConfig;
import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.CardInfo;
import co.fengfeng.domain.LogInfo;
import co.fengfeng.domain.UserInfo;
import co.fengfeng.mapper.CardInfoMapper;
import co.fengfeng.mapper.LogInfoMapper;
import co.fengfeng.mapper.UserInfoMapper;
import co.fengfeng.service.LoginInfoService;
import co.fengfeng.service.LoginService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
@Transactional
public class LoginServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LoginService {
    @Autowired
    private CardInfoMapper cardInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private LogInfoMapper logInfoMapper;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private LoginService loginService;


    /**
     * 验证银行卡信息是否正确
     *
     * @param cardId
     * @param password
     * @return
     */

    @Override
    public AjaxRes getCardInfo(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        //判断该账户是否被锁定
        String errorNumber;
        String errorTimes;
        if (redisConfig.getCacheObject("errorNumber")!=null){
            errorNumber=redisConfig.getCacheObject("errorNumber");
            System.out.println(errorNumber);
        }else {
            errorNumber = "0";
            System.out.println(errorNumber);
        }
        if (Integer.parseInt(errorNumber)>= 5){
            if (redisConfig.getCacheObject("errorTimes")!=null){
                errorTimes = redisConfig.getCacheObject("errorTimes");
                redisConfig.getAndSet("errorTimes",errorTimes,1);
            }else {
                errorTimes="1";
            }
            /**************保存登录记录*****************/
            LogInfo logInfo = new LogInfo();
            logInfo.setCardId(cardId);
            logInfo.setTradeType("1");
            logInfo.setDate(new Date());
            ajaxRes.setRes("error");
            ajaxRes.setMeg("该账户已经被锁定，禁止登录,请"+Integer.parseInt(errorTimes)*5+"分钟后在输入密码登录");
            return ajaxRes;
        }
        try {
            //CardInfo card = cardInfoMapper.selectOne(cardInfo);
            QueryWrapper<CardInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("card_id",cardInfo.getCardId());
            CardInfo card = cardInfoMapper.selectOne(queryWrapper);
            System.out.println("***************************"+card);
            if (StringUtils.isEmpty(card.toString())||!(password.equals(card.getPassword()))) {
                    //密码错误
                    Integer result = Integer.parseInt(errorNumber) + 1;
                    errorNumber=result.toString();
                    if (redisConfig.getCacheObject("errorNumber")!=null){
                        redisConfig.getAndSet("errorNumber",errorNumber,2);
                    }else {
                        redisConfig.setCacheObject("errorNumber",errorNumber);
                    }
                    ajaxRes.setRes("error");
                    ajaxRes.setMeg("密码错误");
                    return ajaxRes;

            } else {

                QueryWrapper<UserInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("customer_id",card.getCustomerId());
                UserInfo userInfo = userInfoMapper.selectOne(queryWrapper1);
                String customerName = userInfo.getCustomerName();
//                insertLog(card.getCardId());

                /**************保存登录记录*****************/
                LogInfo logInfo = new LogInfo();
                logInfo.setCardId(cardId);
                logInfo.setTradeType("0");
                logInfo.setDate(new Date());

                // 执行插入操作
                loginService.save(logInfo);

                ajaxRes.setRes("success");
                ajaxRes.setMeg("登陆成功");
                ajaxRes.setObject(customerName);
                System.out.println("***************************"+"检验成功");
            }
        } catch (Exception e) {
            log.error("登陆失败");
            System.out.println(e);
            throw new AtmException(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 根据卡号读取账户信息，判断账号是否存在
     * 若存在，则meg返回卡号即可
     * @param cardId
     * @return
     */
    @Override
    public AjaxRes getUserInfo(String cardId, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            QueryWrapper<CardInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("card_id",cardId);
            System.out.println(queryWrapper.toString()+"****************");
            //CardInfo card = cardInfoMapper.selectOne(queryWrapper);
            CardInfo cardInfo = cardInfoMapper.selectOne(queryWrapper);
            System.out.println(cardInfo.toString()+"**********************");
            if (StringUtils.isEmpty(cardInfo.getCardId().toString())) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("卡号不存在");
                return ajaxRes;
            } else {
                ajaxRes.setRes("success");
                ajaxRes.setMeg(cardId);
                QueryWrapper<UserInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("customer_id",cardInfo.getCustomerId());
                UserInfo userInfo = userInfoMapper.selectOne(queryWrapper1);
                System.out.println(userInfo+"*********************************");
                String customerName = userInfo.getCustomerName();
                System.out.println(customerName+"*********************************");
                ajaxRes.setObject(customerName);
                //将卡号信息保存到本次会话
                //session.setAttribute("cardId",cardId);
                return ajaxRes;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AtmException(ExceptionEnum.READ_CARD_ERROE);
        }

    }

    public boolean insertLog(String cardId){
        /**************保存登录记录*****************/
                LogInfo logInfo = new LogInfo();
                logInfo.setCardId(cardId);
                logInfo.setTradeType("0");
                logInfo.setDate(new Date());
        boolean save = loginService.save(logInfo);
        return save;
    }




}
