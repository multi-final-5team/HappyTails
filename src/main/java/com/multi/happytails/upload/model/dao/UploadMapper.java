package com.multi.happytails.upload.model.dao;

import com.multi.happytails.upload.model.dto.UploadDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {
    public int uploadInsert(UploadDto uploadDto);
    public List<String> uploadSelectList(UploadDto uploadDto);
}
