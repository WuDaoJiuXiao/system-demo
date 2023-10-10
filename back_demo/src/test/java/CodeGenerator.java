import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Author 悟道九霄
 * @Date 2023/10/8 14:09
 * @Description
 */
public class CodeGenerator {

    public static void main(String[] args) {

        final String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8&serverTimezone=Asia/Shanghai";
        final String username = "root";
        final String password = "0531";
        final String driverName = "com.mysql.cj.jdbc.Driver";
        final String userDir = System.getProperty("user.dir");

        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig()
                .setAuthor("悟道九霄")
                .setFileOverride(true)
                .setBaseResultMap(true)
                .setOutputDir(userDir + "/src/main/java")
                .setOpen(false)
                .setSwagger2(true)
                .setServiceName("%sService");

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDriverName(driverName)
                .setUsername(username)
                .setPassword(password)
                .setUrl(url);

        PackageConfig packageConfig = new PackageConfig()
                .setParent("com.jiuxiao")
                .setController("controller")
                .setEntity("entry")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper");

        StrategyConfig strategyConfig = new StrategyConfig()
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityLombokModel(true);

        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .execute();
    }
}
