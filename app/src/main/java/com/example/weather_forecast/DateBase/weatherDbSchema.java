package com.example.weather_forecast.DateBase;

public class weatherDbSchema {
    public static final class weatherdetailTable{
        public static final String wdNAME = "WeatherDetail";
        public static final class Cols{
            //白天文字描述
            public static final String TEXTDAY = "textDay";
            //白天图标代码
            public static final String ICONDAY = "iconDay";
            //夜间图标
            public static final String ICONNIGHT = "iconNight";
            //夜间文字描述
            public static final String TEXTNIGHT = "textNight";
            //最高气温
            public static final String TEMPMAX = "tempMax";
            //最低气温
            public static final String TEMPMIN = "tempMin";
            //预报日期
            public static final String FXDATE = "fxDate";
            //湿度
            public static final String HUMIDITY = "humidity";
            //气压
            public static final String PRESSURE = "pressure";
            //风向
            public static final String WINDDIRDAY = "windDirday";
            //风速
            public static final String WINDSPEEDDAY = "windSpeedday";
            //地区
            public static final String LOCATION = "location";
            //唯一标识符，由location+position组成
            public static final String KEYID = "keyid";
        }
    }
}
