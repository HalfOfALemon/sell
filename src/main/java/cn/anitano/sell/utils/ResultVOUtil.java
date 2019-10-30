package cn.anitano.sell.utils;

import cn.anitano.sell.vo.ResultVO;

/**
 * @Author: 杨11352
 * @Date: 2019/10/27 17:26
 */
public class ResultVOUtil {
    /**
     *成功返回
     * @param object
     * @return
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
