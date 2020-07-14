/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.craw;

import hiepnh.checker.CheckWellformed;
import hiepnh.checker.TextUtils;
import hiepnh.constant.constant;
import hiepnh.dao.productDAO;
import hiepnh.jaxb.ProductDetails;
import hiepnh.utils.JaxBGenerater;
import hiepnh.checker.XMLChecker;
import hiepnh.utils.XMLUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author nguye
 */
public class crawlDMGR {

    public crawlDMGR() {
    }

    public void startCrawl() {
        productDAO dao = new productDAO();
//        JaxBGenerater jaxb = new JaxBGenerater();
        prepareParseMainHtml(constant.SRC_HTML + "DMGR.html", constant.DIEN_MAY_GIA_RE_MAIN);
        parseHtmlFormMainDMGR(constant.SRC_HTML + "DMGR.html", constant.SRC_HTML_CRAWL_DMGR);
        XMLUtils xml = new XMLUtils();
        xml.staxIteratorFromDMGR(constant.SRC_HTML_CRAWL_DMGR);
        int countThienPhu = 1;
        for (int i = 1; i < constant.TGDM_PRODUCT_LIST.size(); i++) {
            parseHtmlFromListTGDM(constant.SRC_HTML_DMGR + countThienPhu + ".html", constant.TGDM_PRODUCT_LIST.get(i));
            List<ProductDetails> dto = xml.staxIteratorFromDMGRPageDetails(constant.SRC_HTML_DMGR + countThienPhu + ".html");
            int countDetails = 0;
            for (int h = 0; h < dto.size(); h++) {
                parseHtmlDMGRProductDetails(constant.SRC_HTML_DMGR_DETAIL + countThienPhu + "-" + countDetails + "-details.html", dto.get(h).getLinkDetails());
                ProductDetails result = xml.staxIteratorFromDMGRProductDetails(constant.SRC_HTML_DMGR_DETAIL + countThienPhu + "-" + countDetails + "-details.html", dto.get(h));
                countDetails++;
            }
            countThienPhu++;
        }

        for (int i = 0; i < constant.LIST_PRODUCT.getProductDetails().size(); i++) {
            ProductDetails tp = constant.LIST_PRODUCT.getProductDetails().get(i);
            try {
                List<String> listLink = dao.getLinkDetails();
                if (listLink.size() == 0) {
                    if (!tp.getType().isEmpty() && !tp.getPowerConsumption().isEmpty()) {

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
                        if (!tp.getType().isEmpty() && !tp.getPowerConsumption().isEmpty()) {

                            boolean check = dao.insertProducts(tp.getName(), tp.getImage(), tp.getLinkDetails(), tp.getPrice(), tp.getCategory().toUpperCase(), tp.getMinArea(), tp.getMaxArea(), tp.getPowerConsumption(), tp.getInverter(), tp.getType());
                        }
                    }
                }

                List<String> listCategory = dao.getCategoryName();
                if (listCategory.size() == 0) {
                    boolean insertCategories = dao.insertCategory(tp.getCategory().replaceAll("\n", ""));
                } else {
                    int count = 0;
                    for (String category : listCategory) {
                        if (tp.getCategory().replaceAll("\n", "").equalsIgnoreCase(category)) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        boolean insertCategories = dao.insertCategory(tp.getCategory().replaceAll("\n", ""));
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    private String modifyHTMLDetails(String line) {
        line = line.trim();
        line = line.replaceAll("&nbsp;", "");
        line = line.replaceAll("&", " and ");
        line = line.replaceAll("</section>", "");
        line = line.replaceAll("</div>", "");
        line = line.replaceAll("<div class=\"thongsokythuat\"><h4 class=\"title-thongso\">THÔNG SỐ KỸ THUẬT</h4>", "");
        line = line.replaceAll(">", ">" + "\n");
        line = line.replaceAll("color:;", "color:red;");
        line = line.replaceAll("background-color:;", "background-color:aaa;");
        return line;
    }

    private String modifyHTML(String line) {
        line = line.trim();
        line = line.replaceAll("&nbsp;", "");
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
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
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

    public void parseHtmlFormMainDMGR(String filePath, String fileWrite) {
        CheckWellformed check = new CheckWellformed();
        Writer writer = null;
        int isCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line = "";
            String document = "";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWrite), "UTF-8"));
            boolean isRead = false;
            writer.write("<root>" + "\n");
            while ((line = reader.readLine()) != null) {
                if (line.contains("<section id=\"nav_menu-3\" class=\"widget-odd widget-last widget-first widget-1 widget widget_nav_menu\">") && isCount == 0) {
                    isRead = true;
                    isCount++;
                }
                if (line.contains("</section>") && isRead) {
                    isRead = false;
                }
                if (isRead) {
                    document += line;
//                    line = modifyHTML(line);
//                    if (line.length() > 1) {
//                        writer.write(line + "\n");
//                    }
                }
            }
            document = TextUtils.refineHtml(document);
            writer.write(document);
            writer.write("</root>");
            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseHtmlFromListTGDM(String filePath, String url) {
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

                if (inputLine.contains("<ul class=\"row list-products\">")) {
                    isStart = true;
                }
                if (isStart) {
                    if (inputLine.contains("</main>") || inputLine.contains("<div class=\"term-description\">") || inputLine.contains("</div><nav class=\"woocommerce-pagination\">")) {
                        isEnd = true;
                    }
                }
                if (isStart && !isEnd) {
                    inputLine = modifyHTML(inputLine);
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

    public void parseHtmlDMGRProductDetails(String filePath, String url) {
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

                if (inputLine.contains("<table class=\"woocommerce-product-attributes shop_attributes\">") && isCount == 0) {
                    isStart = true;
                    isCount++;
                }
                if (isStart) {
                    if (inputLine.contains("<div class=\"woocommerce-product-details__short-description\">")) {
                        isEnd = true;
                    } else if (inputLine.contains("<form class=\"cart\" action=\"https://dienmaygiare.net/dieu-hoa-midea-9000-btu-msaf-10crn8/\" method=\"post\" enctype='multipart/form-data'>")) {
                        isEnd = true;
                    }
                }
                if (isStart && !isEnd) {
                    inputLine = modifyHTMLDetails(inputLine);
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
}
