package com.jw22.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public static boolean exist(Integer parentType) {
        for (CommentTypeEnum type : CommentTypeEnum.values()) {
            if (type.getType().equals(parentType)) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
