package com.zzh.mapper;

import com.zzh.pojo.vo.Reports;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersReportMapperCustom {
    List<Reports> selectAllVideoReport(@Param("id") String id ,@Param("title") String title ,
                                       @Param("dealUsername") String dealUsername ,@Param("dealVideoId") String dealVideoId ,
                                       @Param("submitUsername") String submitUsername ,@Param("status") Integer status );
}