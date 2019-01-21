package com.zzh.mapper;

import com.zzh.pojo.SearchRecords;
import com.zzh.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
      List<String> getHotwords();
}