package com.website.weily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 15:46
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends Base{


    private static final long serialVersionUID = -7550580488929067866L;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
