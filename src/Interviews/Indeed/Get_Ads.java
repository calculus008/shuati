package src.Interviews.Indeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Get_Ads {
    /**
     * 给你⼀一个List ⾥里里⾯面有ads，然后写⼀一个get() function，来随机get⼀一 个list⾥里里⾯面的ad，不不能重复，
     * ⽽且get 完了后 return null。
     *
     * 每次random⼀一个，然后跟List最后⼀一个交换即可， 接着抛弃最后⼀一个
     */

    int total;
    List<Integer> list;
    Random r;

    public Get_Ads(List<Integer> ads) {
        this.list = ads;
        total = this.list.size();
        r = new Random();
    }

    public Integer getAds() {
        if (list == null || list.size() == 0) return null;

        if (total == 0) return null;

        int idx = r.nextInt(total);
        int ret = list.get(idx);

        int temp = list.get(total - 1) ;
        list.set(total - 1, ret);
        list.set(idx, temp);

        total--;

        return ret;
    }

    public static void main(String[] args) {
        List<Integer> ads = new ArrayList<>();
        ads.add(2);
        ads.add(100);
        ads.add(4);
        ads.add(54);
        ads.add(6);
        ads.add(77);

        Get_Ads test = new Get_Ads(ads);

        System.out.println(test.getAds());
        System.out.println(test.getAds());
        System.out.println(test.getAds());
        System.out.println(test.getAds());
        System.out.println(test.getAds());
        System.out.println(test.getAds());
        System.out.println(test.getAds());

    }
}
