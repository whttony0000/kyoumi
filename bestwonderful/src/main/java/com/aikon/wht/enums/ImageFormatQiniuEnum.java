package com.aikon.wht.enums;

/**
 * @author haitao.wang
 *
 *  七牛图片格式后缀.
 */
public enum ImageFormatQiniuEnum {
    // ORIGIN
    ORIGIN(1,"?imageView2/0/q/75|imageslim"),
    // MARKED
    ORIGIN_WATERMARK(2,"?imageView2/0/q/75|watermark/2/text/YmVzdHdvbmRlcmZ1bA==/font/5a6L5L2T/fontsize/400/fill/I0M5QjczOQ==/dissolve/79/gravity/SouthEast/dx/10/dy/10|imageslim"),
    ;

    Integer code;

    String suffix;

    ImageFormatQiniuEnum(Integer code, String suffix) {
        this.code = code;
        this.suffix = suffix;
    }

    public Integer getCode() {
        return code;
    }

    public String getSuffix() {
        return suffix;
    }

    public static ImageFormatQiniuEnum getImageFormatEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ImageFormatQiniuEnum imageFormatQiniuEnum : values()) {
            if (imageFormatQiniuEnum.getCode().equals(code)) {
                return imageFormatQiniuEnum;
            }
        }
        return null;
    }

    public static String getSuffixByCode(Integer code) {
        ImageFormatQiniuEnum imageFormatQiniuEnum =getImageFormatEnumByCode(code);
        if (imageFormatQiniuEnum == null) {
            return null;
        }
        return imageFormatQiniuEnum.getSuffix();
    }
}
