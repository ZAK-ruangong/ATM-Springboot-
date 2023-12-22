package co.fengfeng.controller;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.TransInfo;
import co.fengfeng.mapper.TransInfoMapper;
import co.fengfeng.service.TransInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(value = "凭条打印", tags = {"凭条打印接口"})
public class ReceiptsController {
    @Autowired
    private TransInfoService TransInfoService;

    @ApiOperation(value = "凭条打印", notes = "打印该用户的凭条", httpMethod = "Get")
    @PostMapping("/getUserRecepits")
    public ResponseEntity<List<TransInfo>> getUserRecepits(String cardId, BigDecimal money, HttpSession session){
        List<TransInfo> userRecepits = TransInfoService.getUserRecepits(cardId, session);
        return ResponseEntity.ok(userRecepits);
    }
}
