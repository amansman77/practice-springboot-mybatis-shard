package com.ho.practice.shardmybatis.dao;

import com.ho.practice.shardmybatis.dto.DataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShardTestDAO {

    int insertData(DataDto dataDto);

    List<Integer> selectDataDetailPagingSeq(Map<String, Object> mParam);
}
