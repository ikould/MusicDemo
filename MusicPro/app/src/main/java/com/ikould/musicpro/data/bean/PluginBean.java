package com.ikould.musicpro.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 插件更新实体类
 * <p>
 * Created by liudong on 2016/8/17.
 */
public class PluginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * code : 0
     * version : 0
     * data : [{"file":"plugin1","ico":"plugin1_ico01.png","name":"杀你所想","apk":"BackService1.1.apk"}]
     */

    private int code;
    private int version;
    /**
     * file : plugin1
     * ico : plugin1_ico01.png
     * name : 杀你所想
     * apk : BackService1.1.apk
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private static final long serialVersionUID = 2L;

        private String file;
        private String ico;
        private String name;
        private String apk;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApk() {
            return apk;
        }

        public void setApk(String apk) {
            this.apk = apk;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "file='" + file + '\'' +
                    ", ico='" + ico + '\'' +
                    ", name='" + name + '\'' +
                    ", apk='" + apk + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PluginBean{" +
                "code=" + code +
                ", version=" + version +
                ", data=" + data.toString() +
                '}';
    }
}
