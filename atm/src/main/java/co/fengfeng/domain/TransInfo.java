package co.fengfeng.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

//交易信息表
@Data
@NoArgsConstructor
@TableName("transinfo")
public class TransInfo {
    private String cardId;  //银行卡号
    private Integer transType;  //交易类型存0，取1，2转账
    private BigDecimal transMoney;  //交易金额
    private Date transDate; //交易时间
    private String remark;  //备注
}
