package com.tri.common.api.mapper;


import com.tri.common.pojo.entity.ImageRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageRecordMapper {

    @Insert("insert into tri_image_record(image_url) values(#{imageUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Long insert(ImageRecord imageRecord);

    @Select("select * from tri_image_record where id = #{id}")
    ImageRecord selectById(Long id);

    List<ImageRecord> selectByids(List<Long> ids);
}
