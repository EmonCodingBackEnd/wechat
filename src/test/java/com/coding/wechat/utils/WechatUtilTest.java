package com.coding.wechat.utils;

import com.coding.wechat.config.WechatConfig;
import com.coding.wechat.constants.WechatConsts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class WechatUtilTest {
    @Autowired private MockMvc mvc;

    @Autowired WechatConfig wechatConfig;

    // [lm's ps]: 20180430 21:11 上传图片
    // Turg7a0ggBp3wWy06KL1CMZHDaFbMtVhD1FGtGMVRnDRg_0q3mqw7ycdgGlSJ_dX
    @Test
    public void uploadImage() throws Exception {
        String result =
                mvc.perform(MockMvcRequestBuilders.get("/accessToken"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<Map>() {}.getType());
        // 凭证
        String accessToken = String.valueOf(map.get("access_token"));

        String filePath = "C:\\Users\\LiMing\\Pictures\\LovePicture\\139-1604251II9.jpg";
        String uploadUrl =
                wechatConfig
                        .getUploadUrl()
                        .replace(WechatConsts.BaseInfo.ACCESS_TOKEN, accessToken)
                        .replace(WechatConsts.Media.TYPE, WechatConsts.Media.IMAGE);
        log.info(uploadUrl);
        String mediaId = WechatUtil.upload(filePath, uploadUrl, WechatConsts.Media.IMAGE);
        log.info(mediaId);
    }

    // [lm's ps]: 20180430 21:11 上传缩略图
    // Bd8-7-QJU3G7K8lu5ofNOu2W728A5J05vCmFgf_1Q9_IQD7x4VuzYbjqRiODSZ-l
    @Test
    public void uploadThumb() throws Exception {
        String result =
                mvc.perform(MockMvcRequestBuilders.get("/accessToken"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<Map>() {}.getType());
        // 凭证
        String accessToken = String.valueOf(map.get("access_token"));

        String filePath = "C:\\Users\\LiMing\\Pictures\\LovePicture\\459090977.jpg";
        String uploadUrl =
                wechatConfig
                        .getUploadUrl()
                        .replace(WechatConsts.BaseInfo.ACCESS_TOKEN, accessToken)
                        .replace(WechatConsts.Media.TYPE, WechatConsts.Media.THUMB);
        log.info(uploadUrl);
        String mediaId = WechatUtil.upload(filePath, uploadUrl, WechatConsts.Media.THUMB);
        log.info(mediaId);
    }

    @Test
    public void createMenu() throws Exception {
        String result =
                mvc.perform(MockMvcRequestBuilders.get("/accessToken"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<Map>() {}.getType());
        // 凭证
        String accessToken = String.valueOf(map.get("access_token"));

        String menu = gson.toJson(WechatUtil.initMenu());

        mvc.perform(
                        MockMvcRequestBuilders.get(
                                        "/createMenu?accessToken={accessToken}&menu={menu}",
                                        accessToken,
                                        menu)
                                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

    @Test
    public void queryMenu() throws Exception {
        String result =
                mvc.perform(MockMvcRequestBuilders.get("/accessToken"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<Map>() {}.getType());
        // 凭证
        String accessToken = String.valueOf(map.get("access_token"));

        result =
                mvc.perform(
                                MockMvcRequestBuilders.get(
                                                "/queryMenu?accessToken={accessToken}", accessToken)
                                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        log.info(result);
    }

    @Test
    public void deleteMenu() throws Exception {
        String result =
                mvc.perform(MockMvcRequestBuilders.get("/accessToken"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<Map>() {}.getType());
        // 凭证
        String accessToken = String.valueOf(map.get("access_token"));

        String menu = gson.toJson(WechatUtil.initMenu());

        mvc.perform(
                        MockMvcRequestBuilders.get(
                                        "/deleteMenu?accessToken={accessToken}", accessToken, menu)
                                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }
}
