package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.LogInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

public interface LoginService extends IService<LogInfo> {

    AjaxRes getCardInfo(String cardId, String password);

    AjaxRes getUserInfo(String cardId, HttpSession session);
}
