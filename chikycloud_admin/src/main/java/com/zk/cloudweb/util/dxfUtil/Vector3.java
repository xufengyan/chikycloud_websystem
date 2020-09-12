package com.zk.cloudweb.util.dxfUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Vector for 3 dimension
 *
 * @author wangp
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Data
public class Vector3 {

    private double x;
    private double y;
    private double z;

    public static Vector3 ZERO = new Vector3(0, 0, 0);

    public Vector3(int i, int i1, int i2) {
    }
}
