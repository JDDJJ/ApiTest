/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.jdd.apitest.token.http;

/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class GlobalToken {
    private static String sToken = "";
//    private static String sToken = "2vjP5ZMJqew5DeSt9IWLxVnn";
    //赋值初值 以防空指针异常
    //静态资源时app开启后被分配静态资源区后创建的 app关闭后时被释放的
    //如果需要永久存储 还是需要保存到本地

    /**
     * @param token 更新时需要同时更新磁盘中的值 application 初始化时需要读取磁盘中的值
     */
    public static synchronized void updateToken(String token) {
        sToken = token;
    }

    public static String getToken() {
        return sToken;
    }
}
