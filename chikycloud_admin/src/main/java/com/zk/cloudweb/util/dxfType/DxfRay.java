package com.zk.cloudweb.util.dxfType;


import com.zk.cloudweb.entity.base.BaseDxfEntity;
import com.zk.cloudweb.util.dxfUtil.Util.DxfLineBuilder;
import com.zk.cloudweb.util.dxfUtil.Vector3;
import lombok.Getter;
import lombok.Setter;

/**
 * 射线
 *
 * @author wangp
 */
@Getter
@Setter
public class DxfRay extends BaseDxfEntity {

    /**
     * 起始点位置
     */
    private Vector3 start;
    /**
     * 射线方向
     */
    private Vector3 direction;

    @Override
    protected String getChildDxfStr() {
        return DxfLineBuilder.build()
                .append(10, start.getX())
                .append(20, start.getY())
                .append(30, start.getZ())
                .append(11, direction.getX())
                .append(21, direction.getY())
                .append(31, direction.getZ())
                .toString();
    }

    @Override
    public String getEntityName() {
        return "RAY";
    }

    @Override
    public String getEntityClassName() {
        return "AcDbRay";
    }
}
