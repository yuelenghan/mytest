import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        // 记录报名人数
        int count = 0;

        // 读取excel
        List<Map<String, String>> list = ReadExcel.Excel_03_Reader("d:/test.xls");

        // 循环注册
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            List formParams = new ArrayList();
            String idCard = map.get("idCard");
            int num = Integer.parseInt(String.valueOf(idCard.charAt(idCard.length() - 2)));
            if (num % 2 == 0) {
                formParams.add(new BasicNameValuePair("radioSex", "rdWoman"));
            } else {
                formParams.add(new BasicNameValuePair("radioSex", "rdMan"));
            }
            formParams.add(new BasicNameValuePair("txtName", map.get("name")));

            formParams.add(new BasicNameValuePair("txtNo", idCard));
            formParams.add(new BasicNameValuePair("txtDeptment", map.get("unit")));
            formParams.add(new BasicNameValuePair("txtPhone", map.get("phone")));
            formParams.add(new BasicNameValuePair("rbLCarType", map.get("carType")));
            formParams.add(new BasicNameValuePair("txtFirstLicenseDate", map.get("date")));
            formParams.add(new BasicNameValuePair("selCity", "淮北"));
            formParams.add(new BasicNameValuePair("selCounty", "市区"));
            formParams.add(new BasicNameValuePair("txtAddress", map.get("addr")));

            formParams.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWFQKM3e+UDQLhtt72DwLEhISFCwKns/iwBALejNrhBAKF+7KiBALamZbZDwLEku2wCAKvkfKWBgKvkfaWBgKvkcqWBgKukfKWBgKukfaWBgKtkfKWBgKtkfaWBgLw/pT7CgLf4MyiAgLby9TmDQLWh6WdCQLv7ITZAgLStLntCdbm+KV2C8OwxlcD7AOiVp4RKH+X"));
            formParams.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUJNTIxMTA0MDUzD2QWAgIDD2QWAgINDw8WAh4ISW1hZ2VVcmwFHGltZy/lhajnnIEvaW1nL2Jhb214eF9xci5qcGdkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WBAUFcmRNYW4FB3JkV29tYW4FB3JkV29tYW4FCGlCdG5TYXZlufyIjBSTH7xqiOtgB9z1Y8MPRzI="));
            formParams.add(new BasicNameValuePair("hidCounty", "市区"));
            formParams.add(new BasicNameValuePair("iBtnSave.x", "0"));
            formParams.add(new BasicNameValuePair("iBtnSave.y", "0"));

            HttpClient client = new DefaultHttpClient();
            HttpEntity entity = null;
            entity = new UrlEncodedFormEntity(formParams, HTTP.UTF_8);
            HttpPost request = new HttpPost("http://wjf.ahjtxx.com/wjfqs/SignUp.aspx");
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            System.out.println(response.getStatusLine());
            count ++;
        }

        System.out.println("共报名： " + count + "人");
    }
}
