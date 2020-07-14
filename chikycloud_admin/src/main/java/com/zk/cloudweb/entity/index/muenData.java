package com.zk.cloudweb.entity.index;

import com.zk.cloudweb.entity.BaseEntity;
import com.zk.cloudweb.entity.UserHeadmenu;
import lombok.Data;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/5/21 17:25
 */
@Data
public class muenData {
   private homeInfo homeInfo;
   private logoInfo logoInfo;
   private List<UserHeadmenu> menuInfo;
}
