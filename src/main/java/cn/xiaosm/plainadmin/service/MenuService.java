/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuService
 * Author:   Young
 * Date:     2020/6/16 10:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.plainadmin.service;

import cn.xiaosm.plainadmin.entity.Menu;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public interface MenuService extends BaseService<Menu> {

    List<Menu> getByRoleId(String roleId);

    List<Menu> buildTree(List<Menu> menuList);
}