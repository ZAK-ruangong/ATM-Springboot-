package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.TransInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TransInfoService extends IService<TransInfo> {

    List<TransInfo> getUserRecepits(String cardId, HttpSession session);
}
