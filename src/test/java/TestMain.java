import java.util.Date;

import mchhui.customnpcsfix.util.NBTJsonUtil;
import net.minecraft.nbt.NBTTagCompound;

public class TestMain {

    public static void main(String[] args) {
        int[] arr=new int[] {0};
        a(arr);
        System.out.println(arr[0]);
        System.out.println(merge("test","1"));
    }
    
    static void a(int[] arr) {
        arr[0]=1;
    }
    
    public static String merge(String... strs){
        String result="";
        for(String str:strs){
            result+=str;
        }
        return result;
    }
}
