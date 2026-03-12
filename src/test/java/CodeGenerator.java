import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        // 1. 配置数据库连接
        // 注意替换你的 root 密码
        String url = "jdbc:mysql://localhost:3306/personal_system?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";

        // 2. 项目的根路径，用于指定生成代码存放的绝对位置
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("YueLin") // 设置作者
                            .enableSwagger() // 开启 Swagger 模式（可选，方便后续配合Knife4j生成接口文档）
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.personal.system") // 设置父包名，例如 com.yourname.system
                            .moduleName("") // 设置父包模块名，这里留空即可
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")); // 设置 mapper.xml 的存放路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(
                                    // 基础模块
                                    "sys_user",
                                    // 工作模块
                                    "work_task", "work_shortcut", "work_pomodoro_log",
                                    // 学习模块
                                    "study_note", "study_error_book", "study_progress", "study_countdown",
                                    // 生活模块
                                    "life_ledger", "life_habit", "life_habit_log", "life_recipe", "life_memo"
                            ) // 设置需要生成的表名
                            .addTablePrefix("sys_", "work_", "study_", "life_") // 设置过滤表前缀（生成实体类时会去掉前缀，比如 life_memo 会变成 Memo）

                            // 实体类策略配置
                            .entityBuilder()
                            .enableLombok() // 开启 Lombok
                            .enableTableFieldAnnotation() // 开启生成实体时生成字段注解

                            // 控制器策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启 @RestController 风格

                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation(); // 开启 @Mapper 注解
                })
                .execute();
    }
}