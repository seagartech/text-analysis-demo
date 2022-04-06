package com.seagartech.demo;

import com.seagartech.textAnalysis.entity.CheckDuplicateResult;
import com.seagartech.textAnalysis.entity.SimilarItem;
import com.seagartech.textAnalysis.entity.SimilarSubItem;
import com.seagartech.textAnalysis.utils.AnalysisUtils;
import com.seagartech.textAnalysis.utils.LicenseUtils;

import java.util.List;

public class TextAnalysisDemo {

    public static void main(String[] args) {
        ////////////////////////////////////////////
        // 第一次运行需要远程下载jar包，这可能需要几分钟 //
        ///////////////////////////////////////////
        try {
            // 首先获取本机的机器码
            System.out.println("本机的机器码是：" + LicenseUtils.getMachineCode());
            // 获取到机器码后
            // 访问 https://www.seagartech.com/api-license/trial/registerForTrial?machineCode=《机器码》
            // 复制返回值的 data 字段，配置许可文件后方可使用
//            LicenseUtils.setLicense("=== 设置从网站获取的license ===");
            LicenseUtils.setLicense("c2VhZ2FydGVjaC1saWNlbnNlrO0ABXNyACtjb20uc2VhZ2FydGVjaC5saWNlbnNlLmNsaWVudC5MaWNlbnNlRW50aXR508JNjTc2vzMCAAVKAAlhcHBseVRpbWVKAApleHBpcmVUaW1lTAADbWQ1dAASTGphdmEvbGFuZy9TdHJpbmc7WwAKcHVibGljS2V5c3QAAltCWwAJc3BsaXRGbGFncQB+AAJ4cAAAAX/8bOpIAAABgAvf/j50ACA5N2M5YzdhZDZkMjU3NTI0MWM1YmRjODY5MjM2ZTllM3VyAAJbQqzzF/gGCFTgAgAAeHAAAACiMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC7XQrdlDpchjRu9rtccOT9oBqIX8XcdQf8VxJnkXnUpG3E1+SA9q3faWXLJb8ZgTYu5sfOKoh/KBKtW8/D9VnaxpiWxbtd438Jl1LFVdSYPP3L8QbeSj7lHQQDJ4wher2kza4zMkpZ+TQ4KSlUA8R096hD0hLxNUxOHOP0PEBDwIDAQABdXEAfgAFAAAAAgQuegAAAUQELgAAAX/8bOpIAAABgAvf/j4ELqIwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAILtdCt2UOlyGNG72u1xw5P2gGohfxdx1B/xXEmeRedSkbcTX5ID2rd9pZcslvxmBNi7mx84qiH8oEq1bz8P1WdrGmJbFu13jfwmXUsVV1Jg8/cvxBt5KPuUdBAMnjCF6vaTNrjMySln5NDgpKVQDxHT3qEPSEvE1TE4c4/Q8QEPAgMBAAEELoBgEoLA082sb/9yGkVIIhYPyQ0J1KilYe5km1zMgwDDD3WXfgX3xcwD3zImY2thq33IDv3gvmqhVYAur/vDqjdrx+vTIsZuT6laoPNSm7ZFyuJyGiQz3SK4l1LFgYBscJMvG/o4b9Fr1DxHb8Nbmqdwubi/cjp5L8rFUtDQFw8YewQuAAAAAAAAABZ3i4AKG2J/whsXf/KZ56ZyB5KqSkf7qBfAeeXDAHOhBirpGU8dm4ze7kIXWP5btNMNtdYRZ7jTn88dR9A2zs1VJd5ShcgVuiYboM1dUh/ovHFjW2sqVShZIqI6luf6pJsZIdCv6qpd86HZvahB0OmiICfMTMlARaEMjZln1D/w/4VHbwQuAAAAAAAAADN3i4AKUm1GYZB0AJDHFAcZ9XWFMspR9eUTB/XWS6zDbVo5mJgoV9FJOA8CliHmc/pxRnh13U3Y1qQpNlKj/fHPOp+mlSbFGCRCWrZjRPWqeJDEjHrBew0khV80GE0dtjDvG8l9/v3IW8qGlEto0qxSGemnOZ1Ph0jqh3ajO4+tlQ7pBAQuAAAAAAAAAB53i4B0Q4OFkT6cNcQZdIBWyKiIEWyTCtqXDeElV5GE3cGLyhO0DeaNxD4L6RBv2m8G06wC/yX63tsjsWtLx1Mnc7B0IvW5qs5YrgBTksRGe/+iAynSi1qpPUxbQ4gpVAmwqJxoUdpM9Jh8jyyOu7Uc/n0BGDE4TD2SB6K6aDakyfDMxQQuAAAAAAAAAAF3AQA=");

            // 可设置文件 或 文件夹
            String targetFilePath = "/Users/liangyue/Desktop/checktest/测试英文.docx";
            String compareFilePath = "/Users/liangyue/Desktop/checktest/比对英文.docx";
            // 报告生成路径（不需要生成可为空字符串）
            String reportFilePath = "/Users/liangyue/Desktop/checktest/report-s";
            // 预加载需要比对的源文件
            // 非必须，不预加载的话会在调用 check 方法时处理
            AnalysisUtils.initTargetFileList(targetFilePath);

            long startTime_prepare = System.currentTimeMillis();
            // 预加载用于比对的对比库文件
            // 非必须，不预加载的话会在调用 check 方法时处理
            AnalysisUtils.initCompareFileList(compareFilePath);
            // 预加载耗时
            Long prepareTime = System.currentTimeMillis() - startTime_prepare;

            // 查重开始时间
            long startTime = System.currentTimeMillis();
            // 执行查重方法
            // 参数1：需要检测的目标文件 或 文件夹
            // 参数2：用于对比的比对库
            // 参数3：相似度阀值，建议设置不小于30分
            // 参数4：检测报告生成路径（不需要生成可设置空字符串）
            List<CheckDuplicateResult> resultList = AnalysisUtils.checkDuplicate(
                    targetFilePath,
                    compareFilePath,
                    30, reportFilePath);

            Long checkTime = System.currentTimeMillis() - startTime;
            System.out.println("《《《《《《《《《《《《《《《《《 比对结果 》》》》》》》》》》》》》》》》》》");
            System.out.println("预加载对比库用时:" + prepareTime + "毫秒，建议项目启动时加载一次，无需每次对比加载，例如springboot项目的application入口");
            System.out.println("比对用时:" + checkTime + "毫秒");
            System.out.println("共找到了: " + resultList.size() + " 个文件有重复项");
            for (int i = 0; i < resultList.size(); i++) {
                CheckDuplicateResult result = resultList.get(i);
                List<SimilarItem> similarItemList = result.getSimilarItemList();
                System.out.println("与<" + result.getFileName() + ">相似的文件有:" + similarItemList.size() + " 个");
                System.out.println("文字复制比：" + result.getSimilarRatio() + "%");
                System.out.println("单篇最大文字复制比：" + result.getMaxSingleSimilarRatio() + "%");
                System.out.println("总字数：" + result.getFullTextLength());
                System.out.println("重复字数：" + result.getSimilarTextLength());
                System.out.println("单篇最大重复字数：" + result.getMaxSingleSimilarWordsNum());
                System.out.println("单篇最小重复字数：" + result.getMinSingleSimilarWordsNum());
                System.out.println("段落数：" + result.getParagraphNum());
                System.out.println("疑似段落数：" + result.getSimilarParagraphNum());
                System.out.println("段落最大重复字数：" + result.getMaxParaSimilarWordsNum());
                System.out.println("段落最小重复字数：" + result.getMinParaSimilarWordsNum());
                System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓" + result.getFileName() + "↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
                for (int j = 0; j < similarItemList.size(); j++) {
                    SimilarItem similarItem = similarItemList.get(j);
                    System.out.println("与<" + result.getFileName() + ">相似的文件：   " + similarItem.getFileName());
                    System.out.println("相似文本段落数：" + similarItem.getParagraphNum());
                    System.out.println("相似文本字数分别是：" + similarItem.getSimilarTextLength() + "\n");
                    System.out.println("相似分数：" + similarItem.getSimilarScore() + "\n");

                    List<SimilarSubItem> similarSubItemList = similarItem.getSimilarSubItemList();
                    System.out.println("找到了:" + similarSubItemList.size() + " 处相似");
                    for (int k = 0; k < similarSubItemList.size(); k++) {
                        SimilarSubItem similarSubItem = similarSubItemList.get(k);
                        System.out.println("第 " + (k + 1) + " 处有 " + similarSubItem.getSimilarTextLength() + " 个字相似");
                        System.out.println("对于原文件的重复字数比例是:" + similarSubItem.getIndirectRating() + "%");
                        System.out.println("相似文字是:\n" + similarSubItem.getSimilarText() + "\n");
                    }
                    System.out.println("=================================================================");
                }
                System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑" + result.getFileName() + "↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
