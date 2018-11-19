package com.towcent.base.manager.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.qrcode.QuickMarkService;
import com.towcent.base.common.utils.DateUtils;
import com.towcent.base.common.utils.IdGen;
import com.towcent.base.common.utils.SpringFTPUtil;
import com.towcent.base.manager.QrcodeApi;
import com.towcent.base.service.BaseService;

/**
 * 二维码
 */
@Service
public class QrcodeApiImpl extends BaseService implements QrcodeApi {
	
    protected static String TMPDIR = System.getProperty("java.io.tmpdir");
    /** 统一后缀 */
    protected static final String JPG = "jpg";
    protected static final String BASE_PATH = "/"; // 正式上线要切换成"/"

    @Value("${image.url.header}")
    protected String headerUrl;
    @Resource
    protected MessageChannel ftpChannel;
    @Resource
    private QuickMarkService quickMarkService;

    @Override
    public String createQrcode(String content, int size) throws RpcException {
        String tempPath = this.getTempPath();
        String fileName = IdGen.randomString();
        //生成二维码
        quickMarkService.encoderGQRCode2File(content,tempPath+fileName,JPG,size);
        //上传二维码到ftp
        // 图片的相对目录(ftp服务器)
        String relativePath = BASE_PATH + "app/qrcode/";
        relativePath += DateUtils.formatMonth(new Date()) + "/";
        File file = new File(tempPath+fileName);
        boolean flag = false;
        try {
            flag = SpringFTPUtil.ftpUpload(ftpChannel, file, relativePath);
            if (flag) {
                return headerUrl + relativePath + fileName;
            } else {
                return "-1";
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("生成二维码失败",e);
            throw new RpcException("生成二维码失败",e);
        }finally {
            FileUtils.deleteQuietly(new File(tempPath + fileName));
        }
    }

    /**
     * 获取服务本地临时目录
     *
     * @return
     */
    protected String getTempPath() {
        StringBuilder tempPath = new StringBuilder(TMPDIR);
        tempPath.append(File.separator);
        tempPath.append(IdGen.uuid()).append(File.separator);
        this.mkdir(tempPath.toString());
        return tempPath.toString();
    }

    /**
     * 创建目录
     *
     * @param directory
     */
    protected void mkdir(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
