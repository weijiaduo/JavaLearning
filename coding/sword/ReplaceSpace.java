package com.wjd.sword;

public class ReplaceSpace {

    public static void main(String[] args) {
        String s = "We Are Happy.";
        System.out.println(replaceSpace(new StringBuffer(s)));
    }

    public static String replaceSpace(StringBuffer str) {
        if (str == null){
            return null;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' '){
                count++;
            }
        }

        StringBuffer nStr;
        if (count > 0){
            nStr = new StringBuffer();
            for (int i =str.length()-1; i >=0; i--) {
                if (str.charAt(i) == ' '){
                    nStr.append("02%");
                }else {
                    nStr.append(str.charAt(i));
                }
            }
            nStr.reverse();
        }else {
            nStr = str;
        }

        return nStr.toString();
    }

}
