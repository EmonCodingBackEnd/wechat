package com.coding.wechat.component.recordno.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void getShopRecordNo() throws Exception {
        String recordNo = recordNoMgrService.getShopRecordNo();
        Assert.assertNotNull("编号不存在", recordNo);
    }

    @Test
    public void getUserinfoRecordNo() throws Exception {
        String recordNo = recordNoMgrService.getUserinfoRecordNo();
        Assert.assertNotNull("编号不存在", recordNo);
    }
}
