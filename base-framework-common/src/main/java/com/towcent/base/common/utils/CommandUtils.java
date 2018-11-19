/*
 * 文 件 名:  CommandUtils.java
 * 版   权: Copyright www.hidym.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-12-14
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 命令行出来
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2013-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommandUtils
{
	protected static Logger logger = LoggerFactory.getLogger(CommandUtils.class);

    @SuppressWarnings("finally")
    public static boolean exeCommand(String command)
    {
        boolean flag = false;
        Process process = null;
        try
        {
            if (logger.isDebugEnabled()) {
                logger.debug("image process command is " + command);
            }
            String osName = System.getProperty("os.name");
            if (osName.toUpperCase().startsWith("Windows".toUpperCase())) {
                process = Runtime.getRuntime().exec("cmd /c " + command);
            } else {
                process = Runtime.getRuntime().exec(command);
            }
            process.waitFor();

            flag = 0 == process.exitValue();

            if (logger.isDebugEnabled()) {
                logger.debug("image process " + flag);
            }
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        finally
        {
            return flag;
        }
    }

    public static boolean moveFile(String file, String dir)
    {
        String command = "";
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().startsWith("Windows".toUpperCase())) {
            command = "move " + file + " " + dir;//XCOPY  D:\testin D:\temp\  /S /Y
        } else {
            command = "mv " + file + " " + dir;
        }
        return exeCommand(command);
    }

    public static List<Long> getImagePixel(String filePath)
    {
        List<Long> list = new ArrayList<Long>();
        File file = new File(filePath);
        BufferedImage bi = null;
        try
        {
            bi = ImageIO.read(file);
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        list.add(new Long(bi.getWidth()));
        list.add(new Long(bi.getHeight()));
        return list;
    }

    /**
     * 随机获取UUID字符串(无中划线)
     * 
     * @return UUID字符串
     */
    public static String getUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
