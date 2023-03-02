package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Application;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
}
