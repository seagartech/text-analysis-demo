package com.seagartech.demo;

import com.seagartech.textAnalysis.config.Config;
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
            LicenseUtils.setLicense("c2VhZ2FydGVjaC1saWNlbnNlrO0ABXNyACtjb20uc2VhZ2FydGVjaC5saWNlbnNlLmNsaWVudC5MaWNlbnNlRW50aXR508JNjTc2vzMCAAVKAAlhcHBseVRpbWVKAApleHBpcmVUaW1lTAADbWQ1dAASTGphdmEvbGFuZy9TdHJpbmc7WwAKcHVibGljS2V5c3QAAltCWwAJc3BsaXRGbGFncQB+AAJ4cAAAAYNEkpJUAAABg1QFpjV0ACA0MDQ2ZjkzNThhODY5MjU4Nzg1YWQ1ZTE3Njg1YjAxMnVyAAJbQqzzF/gGCFTgAgAAeHAAAACiMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJFDYZVbNZG0YiSmeZ3lEXY1vJuB51S9VMe75+fZfV7+Q4tBu8T8U8Z2IPulJWFlP1RbOg9W7R7dcGJxHyilkp6ybTyPdqczefPpoYbeYVqbls4J6txlYktanLFTPLwIQ/rGwSVVDLLOfd1/+kfLqLjNiNHjMdlZ5HYkEX7fwX4QIDAQABdXEAfgAFAAAAAmwwegAAAURsMAAAAYNEkpJUAAABg1QFpjVsMKIwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAIkUNhlVs1kbRiJKZ5neURdjW8m4HnVL1Ux7vn59l9Xv5Di0G7xPxTxnYg+6UlYWU/VFs6D1btHt1wYnEfKKWSnrJtPI92pzN58+mhht5hWpuWzgnq3GViS1qcsVM8vAhD+sbBJVUMss593X/6R8uouM2I0eMx2VnkdiQRft/BfhAgMBAAFsMIBRajIWoZQbSYiFUdYqgT4vaFzuQaSAZJq2FWqpN0Bc9iAP49ZtEicXnYYrzqp6HreNB/pHkmi0GKuc5K8nVpCpjdRi45FkjVpjR3WEnou7E6nkHM2SoY/PI8202iWhiHLLneNUf6+YMnY85b45efBJKFluYRdJWDodW3o8EghJ42wwAAAAAAAAABV3i4BBox6nfrA5Uck7yxtbJ23uCD34D0k8cWTUyd8IfRKiF2Z71yt2hA0ifFljMaej7AzAbiZEQpf6KvPWlENNIcV1/fvpxsUiH/hIq52JO2bdV44E5Qoh3UCQ0014LZnYbeBA45NOPmY9vGiKN7mw3H8q90WaWdN1MHjfyQapkHUCj2wwAAAAAAAAABF3i4BrUyQ1EbqGpNN+CmimC+HyANhDQKZv9ALT5U43nI4AwBNQ0x/PRjt6Ow5X8kn4Ax7nK63BqUc6pht+4rhHxWXTXWCMNTL5VcErzKibyorPFtC1s9Y30jEvMwrAJK6Px3MgC9YcN5zSt/L8q4u2v6VpPDCyCnzsx69vbVlLI9g7pGwwAAAAAAAAABV3i4BRiUJrZf8PdcVI3U9SZ3CSARFcHBNzTVAq3YDzR+SfgSAWQhc/sCtVDJDaZdaw3AwOvGUsxccPH4mYaH30TbppUr0AnXitbAf0VJfQOe+aCXyxn50hfljS5ZWPaCDAIm/WagDB1moPQhfxIK97BsRy2ytWVo1631kramcDLDpsNWwwAAAAAAAAAAV3AQA=");

            // 线程数，可根据机器配置高低设定
            Config.threadNum = 4;
            // 可设置文件 或 文件夹
            String targetFilePath = "/Users/liangyue/Desktop/checktest/测试-需要对比的文档的副本.doc";
            String compareFilePath = "/Users/liangyue/Desktop/checktest/test4/";
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
