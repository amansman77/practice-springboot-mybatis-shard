package com.ho.practice.shardmybatis.dao;

import com.ho.practice.shardmybatis.common.annotation.ShardKey;
import com.ho.practice.shardmybatis.dto.DataDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(value = "postTransactionManager", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class ShardTestDaoService {

    private final ShardTestDAO shardTestDAO;

    public ShardTestDaoService(ShardTestDAO shardTestDAO) {
        this.shardTestDAO = shardTestDAO;
    }

    @Transactional(value = "postTransactionManager", isolation = Isolation.READ_COMMITTED)
    public int insertData(@ShardKey String id, DataDto dataDto) {
        return shardTestDAO.insertData(dataDto);
    }
    public List<Integer> selectDataDetailPagingSeq(@ShardKey String id, Map<String, Object> mParam) {
        return shardTestDAO.selectDataDetailPagingSeq(mParam);
    }

    @Transactional(value = "postTransactionManager", isolation = Isolation.READ_COMMITTED)
    public int insertDataRollback(String id, DataDto dataDto) {
        shardTestDAO.insertData(dataDto);
        throw new RuntimeException("강제 익셉션");
    }
}
