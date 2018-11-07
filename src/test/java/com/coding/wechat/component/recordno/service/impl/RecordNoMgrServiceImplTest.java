package com.coding.wechat.component.recordno.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RecordNoMgrServiceImplTest {

    @Autowired RecordNoMgrServiceImpl recordNoMgrService;

    @Test
    public void getTenantRecordNo() throws Exception {
        String recordNo = recordNoMgrService.getTenantRecordNo();
        Assert.assertNotNull("编号不存在", recordNo);
    }

    @Test
    public void getBatchTenantRecordNo() throws Exception {
        int recordNoNum = 9;
        List<String> recordNoList = recordNoMgrService.getBatchTenantRecordNo(recordNoNum);
        Assert.assertTrue("编号不存在", recordNoList.size() == recordNoNum);
    }

    @Test
    public void getShopRecordNo() throws Exception {
        String recordNo = recordNoMgrService.getShopRecordNo();
        Assert.assertNotNull("编号不存在", recordNo);
    }

    @Test
    public void getBatchShopRecordNo() throws Exception {
        int recordNoNum = 9;
        List<String> recordNoList = recordNoMgrService.getBatchShopRecordNo(recordNoNum);
        Assert.assertTrue("编号不存在", recordNoList.size() == recordNoNum);
    }

    @Test
    public void getUserinfoRecordNo() throws Exception {
        String recordNo = recordNoMgrService.getUserinfoRecordNo();
        Assert.assertNotNull("编号不存在", recordNo);
    }

    @Test
    public void getBatchUserinfoRecordNo() throws Exception {
        int recordNoNum = 9;
        List<String> recordNoList = recordNoMgrService.getBatchUserinfoRecordNo(recordNoNum);
        Assert.assertTrue("编号不存在", recordNoList.size() == recordNoNum);
    }

    @Test
    public void batchTest() {
        for (int i = 0; i < 99; i++) {
            recordNoMgrService.getTenantRecordNo();
            recordNoMgrService.getBatchTenantRecordNo(9);
            recordNoMgrService.getShopRecordNo();
            recordNoMgrService.getBatchShopRecordNo(9);
            recordNoMgrService.getUserinfoRecordNo();
            recordNoMgrService.getBatchUserinfoRecordNo(9);
        }
    }
}
