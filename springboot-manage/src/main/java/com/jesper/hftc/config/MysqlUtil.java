package com.jesper.hftc.config;

import java.io.*;
import java.net.URL;

/**
 * @Author 廖凡
 * @Date 2020/2/28 23:02
 */
public class MysqlUtil {

    public static void main(String[] args) {

        try {
            if (exportDatabase("127.0.0.1", "3306", "root", "123456",
                    "C:\\chengzheming\\backupDatabase", "test-20190516.sql", "test")
            ) {
                System.out.println("数据库成功备份");
            } else {
                System.out.println("数据库备份失败");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (importDatabase("127.0.0.1", "3306", "root", "root", "C:\\chengzheming\\backupDatabase", "test-20190516.sql", "ims")) {
            System.out.println("数据库导入成功");
        } else {
            System.out.println("数据库导入失败");
        }
    }

    /**
     * Mysql数据库导出
     *
     * @param hostIP       数据库地址
     * @param hostPort     端口
     * @param userName     用户名
     * @param password     密码
     * @param savePath     导出路径
     * @param fileName     导出文件名
     * @param databaseName 要导出数据库名
     * @return
     * @throws InterruptedException
     */
    public static boolean exportDatabase(String hostIP, String hostPort, String userName, String password, String savePath,
                                         String fileName, String databaseName) throws InterruptedException {
        //目录不存在则新建
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        //在地址后补充系统默认分隔符
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;

        BufferedReader bufferedReader = null;

        try {
            Runtime runtime = Runtime.getRuntime();

            //因为我的地址有空格，为解决找不到路径所以用了这个方式
            URL url = new URL("file:C:\\chengzheming\\develop software\\mysql-5.6.41-winx64\\bin");
            String path = url.getPath();

            //"mysqldump -h127.0.0.1 -uroot -P3306 -proot test"
            String cmd = "\\mysqldump -h" + hostIP + " -u" + userName + " -P" + hostPort + " -p" + password + " " + databaseName;

            cmd = path + cmd;
            Process process = runtime.exec(cmd);

            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);

            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
            printWriter.flush();
            if (process.waitFor() == 0) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 导入Mysql数据库
     *
     * @param hostIP         数据库地址
     * @param hostPort       端口
     * @param userName       用户名
     * @param password       密码
     * @param importFilePath 数据库文件路径
     * @param sqlFileName    要导入的文件名
     * @param databaseName   要导入的数据库名
     * @return
     * @throws InterruptedException
     */
    public static boolean importDatabase(String hostIP, String hostPort, String userName, String password, String importFilePath,
                                         String sqlFileName, String databaseName) {
        File imporFile = new File(importFilePath);
        if (!imporFile.exists()) {
            imporFile.mkdirs();
        }

        if (!importFilePath.endsWith(File.separator)) {
            importFilePath = importFilePath + File.separator;
        }

        //mysql -h127.0.0.1 -uroot -P3306 -p test<C:\chengzheming\backupDatabase\
        try {
            Process process = Runtime.getRuntime().exec("cmd /C" + "mysql -h" + hostIP + " -P" + hostPort + " -u" + userName + " -p" + password + " " + databaseName + "<" + importFilePath + sqlFileName);
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
