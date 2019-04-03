package com.bester.attendance.controller;

import com.bester.attendance.common.CommonResult;
import com.bester.attendance.enums.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author zhangqiang
 * @date 2019-04-03
 */
@RestController
public class OfficeConvert {

    private static final String OPEN_OFFICE_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
    private static final String SOURCE_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\TestFile";
    private static final String TARGET_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\Convert\\";
    private static final String OUTPUT_FILE_FORMAT = ".html";
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeConvert.class);

    /**
     * 预览office文件
     *
     * @param inputFilePath
     * @param response
     */
    @GetMapping("/preview")
    public CommonResult onlinePreview(String inputFilePath, HttpServletResponse response) {
        if (StringUtils.isEmpty(inputFilePath)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        } else {
            File file = office2Html(inputFilePath);
            if (file == null) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buf = new byte[1024];
                int len;
                response.reset();
                response.setContentType("text/html");
                response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(file.getName(), "utf-8"));
                OutputStream outputStream = response.getOutputStream();
                while ((len = bufferedInputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                bufferedInputStream.close();
                outputStream.close();
                return CommonResult.success();
            } catch (Exception e) {
                e.printStackTrace();
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
        }
    }

    /**
     * office转换为html
     *
     * @param inputFilePath 源文件地址
     */
    public static File office2Html(String inputFilePath) {

        OfficeManager officeManager = null;
        try {
            File inputFile = new File(inputFilePath);
            String inputFileName = inputFilePath.substring(inputFilePath.lastIndexOf("\\") + 1, inputFilePath.lastIndexOf("."));
            String outputFilePath = inputFileName + OUTPUT_FILE_FORMAT;
            if (!inputFile.exists()) {
                LOGGER.warn("源文件不存在，预览终止");
                return null;
            }
//            获取openOffice
            officeManager = getOfficeManager();
//            连接openOffice
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            return convertFile(inputFile, outputFilePath, converter);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件转换失败");
        } finally {
            if (officeManager != null) {
                officeManager.stop();
            }
        }
        return null;
    }


    /**
     * 连接openOffice并启动
     *
     * @return
     */
    public static OfficeManager getOfficeManager() {
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        configuration.setOfficeHome(OPEN_OFFICE_HOME);
        OfficeManager officeManager = configuration.buildOfficeManager();
        officeManager.start();
        return officeManager;
    }

    /**
     * 转换文件
     *
     * @param inputFile      源文件
     * @param outputFilePath 输出文件地址
     * @param converter      转换器
     * @return
     */
    public static File convertFile(File inputFile, String outputFilePath, OfficeDocumentConverter converter) {
        File outputFile = new File(TARGET_FILE_PATH + outputFilePath);
        if (outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        converter.convert(inputFile, outputFile);
        return outputFile;
    }


}
