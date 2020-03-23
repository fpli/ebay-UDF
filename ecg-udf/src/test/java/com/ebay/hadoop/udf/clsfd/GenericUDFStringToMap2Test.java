package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.LinkedHashMap;

/*
 * Created by jianyahuang on 2019.02.26
 *
 * --and test in apollo-rno
 * select CUSTOMTARGETING,str_to_map2(CUSTOMTARGETING,  '\;', '=') AS CSTM_TRGTNG
 * FROM clsfd_working.stg_clsfd_dfp_imprsns_it_w
 * limit 1
 * ;
 *
 * cstm_id=8;hb_bidder=appnexus;hb_pb=0.21;hb_pb_appnexus=0.21;hb_pb_criteo=0.15;hb_pb_ix=0.16;hb_pb_openx=0.07;hb_size=300x250;upr=0.10;upr_hi=0.20;upr_lo=0.00;adid=89416618;brand=no;cat=per_la_casa;category=arredamento_e_casalinghi;citta=cogliate;ct=318767104;hn=kijiji.it;kw=motore toyota rav d4d;model=no;production=true;provincia=monza_brianza;pt=vip;ptg=enable-vip-banner;ptg=images-legacy.enabled-false;ptg=ads2019_on;ptg=images-eps-migrate.enabled-false;ptg=toggle-switch-djbi54-false;ptg=hit-4590-b;ptg=images-eps-search.enabled;ptg=toggle-switch-yynvs3-true;ptg=hit_3331_adsense_desktop_on;ptg=images-eps.enabled;ptg=agos_off;ptg=enable-facebook-login;ptg=gdpr-consent-page;ptg=mobile-banner-test-100;ptg=pricetransparency-labels-off;ptg=kit-11028-group-b;ptg=hit_3331_adsense_mobile_on;ptg=image_search_on;ptg=gdpr_on;regione=lombardia;type=no
 * {"adid":"89416618","brand":"no","cat":"per_la_casa","category":"arredamento_e_casalinghi","citta":"cogliate","cstm_id":"8","ct":"318767104","hb_bidder":"appnexus","hb_pb":"0.21","hb_pb_appnexus":"0.21","hb_pb_criteo":"0.15","hb_pb_ix":"0.16","hb_pb_openx":"0.07","hb_size":"300x250","hn":"kijiji.it","kw":"motore toyota rav d4d","model":"no","production":"true","provincia":"monza_brianza","pt":"vip","ptg":"enable-vip-banner;images-legacy.enabled-false;ads2019_on;images-eps-migrate.enabled-false;toggle-switch-djbi54-false;hit-4590-b;images-eps-search.enabled;toggle-switch-yynvs3-true;hit_3331_adsense_desktop_on;images-eps.enabled;agos_off;enable-facebook-login;gdpr-consent-page;mobile-banner-test-100;pricetransparency-labels-off;kit-11028-group-b;hit_3331_adsense_mobile_on;image_search_on;gdpr_on","regione":"lombardia","type":"no","upr":"0.10","upr_hi":"0.20","upr_lo":"0.00"}
 *
 */
public class GenericUDFStringToMap2Test {

    @Test
    public void test(){

        GenericUDFStringToMap2 stringToMap2 = new GenericUDFStringToMap2();
        ObjectInspector[] arguments = new ObjectInspector[3];
        arguments[0] = PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        arguments[1] = PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        arguments[2] = PrimitiveObjectInspectorFactory.writableStringObjectInspector;

        GenericUDF.DeferredObject[] args = new GenericUDF.DeferredObject [3];
        Text strIn = new Text("cstm_id=8;hb_bidder=appnexus;hb_pb=0.21;hb_pb_appnexus=0.21;hb_pb_criteo=0.15;hb_pb_ix=0.16;hb_pb_openx=0.07;hb_size=300x250;upr=0.10;upr_hi=0.20;upr_lo=0.00;adid=89416618;brand=no;cat=per_la_casa;category=arredamento_e_casalinghi;citta=cogliate;ct=318767104;hn=kijiji.it;kw=motore toyota rav d4d;model=no;production=true;provincia=monza_brianza;pt=vip;ptg=enable-vip-banner;ptg=images-legacy.enabled-false;ptg=ads2019_on;ptg=images-eps-migrate.enabled-false;ptg=toggle-switch-djbi54-false;ptg=hit-4590-b;ptg=images-eps-search.enabled;ptg=toggle-switch-yynvs3-true;ptg=hit_3331_adsense_desktop_on;ptg=images-eps.enabled;ptg=agos_off;ptg=enable-facebook-login;ptg=gdpr-consent-page;ptg=mobile-banner-test-100;ptg=pricetransparency-labels-off;ptg=kit-11028-group-b;ptg=hit_3331_adsense_mobile_on;ptg=image_search_on;ptg=gdpr_on;regione=lombardia;type=no");
        Text strDel1 = new Text(";");
        Text strDel2 = new Text("=");
        LinkedHashMap<String, String> ret = new LinkedHashMap();
        args[0] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return strIn;
            }
        };
        args[1] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return strDel1;
            }
        };
        args[2] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return strDel2;
            }
        };

        try {
            ObjectInspector objectInspector =  stringToMap2.initialize(arguments);
            Object result = stringToMap2.evaluate(args);
            ret = (LinkedHashMap<String, String>) result;

            for (String key : ret.keySet()){
                String value = ret.get(key);
                System.out.println("key:"+key+"; value:"+value);
            }

            System.out.println("happy ending ...");


        } catch (UDFArgumentException e) {
            e.printStackTrace();
        } catch (HiveException e) {
            e.printStackTrace();
        }

    }
}
