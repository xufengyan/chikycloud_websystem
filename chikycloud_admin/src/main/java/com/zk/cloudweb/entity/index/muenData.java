package com.zk.cloudweb.entity.index;

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
   private com.zk.cloudweb.entity.index.homeInfo homeInfo;
   private com.zk.cloudweb.entity.index.logoInfo logoInfo;
   private List<UserHeadmenu> menuInfo;
}
