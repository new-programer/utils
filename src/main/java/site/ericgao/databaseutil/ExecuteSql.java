package site.ericgao.databaseutil;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Project utils
 * @Description //TODO
 * @Author EricGao
 * @Date 2021/2/8 13:12
 * @Version 1.0
 **/
public class ExecuteSql {
    /**
     * 执行 sql 脚本
     * @param connection 数据库连接
     * @param sqlScript sql脚本名称（带路径）
     */
    public static void runSqlScript(Connection connection, String sqlScript){
        try {
            ScriptRunner runner = new ScriptRunner(connection);
            //设置字符集,不然中文乱码插入错误
            Resources.setCharset(Charset.forName(String.valueOf(StandardCharsets.UTF_8)));
            //设置是否输出日志
            runner.setLogWriter(null);
            //读取sql脚本文件
            FileReader fileReader = new FileReader(new File(sqlScript));
            runner.runScript(fileReader);
            //设置 全部执行 还是按行执行
            runner.setSendFullScript(true);
            runner.closeConnection();
            connection.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
