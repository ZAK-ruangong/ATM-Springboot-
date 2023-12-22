package co.fengfeng.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("loginfo")
public class LogInfo {
    private String cardId;  //卡号

    private String tradeType; //交易类型


    private Date date; //日期

}
