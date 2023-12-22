package co.fengfeng.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@TableName("livingpay")
public class LivingPay {
    /**
     * 用户id
     */
    private String cardId;

    /**
     * 缴费账户id
     */
    private String livingId;

    /**
     * 所需缴纳的费用
     */
    private BigDecimal pay;

    /**
     * 账单生成日期
     */
    private Date date;

    /**
     * 付费种类,0代表电话费，1代表水电费
     */
    private String payType;


    /**
     * 缴费状态，分未已缴费，未缴费
     */
    private String status;
}
