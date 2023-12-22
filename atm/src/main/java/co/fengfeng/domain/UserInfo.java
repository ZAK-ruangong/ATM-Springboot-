package co.fengfeng.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@TableName( "userinfo")
public class UserInfo {
    @Id
    private Integer customerId; //用户编号
    private String customerName;    //开户人姓名
    private String pid; //身份证号码
    private String telephone; //电话
    private String address; //地址
}
