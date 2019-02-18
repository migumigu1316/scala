package sparkKnowledge;

/**
 * @Description: TODO
 * @ClassName: reverse
 * @Author: xqg
 * @Date: 2019/1/14 16:17
 */
public class reverse {
//    给定一个字符串，一个整数k，要求每隔2k个字符翻转前k ge如：
//    String str = “abcdefg”，k=2
//    Result=“bacdfeg”

    public String reverseStr(String s, int k) {
        int len = s.length();//7
        int index = 0;
        char[] chars = s.toCharArray();

        while (index < len) {
            if (len - index > k) {
                for (int i = index, j = index + k - 1; i < j; i++, j--) {
                    char temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;
                }
                index = index + 2 * k;
            } else {
                for (int i = index, j = len - 1; i < j; i++, j--) {
                    char temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;
                }
                index = len;
            }

        }
        return s;
    }

    public static void main(String[] args) {
        reverse reverse = new reverse();
        String str = "abcdefg";
        reverse.reverseStr(str, 2);

    }
}