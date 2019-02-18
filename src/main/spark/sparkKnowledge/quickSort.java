package sparkKnowledge;

import java.util.Arrays;

/**
 * @Description: TODO 快速排序
 * @ClassName: quickSort
 * @Author: xqg
 * @Date: 2019/1/15 20:14
 */
public class quickSort {
    public static void qSort(int[] arr, int left, int right) {
        int i, j, t, temp;
        if (left > right) {
            return;
        }

        //temp中存的就是基准数
        temp = arr[left];
        i = left;
        j = right;

        while (i != j) {//第一轮：直到i=j的时候，即：arr[i] = arr[j]，准备进入下一次循环
            //右边的值 大于等于 基准值
            while (arr[j] >= temp && i < j) {
                j--;
            }

            //左边的值 小于等于 基准值
            while (arr[i] <= temp && i < j) {
                i++;
            }

            //交换值
            if (i < j) {
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        //最终将基准数归位
        //这个时候是i=j，arr[i] = arr[j],将这个中间的值和基准值交换，进入下一轮循序
        arr[left] = arr[i];
        arr[j] = temp;

        //这时候进入第二轮，以基准值为中心，分成两半，同样的循环左边
        qSort(arr, left, i - 1);

        //这时候进入第二轮，以基准值为中心，分成两半，同样的循环右边
        qSort(arr, i + 1, right);

        //以此类推，直到所有的都排序结束
    }

    public static void QuickSort(int[] arr) {
        //传入数组，传入左边的leftIndex = 0，rightIndex = arr.length - 1
        qSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = {45, 12, 46, 5, 13, 47, 2};
        quickSort quickSort = new quickSort();

        quickSort.QuickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
