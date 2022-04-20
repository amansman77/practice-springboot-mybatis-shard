package com.ho.practice.shardmybatis.dao;

import com.ho.practice.shardmybatis.dto.DataDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShardTestDAOServiceTest {

    @Autowired
    private ShardTestDaoService shardTestDaoService;

//    private String shardNumber = "01";
//    private String id = "OEB00002";
//    private Integer mainSeq = 162000012;

    private String shardNumber = "02";
    private String id = "ALG800D4";
    private Integer mainSeq = 83;
    private Integer seq = 1011;

//    private String shardNumber = "03";
//    private String id = "OEB00001";
//    private Integer mainSeq = 162000011;
//    private Integer seq = 1013;

//    @Test
    void insertData() {
        DataDto dataDto = DataDto.builder()
                .mainSeq(mainSeq)
                .txt("호 추가 테스트 " + shardNumber)
                .tid("ALG7NMAE")
                .isDelete(0)
                .isReport(0)
                .build();

        int insertCount = shardTestDaoService.insertData(id, dataDto);
        assertEquals(1, insertCount);
    }

//    @Test
    void selectReplyDetailPagingSeq() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mainSeq", mainSeq);
        paramMap.put("seq", seq);
        paramMap.put("start", 0);
        paramMap.put("length", 5);

        List<Integer> integers = shardTestDaoService.selectDataDetailPagingSeq(id, paramMap);
        System.out.println(integers.size());
    }

//    @Test
    void insertData_rollback() {
        DataDto dataDto = DataDto.builder()
                .mainSeq(mainSeq)
                .txt("호 롤백 테스트 " + shardNumber)
                .tid("ALG7NMAE")
                .isDelete(0)
                .isReport(0)
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> {
            shardTestDaoService.insertDataRollback(id, dataDto);
        });
    }

//    @Test
    void insertReply_null() {
        DataDto dataDto = DataDto.builder()
                .mainSeq(mainSeq)
                .txt("호 추가 테스트 " + shardNumber)
                .tid("ALG7NMAE")
                .isDelete(0)
                .isReport(0)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shardTestDaoService.insertData(null, dataDto);
        });
    }

//    @Test
    void insertReply_nullString() {
        DataDto dataDto = DataDto.builder()
                .mainSeq(mainSeq)
                .txt("호 추가 테스트 " + shardNumber)
                .tid("ALG7NMAE")
                .isDelete(0)
                .isReport(0)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shardTestDaoService.insertData("null", dataDto);
        });
    }
}