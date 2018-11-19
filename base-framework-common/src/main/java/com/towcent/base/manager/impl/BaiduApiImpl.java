package com.towcent.base.manager.impl;

import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.aip.ocr.AipOcr;
import com.towcent.base.common.enums.BaseInfoEnum;
import com.towcent.base.common.enums.SexEnum;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.BankInfoDo;
import com.towcent.base.common.model.IdcardDto;
import com.towcent.base.common.utils.DateUtils;
import com.towcent.base.manager.BaiduApi;
import com.towcent.base.service.BaseService;

/**
 * 百度接口
 */
@Service
public class BaiduApiImpl extends BaseService implements BaiduApi {
    @Value("${baidu.appId}")
    private String appId;
    @Value("${baidu.aipKey}")
    private String aipKey;
    @Value("${baidu.aipToken}")
    private String aipToken;
    @Override
    public IdcardDto getIdcard(String imgPath,Boolean isFront,HashMap<String,String> options) throws RpcException {
        IdcardDto idcardDto = new IdcardDto();
        try {
            AipOcr ocr = new AipOcr(appId, aipKey, aipToken);
            // HashMap<String, String> map = new HashMap<String,String>();
            JSONObject json = ocr.idcard(imgPath, isFront, options);
            if(json != null){
                Object o = json.get("words_result");
                JSONObject a = new JSONObject(o.toString());
                Object sexObj = a.get(BaseInfoEnum.SEX.getName());
                JSONObject sexJson = new JSONObject(sexObj.toString());
                if(SexEnum.MAN.getName().equals(sexJson.getString("words"))){
                    idcardDto.setSex(1);
                }else if(SexEnum.WOMEN.getName().equals(sexJson.getString("words"))){
                    idcardDto.setSex(0);
                }else{
                    idcardDto.setSex(2);
                }
                //姓名
                Object nameObj = a.get(BaseInfoEnum.NAME.getName());
                JSONObject nameJson = new JSONObject(nameObj.toString());
                idcardDto.setName(nameJson.getString("words"));
                //住址
                Object addressObj = a.get(BaseInfoEnum.ADDRESS.getName());
                JSONObject addressJson = new JSONObject(addressObj.toString());
                idcardDto.setAddress(addressJson.getString("words"));
                //公民身份号码
                Object cardObj = a.get(BaseInfoEnum.IDCARD.getName());
                JSONObject cardJson = new JSONObject(cardObj.toString());
                idcardDto.setIdentityCard(cardJson.getString("words"));
                //出生
                Object birthObj = a.get(BaseInfoEnum.BIRTHDAY.getName());
                JSONObject birthJson = new JSONObject(birthObj.toString());
                idcardDto.setBirthday(DateUtils.parseDate(birthJson.getString("words"),"yyyyMMdd"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return idcardDto;
    }

    @Override
    public BankInfoDo getBank(String imagePath) throws RpcException {
        BankInfoDo bankInfoDo = new BankInfoDo();
        AipOcr ocr = new AipOcr(appId, aipKey, aipToken);
        // HashMap<String, String> map = new HashMap<String,String>();
        JSONObject json = ocr.bankcard(imagePath);
        if(json != null){
            Object o = json.get("result");
            JSONObject cardJson = new JSONObject(o.toString());
            bankInfoDo.setBankCardNumber(cardJson.getString("bank_card_number"));
            bankInfoDo.setBankName(cardJson.getString("bank_name"));
            bankInfoDo.setBankCardType(cardJson.getInt("bank_card_type"));
        }
        return bankInfoDo;
    }
}
