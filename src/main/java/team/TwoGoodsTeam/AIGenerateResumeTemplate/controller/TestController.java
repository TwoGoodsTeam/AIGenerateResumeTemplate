package team.TwoGoodsTeam.AIGenerateResumeTemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.TwoGoodsTeam.AIGenerateResumeTemplate.configure.CustomProperties;
import team.TwoGoodsTeam.AIGenerateResumeTemplate.utils.PdfUtil;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    //@Autowired
    //private static CustomProperties customProperties;

    @RequestMapping(value = "/{name}")
    public String test(@PathVariable String name) throws Exception {
        //System.out.println(String.format("prefix=%s,suffix=%s;", customProperties.getPrefix(), customProperties.getSuffix()));
        try {
            PdfUtil pdfUtil = new PdfUtil("templates/", ".html");
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            File file = new File("D:\\test.pdf");
            if (!file.isFile()) {
                file.createNewFile();
            }
            pdfUtil.generate(file, "template", map);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "OK";
    }

}
