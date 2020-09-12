package com.zk.cloudweb.util.dxfType;



import com.zk.cloudweb.entity.base.BaseDxfEntity;
import com.zk.cloudweb.util.dxfUtil.Util.DxfLineBuilder;
import com.zk.cloudweb.util.dxfUtil.Vector3;
import lombok.*;

/**
 * 圆
 *
 * @author wangp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DxfCircle extends BaseDxfEntity {

    /**
     * 圆心位置
     */
    private Vector3 center;
    /**
     * 半径
     */
    private double radius;


    @Override
    public String getEntityName() {
        return "CIRCLE";
    }

    @Override
    public String getEntityClassName() {
        return "AcDbCircle";
    }

    @Override
    protected String getChildDxfStr() {
        return DxfLineBuilder.build()
                .append(10, center.getX())
                .append(20, center.getY())
                .append(30, center.getZ())
                .append(40, radius)
                .toString();
    }

}

