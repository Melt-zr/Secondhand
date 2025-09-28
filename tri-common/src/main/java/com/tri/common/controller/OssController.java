package com.tri.common.controller;


import com.tri.common.mapper.ImageRecordMapper;
import com.tri.common.constant.MessageConstant;
import com.tri.common.pojo.entity.ImageRecord;
import com.tri.common.result.Result;
import com.tri.common.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/common/image")
@Api(tags = "图片相关接口")
@Slf4j
public class OssController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private ImageRecordMapper imageRecordMapper;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        log.info("图片上传：{}",file.getOriginalFilename());
        try {
            //原始图片名
            String originalFilename = file.getOriginalFilename();
            //获取扩展名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            //构建新的图片名称
            String objectName = UUID.randomUUID().toString() + extension;
            //上传
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            ImageRecord imageRecord = new ImageRecord(null,filePath);

            imageRecordMapper.insert(imageRecord);
            log.info("图片上传成功:{}",filePath);

            return Result.success(imageRecord);
        } catch (IOException e) {
            log.error("图片上传失败:{}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }


    //批量上传
    @ApiOperation(value = "批量上传图片")
    @PostMapping("/uploadBatch")
    @Transactional
    public Result uploadBatch(@RequestParam("files") MultipartFile[] files) {
        log.info("批量图片上传：{}", files.length);
        try {

            List<ImageRecord> imageRecords = new ArrayList<>();

            for (MultipartFile file : files) {
                //原始图片名
                String originalFilename = file.getOriginalFilename();
                //校验图片名
                if (originalFilename == null || originalFilename.isEmpty()) {
                    continue; // 跳过空图片
                }

                //获取扩展名后缀
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                //构建新的图片名称
                String objectName = UUID.randomUUID().toString() + extension;
                //上传
                String filePath = aliOssUtil.upload(file.getBytes(), objectName);

                ImageRecord imageRecord = new ImageRecord();
                imageRecord.setImageUrl(filePath);

                imageRecordMapper.insert(imageRecord);
                imageRecords.add(imageRecord);
                log.info("图片上传成功:{}", filePath);
            }

            return Result.success(imageRecords);
        } catch (IOException e) {
            log.error("批量图片上传失败:", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        } catch (Exception e) {
            log.error("批量图片上传异常:", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }

    //根据id获取imageUrl
    @ApiOperation(value = "根据id获取imageUrl")
    @GetMapping("/{id}")
    public Result<ImageRecord> getImageUrlById(@PathVariable("id") Long id) {
        ImageRecord imageRecord = imageRecordMapper.selectById(id);
        return Result.success(imageRecord);
    }

    @ApiOperation(value = "根据ids批量获取imageUrl")
    @PostMapping("/getBatch")
    public Result<List<ImageRecord>> getImageUrlByIds(@RequestBody List<Long> ids) {
        List<ImageRecord> imageRecords = imageRecordMapper.selectByids(ids);
        return Result.success(imageRecords);
    }

}
