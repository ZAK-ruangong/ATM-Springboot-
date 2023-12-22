package co.fengfeng.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 定义返回结果类
 */
@Setter
@Getter
@NoArgsConstructor
public class AjaxRes {
    private String res;
    private String meg;
    private Object object;

    // 构造函数，用于表示成功的结果
    public  AjaxRes success(Object data) {
        AjaxRes result = new AjaxRes();
        result.setRes("success");
        result.setObject(data);
        return result;
    }

    // 构造函数，用于表示失败的结果
    public AjaxRes failure(String message) {
        AjaxRes result = new AjaxRes();
        result.setRes("failure");
        result.setMeg(message);
        return result;
    }


}
