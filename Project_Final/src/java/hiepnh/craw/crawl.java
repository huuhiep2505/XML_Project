package hiepnh.craw;

import hiepnh.checker.TextUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import hiepnh.constant.constant;
import hiepnh.dao.productDAO;
import hiepnh.utils.JaxBGenerater;
import hiepnh.utils.XMLUtils;
import hiepnh.jaxb.ProductDetails;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HiepNguyen
 */
public class crawl {

    public void startCrawl() {

        productDAO dao = new productDAO();
//        JaxBGenerater.JaxBGenerater();   
        prepareParseMainHtml(constant.SRC_HTML + "thienphu.html", constant.THIEN_PHU_MAIN);
        parseHtmlFormMainThienPhu(constant.SRC_HTML + "thienphu.html", constant.SRC_HTML + "ThienPhu_Product.html");
        XMLUtils xml = new XMLUtils();
        xml.staxIteratorFromThienPhu(constant.SRC_HTML + "ThienPhu_Product.html");

        int countThienPhu = 1;
        for (int i = 0; i < constant.THIEN_PHU_PRODUCT_LIST.size(); i++) {
            parseHtmlFromListThienPhu(constant.SRC_HTML_THIEN_PHU + countThienPhu + ".html", constant.THIEN_PHU_PRODUCT_LIST.get(i));
            xml.staxIteratorFromThienPhuPageDetails(constant.SRC_HTML_THIEN_PHU + countThienPhu + ".html", constant.THIEN_PHU_PRODUCT_CATEGORY.get(i));
            xml.staxIteratorFromThienPhuListPage(constant.SRC_HTML_THIEN_PHU + countThienPhu + ".html");

            int countHH = 1;
            for (String a : constant.LIST_PAGE) {
                parseHtmlFromListThienPhu(constant.SRC_HTML_THIEN_PHU + countThienPhu + "-" + countHH + ".html", a);
                xml.staxIteratorFromThienPhuPageDetails(constant.SRC_HTML_THIEN_PHU + countThienPhu + "-" + countHH + ".html", constant.THIEN_PHU_PRODUCT_CATEGORY.get(i));
            }
            countThienPhu++;
        }

        for (int i = 0; i < constant.LIST_PRODUCT.getProductDetails().size(); i++) {
            ProductDetails tp = constant.LIST_PRODUCT.getProductDetails().get(i);
            try {
                List<String> listLink = dao.getLinkDetails();
                if (listLink.size() == 0) {
                        if(!tp.getType().isEmpty() && !tp.getPowerConsumption().isEmpty()){
                            
                    boolean check = dao.insertProducts(tp.getName(), tp.getImage(), tp.getLinkDetails(), tp.getPrice(), tp.getCategory().toUpperCase(), tp.getMinArea(), tp.getMaxArea(), tp.getPowerConsumption(), tp.getInverter(), tp.getType());
                        }
                } else {
                    int isCount = 0;
                    for (String link : listLink) {
                        if (tp.getLinkDetails().equalsIgnoreCase(link)) {
                            isCount++;
                        }
                    }
                    if (isCount == 0) {
if(!tp.getType().isEmpty() && !tp.getPowerConsumption().isEmpty()){
                            
                    boolean check = dao.insertProducts(tp.getName(), tp.getImage(), tp.getLinkDetails(), tp.getPrice(), tp.getCategory().toUpperCase(), tp.getMinArea(), tp.getMaxArea(), tp.getPowerConsumption(), tp.getInverter(), tp.getType());
                        }
                    }
                }

                List<String> listCategory = dao.getCategoryName();
                if (listCategory.size() == 0) {
                    boolean insertCategories = dao.insertCategory(tp.getCategory().toUpperCase());
                } else {
                    int count = 0;
                    for (String category : listCategory) {
                        if (tp.getCategory().equalsIgnoreCase(category)) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        boolean insertCategories = dao.insertCategory(tp.getCategory().toUpperCase());
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }

//        prepareParseMainHtml(constant.SRC_HTML + "thegioidienmay.html", constant.THE_GIOI_DIEN_MAY_MAIN);
//        parseHtmlFormMainTheGioiDienMay(constant.SRC_HTML + "thegioidienmay.html", constant.SRC_HTML + "TheGioiDienMay_Product.html");
//        XMLUtils.staxIteratorFromTheGioiDienMay(constant.SRC_HTML + "TheGioiDienMay_Product.html");
//        int countTGDM = 1;
//        int a = constant.THE_GIOI_DIEN_MAY_PRODUCT_LIST.size();
//        for (int i = 0; i < constant.THE_GIOI_DIEN_MAY_PRODUCT_LIST.size(); i++) {
//            parseHtmlFromListTheGioiDienMay(constant.SRC_HTML_TGDM + countTGDM + ".html", constant.THE_GIOI_DIEN_MAY_PRODUCT_LIST.get(i));
//            XMLUtils.staxIteratorFromTGDMPageDetails(constant.SRC_HTML_TGDM + countTGDM + ".html");
//            int countDetailsTGDM = 0;
//            for (int h=0 ; h < constant.LIST_LINK_PRODUCT_DETAIL_TGDM.size() ; h++) {
//                        parseHtmlTGDMProductDetails(constant.SRC_HTML_TGDM_DETAIL+ countTGDM +"-" + countDetailsTGDM +"-details.html", constant.LIST_LINK_PRODUCT_DETAIL_TGDM.get(h));
//                        countDetailsTGDM++;
//            }
//            countTGDM++;
//        }
    }

    private String modifyHTMLThienPhuDetails(String line) {
        line = line.trim();
        line = line.replaceAll("&nbsp;", "");
        line = line.replaceAll("&", " and ");
        line = line.replaceAll("<p>", "");
        line = line.replaceAll(">", ">" + "\n");
        line = line.replaceAll("color:;", "color:red;");
        line = line.replaceAll("background-color:;", "background-color:aaa;");
        return line;
    }

    private String modifyHTML(String line) {
        line = line.trim();
        line = line.replaceAll("&nbsp;", "");
        line = line.replaceAll("&quote;", "");
        line = line.replaceAll("&", " and ");
        line = line.replaceAll(">", ">" + "\n");
        line = line.replaceAll("color:;", "color:red;");
        line = line.replaceAll("background-color:;", "background-color:aaa;");
        return line;
    }

    public void prepareParseMainHtml(String filePath, String url) {
        Writer writer = null;
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>" + "\n");
            while ((inputLine = in.readLine()) != null) {
                inputLine = modifyHTML(inputLine);
                writer.write(inputLine + "\n");
            }
            writer.write("</root>");
            in.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseHtmlFormMainThienPhu(String filePath, String fileWrite) {
        Writer writer = null;
        int isCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWrite), "UTF-8"));
            boolean isRead = false;
            writer.write("<root>" + "\n");
            while ((line = reader.readLine()) != null) {
                if (line.contains("section-item-content nav-sections-item-content") && isCount == 0) {
                    isRead = true;
                    isCount++;
                }
                if (line.contains("<div class=\"section-item-title nav-sections-item-title\" data-role=\"collapsible\">") && isRead) {
                    isRead = false;
                }
                if (isRead) {
                    line = modifyHTML(line);
                    if (line.length() > 1) {
                        writer.write(line + "\n");
                    }
                }
            }

            writer.write("</root>");
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseHtmlFromListThienPhu(String filePath, String url) {
        Writer writer = null;
        int isCount = 0;
        boolean isStart = false;
        boolean isEnd = false;
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String document = "";
            String inputLine = "";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>" + "\n");
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.contains("products wrapper grid products-grid")) {
                    isStart = true;
                }
                if (isStart) {
                    if (inputLine.contains("<div class=\"field limiter\">")) {
                        isEnd = true;
                    }
                }
                if (isStart && !isEnd) {
//                    inputLine = modifyHTML(inputLine);
                    document += inputLine;
//                    writer.write(inputLine + "\n");
                }
            }
            document = TextUtils.refineHtml(document);
            writer.write(document);
            writer.write("</root>");
            in.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseHtmlThienPhuProductDetails(String filePath, String url) {
        Writer writer = null;
        int isCount = 0;
        boolean isStart = false;
        boolean isEnd = false;
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>" + "\n");
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.contains("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">")) {
                    isStart = true;
                }
                if (isStart) {
                    if (inputLine.contains("<b>")) {
                        isEnd = true;
                    }
                }
                if (isStart && !isEnd) {
                    inputLine = modifyHTMLThienPhuDetails(inputLine);
                    writer.write(inputLine + "\n");
                }
            }

            writer.write("</root>");
            in.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   public void parseHtmlFromListTheGioiDienMay(String filePath, String url) {
        Writer writer = null;
        int isCount = 0;
        boolean isStart = false;
        boolean isEnd = false;
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>" + "\n");
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.contains("<ul class=\"products\">")) {
                    isStart = true;
                }
                if (inputLine.contains("<nav class=\"woocommerce-pagination\">") && isStart) {
                    isEnd = true;
                }
                if (isStart && !isEnd) {
                    inputLine = modifyHTML(inputLine);
                    writer.write(inputLine + "\n");
                }
            }
            writer.write("</ul> \n" + "</root>");
            in.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseHtmlTGDMProductDetails(String filePath, String url) {
        Writer writer = null;
        int isCount = 0;
        boolean isStart = false;
        boolean isEnd = false;
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>" + "\n");
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.contains("<div class=\"thong_so_ky_thuat_chi_tiet\">")) {
                    isStart = true;
                }
                if (isStart) {
                    if (inputLine.contains("<li class=\"view_full_kt_popup\" onclick=\"openpopup()\">Xem đầy đủ bảng thông tin kỹ thuật</li>")) {
                        isEnd = true;
                    }
                }
                if (isStart && !isEnd) {
                    inputLine = modifyHTMLThienPhuDetails(inputLine);
                    writer.write(inputLine + "\n");
                }
            }

            writer.write("</root>");
            in.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseHtmlFormMainTheGioiDienMay(String filePath, String fileWrite) {
        Writer writer = null;
        int isCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWrite), "UTF-8"));
            boolean isRead = false;
            writer.write("<root>" + "\n");
            while ((line = reader.readLine()) != null) {
                if (line.contains("menu-menu-chinh-container") && isCount == 0) {
                    isRead = true;
                    isCount++;
                }
                if (line.contains("</nav>") && isRead) {
                    isRead = false;
                }
                if (isRead) {
                    line = modifyHTML(line);
                    if (line.length() > 1) {
                        writer.write(line + "\n");
                    }
                }
            }

            writer.write("</root>");
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
