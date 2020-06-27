package cn.xiaosm.plainadmin;

import cn.xiaosm.plainadmin.service.MenuService;
import cn.xiaosm.plainadmin.utils.MemoryUtils;
import cn.xiaosm.plainadmin.utils.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync // 启用异步方法调用
@MapperScan("cn.xiaosm.plainadmin.mapper")
// @PropertySource("classpath:application.properties")
@EnableTransactionManagement
@SpringBootApplication
public class PlainAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlainAdminApplication.class, args);
        load();
    }

    public static void load() {
        // 项目启动完成后加载所有菜单
        MenuService menuService = SpringContextUtils.getBean(MenuService.class);
        // List<Menu> list = menuService.list(new QueryWrapper<Menu>().isNotNull("parent_menu"));
        MemoryUtils.saveObject("MenuList", menuService.list());
    }

}
