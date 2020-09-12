package com.zk.cloudweb.util.dxfType;



import com.zk.cloudweb.entity.base.BaseDxfEntity;
import com.zk.cloudweb.util.dxfUtil.Util.DxfLineBuilder;
import com.zk.cloudweb.util.dxfUtil.Vector3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 线段
 * @author wangp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DxfLine extends BaseDxfEntity {
    /**
     * 起始点位置
     */
    private Vector3 startPoint;
    /**
     * 结束点位置
     */
    private Vector3 endPoint;

    @Override
    protected String getChildDxfStr() {
        return DxfLineBuilder.build()
                .append(10, startPoint.getX())
                .append(20, startPoint.getY())
                .append(30, startPoint.getZ())
                .append(11, endPoint.getX())
                .append(21, endPoint.getY())
                .append(31, endPoint.getZ())
                .toString();
    }

    @Override
    public String getEntityName() {
        return "LINE";
    }

    @Override
    public String getEntityClassName() {
        return "AcDbLine";
    }
}
