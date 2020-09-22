package com.ebay.hadoop.udf.shpmt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class GetUrlTest {
    @Test
    public void test() {
        assertEquals("www.bapmic.com", new GetUrl().evaluate(" Email: Website: <a href=www.bapmic.com<a>www.bapmic.com View my other items <a href=http:emailtrk.shipstation.comlsclick"));
        assertEquals("www.ebay.com", new GetUrl().evaluate(" hbuybyububu www.ebay.com hbjbbjb yahoo gmail@com"));
        assertEquals("https://automater.com/p/order-viewer/complaint/16900670/b291a0f5ea8ecefe9e37871a07ad4763b249deb7ec7316b645898353ec794c3d,https://hidrive.ionos.com/share/dto5kyshq1", new GetUrl().evaluate("Purchased product Your purchased product Hi scotsman11111 Â  Thank you for purchase. Â  Your download link is below: Â  https://hidrive.ionos.com/share/dto5kyshq1 Â  The password to access the share is: mercwis Â  Â  If you have any issues please let me know and I'll be happy to help out. Â  Regards Â  If the code or file doesn't work, you can report complaint by clicking on the link below:" +
                " https://automater.com/p/order-viewer/complaint/16900670/b291a0f5ea8ecefe9e37871a07ad4763b249deb7ec7316b645898353ec794c3d"));
        assertNull(new GetUrl().evaluate(null));
    }
}

