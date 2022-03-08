package com.seagartech.demo;

import com.seagartech.textAnalysis.entity.CheckDuplicateResult;
import com.seagartech.textAnalysis.entity.MatchItem;
import com.seagartech.textAnalysis.entity.MatchSubItem;
import com.seagartech.textAnalysis.utils.AnalysisUtils;
import com.seagartech.textAnalysis.utils.LicenseUtils;

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
            LicenseUtils.setLicense("=== 设置从网站获取的license ===");

            // 可设置文件 或 文件夹
            String targetFilePath = "/Users/***/Desktop/checktest/测试-需要对比的文档.doc";
            String compareFilePath = "/Users/***/Desktop/checktest/test/";
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
            // 参数1：需要比对的源文件 参数2：用于比对的对比库 参数3：相似度阀值，建议设置不小于30分
            CheckDuplicateResult checkDuplicateResult = AnalysisUtils.checkDuplicate(
                    targetFilePath,
                    compareFilePath,
                    30);

            Long checkTime = System.currentTimeMillis() - startTime;
            System.out.println("《《《《《《《《《《《《《《《《《 比对结果，结果已同步输出到根路径的out.txt中 》》》》》》》》》》》》》》》》》》");
            System.out.println("预加载对比库用时:" + prepareTime + "毫秒，建议项目启动时加载一次，无需每次对比加载");
            System.out.println("比对用时:" + checkTime + "毫秒");
            System.out.println("共比对了:" + checkDuplicateResult.getCompareNum() + " 次");
            System.out.println("相似文件:" + checkDuplicateResult.getMatchItemList().size() + " 个");
            System.out.println("=================================================================");
            for (int i = 0; i < checkDuplicateResult.getMatchItemList().size(); i++) {
                MatchItem matchItem = checkDuplicateResult.getMatchItemList().get(i);
                System.out.println("相似的文件对是：" + matchItem.getTargetFileName() + "    " + matchItem.getSimilarFileName());
                System.out.println("两个文件的总段落数分别是：" + matchItem.getTargetParagraphNum() + " 和 " + matchItem.getSimilarParagraphNum());
                // 受空格、回车、标点符号、图片以及表格等特殊格式影响，字数统计影响较大，只用于参考
                System.out.println("两个文件字数分别是：" + matchItem.getTargetFileTextLength() + " 和 " + matchItem.getSimilarFileTextLength() + "\n");
                System.out.println("相似分数：" + matchItem.getSimilarScore() + "\n");
                System.out.println("找到了:" + matchItem.getMatchSubItemList().size() + " 处相似");
                for (int j = 0; j < matchItem.getMatchSubItemList().size(); j++) {
                    MatchSubItem matchSubItem = matchItem.getMatchSubItemList().get(j);
                    System.out.println("第 " + (j + 1) + " 处有 " + matchSubItem.getSimilarSize() + " 个字相似");
                    System.out.println("对于原文件的重复字数比例是:" + matchSubItem.getIndirectRating() + "%");
                    System.out.println("相似文字是:" + matchSubItem.getSimilarText() + "\n");
                }
                System.out.println("=================================================================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
