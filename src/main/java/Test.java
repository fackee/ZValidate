import org.zvalidate.scan.ScanValidateAnnotation;

/**
 * Test
 *
 * @author ZhuJianXin
 * @date 2017/9/6
 */
public class Test {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ScanValidateAnnotation.initValidateAnnotationList();
        ScanValidateAnnotation.initValidateAnnotationMap();
    }
}
