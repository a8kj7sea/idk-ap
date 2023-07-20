/*
 MIT License

Copyright (c) 2023 a8kj7sea

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */







package dev.a8kj7sea.ap.utils;


import dev.a8kj7sea.ap.Afkpool;

public class TimeParserUtils {

    private static int parseTime() {
        Object timeFromConfig = Afkpool.getConfiguration().get().get("Settings.afkpool-time");

        int time = 1;

        if (timeFromConfig instanceof Integer) {
            time = (int) timeFromConfig;

            if (time < 1) {
                time = 1;
                System.err.println("AfkPool time cannot be negative number or non-Integer !");
                return time;
            } else {
                return time;
            }
        }
        return time;
    }

    public static int returnAfkPoolTime() {
        return parseTime() * 60000;
    }
}
