package co.fengfeng.domain;



import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.util.Date;

@Data
@NoArgsConstructor
@TableName("systemlog")
public class Systemlog {
    @Id
    private Integer logId;  //日志编号
    private String cardId;  //卡号
    private String function; //操作方法
    private String params; //传入参数
    private Date optime;    //交易时间
}
