/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import hiepnh.constant.constant;
import java.util.ArrayList;
import java.util.List;
import hiepnh.jaxb.ProductDetails;
import static java.lang.Double.parseDouble;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author HiepNguyen
 */
public class XMLUtils implements Serializable {

    public void staxIteratorFromThienPhu(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        int count = 0;
        ProductDetails tp = new ProductDetails();
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("nav") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("navigation")) {
                                isProductTag = true;
                                tp = new ProductDetails();
                            }
                        }

                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) == null) {
                        Attribute attribute = se.getAttributeByName(new QName("href"));
                        attribute.getValue();
                        event = reader.nextTag();
                        se = (StartElement) event;
                        if (se.getName().toString().equals("span")) {
                            event = reader.nextEvent();
                            Characters character = (Characters) event;
                            String url = character.getData();
                            if (url.contains("Điều Hòa") && count < 19) {
                                constant.THIEN_PHU_PRODUCT_LIST.add(attribute.getValue());
                                constant.THIEN_PHU_PRODUCT_CATEGORY.add(character.getData().trim());
                                count++;
//                                System.out.println(character.getData().trim() + "\t" + attribute.getValue());
//                                tp.setLink(attribute.getValue());
                                tp.setCategory(character.getData().trim());
                                tp = new ProductDetails();
                            }
                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromThienPhuPageDetails(String filePath, String categoryName) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> listProduct = new ArrayList<>();
        ArrayList<String> listDetails = new ArrayList<>();
        ProductDetails tp = new ProductDetails();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("ol") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("products list items product-items")) {
                                isProductTag = true;
                                tp = new ProductDetails();
                            }
                        }

                    }
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("img") && se.getAttributeByName(new QName("src")) != null) {
                        Attribute img = se.getAttributeByName(new QName("src"));
                        tp.setImage(img.getValue());
//                        System.out.println(img.getValue());
                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) != null && se.getAttributeByName(new QName("tabindex")) == null) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        if (item.getValue().equals("product-item-link")) {
                            Attribute attribute = se.getAttributeByName(new QName("href"));
                            event = reader.nextEvent();
                            Characters characters = (Characters) event;
//                            System.out.println(attribute.getValue());
                            tp.setLinkDetails(attribute.getValue());
//                            System.out.println(characters.getData());
                            tp.setName(characters.getData().toUpperCase());
                            String name = tp.getName();
//                            System.out.println(name);
                            if (name.contains("1 CHIỀU")) {
                                tp.setType("1 Chiều");
                            } else if (name.contains("2 CHIỀU")) {
                                tp.setType("2 Chiều");
                            }
                            if (name.contains("INVERTER")) {
                                tp.setInverter("yes");
                            } else {
                                tp.setInverter("no");
                            }
                            String[] btu = name.split("\\s");
                            for (String wattage : btu) {
                                if (wattage.contains("0BTU") || wattage.contains("000")) {
                                    tp.setPowerConsumption(wattage);
                                    if (tp.getPowerConsumption().contains("9000") || tp.getPowerConsumption().contains("9.000")) {
                                        tp.setPowerConsumption("9000BTU");
                                        tp.setMinArea(0);
                                        tp.setMaxArea(15);
                                    } else if (tp.getPowerConsumption().contains("7000") || tp.getPowerConsumption().contains("7.000")) {
                                        tp.setPowerConsumption("7000BTU");
                                        tp.setMinArea(0);
                                        tp.setMaxArea(12);
                                    } else if (tp.getPowerConsumption().contains("10000") || tp.getPowerConsumption().contains("10.000")) {
                                        tp.setPowerConsumption("10000BTU");
                                        tp.setMinArea(12);
                                        tp.setMaxArea(17);
                                    } else if (tp.getPowerConsumption().contains("12000") || tp.getPowerConsumption().contains("12.000")) {
                                        tp.setPowerConsumption("12000BTU");
                                        tp.setMinArea(15);
                                        tp.setMaxArea(20);
                                    } else if (tp.getPowerConsumption().contains("13000") || tp.getPowerConsumption().contains("13.000") || tp.getPowerConsumption().contains("14000")) {
                                        tp.setPowerConsumption("13000BTU");
                                        tp.setMinArea(15);
                                        tp.setMaxArea(22);
                                    } else if (tp.getPowerConsumption().contains("18000") || tp.getPowerConsumption().contains("18.000")) {
                                        tp.setPowerConsumption("18000BTU");
                                        tp.setMinArea(20);
                                        tp.setMaxArea(30);
                                    } else if (tp.getPowerConsumption().contains("21000") || tp.getPowerConsumption().contains("21.000")) {
                                        tp.setPowerConsumption("21000BTU");
                                        tp.setMinArea(25);
                                        tp.setMaxArea(35);
                                    } else if (tp.getPowerConsumption().contains("22000") || tp.getPowerConsumption().contains("22.000")) {
                                        tp.setPowerConsumption("22000BTU");
                                        tp.setMinArea(27);
                                        tp.setMaxArea(37);
                                    } else if (tp.getPowerConsumption().contains("24000") || tp.getPowerConsumption().contains("24.000")) {
                                        tp.setPowerConsumption("24000BTU");
                                        tp.setMinArea(30);
                                        tp.setMaxArea(40);
                                    } else if (tp.getPowerConsumption().contains("28000") || tp.getPowerConsumption().contains("28.000")) {
                                        tp.setPowerConsumption("28000BTU");
                                        tp.setMinArea(37);
                                        tp.setMaxArea(47);
                                    } else if (tp.getPowerConsumption().contains("125000")) {
                                        tp.setPowerConsumption("125000BTU");
                                        tp.setMinArea(160);
                                        tp.setMaxArea(220);
                                    }

                                }

                            }
                            tp.setCategory(categoryName);
                            String details = attribute.getValue().trim();
                            listProduct.add(details);
                        }
                    }
                    listDetails = new ArrayList<>();
                    if (se.getName().toString().equals("div") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        if (item.getValue().equals("product-description")) {
                            try {

                                se = (StartElement) reader.nextTag();
                                event = reader.nextEvent();
                                Characters a = (Characters) event;
//                            System.out.println(a.getData());
                                listDetails.add(a.getData().toUpperCase());
                                event = reader.nextTag();
                                event = reader.nextTag();
                                event = reader.nextEvent();
                                Characters b = (Characters) event;
//                            System.out.println(b.getData());
                                listDetails.add(b.getData().toUpperCase());
                                event = reader.nextTag();
                                event = reader.nextTag();
                                event = reader.nextEvent();
                                Characters c = (Characters) event;
//                            System.out.println(c.getData());
                                listDetails.add(c.getData().toUpperCase());
                                event = reader.nextTag();
                                event = reader.nextTag();
                                event = reader.nextEvent();
                                Characters d = (Characters) event;
//                            System.out.println(d.getData());
                                listDetails.add(d.getData().toUpperCase());
                                event = reader.nextTag();
                                event = reader.nextTag();
                                event = reader.nextEvent();
                                Characters e = (Characters) event;
//                            System.out.println(e.getData());
                                listDetails.add(e.getData().toUpperCase());
                            } catch (Exception e) {
                                System.out.println("");
                            }
                            for (String items : listDetails) {
                                try {
                                    if (items.contains("BTU")) {
                                        if (tp.getPowerConsumption().isEmpty() || tp.getPowerConsumption().equals("BTU")) {
                                            String[] btu = items.split("\\s");
                                            for (String watage : btu) {
                                                if (watage.contains("BTU")) {
                                                    tp.setPowerConsumption(watage);
                                                    if (tp.getPowerConsumption().contains("9000") || tp.getPowerConsumption().contains("9.000")) {
                                                        tp.setPowerConsumption("9000BTU");
                                                        tp.setMinArea(0);
                                                        tp.setMaxArea(15);
                                                    } else if (tp.getPowerConsumption().contains("7000") || tp.getPowerConsumption().contains("7.000")) {
                                                        tp.setPowerConsumption("7000BTU");
                                                        tp.setMinArea(0);
                                                        tp.setMaxArea(12);
                                                    } else if (tp.getPowerConsumption().contains("10000") || tp.getPowerConsumption().contains("10.000")) {
                                                        tp.setPowerConsumption("10000BTU");
                                                        tp.setMinArea(12);
                                                        tp.setMaxArea(17);
                                                    } else if (tp.getPowerConsumption().contains("12000") || tp.getPowerConsumption().contains("12.000")) {
                                                        tp.setPowerConsumption("12000BTU");
                                                        tp.setMinArea(15);
                                                        tp.setMaxArea(20);
                                                    } else if (tp.getPowerConsumption().contains("13000") || tp.getPowerConsumption().contains("13.000")) {
                                                        tp.setPowerConsumption("13000BTU");
                                                        tp.setMinArea(15);
                                                        tp.setMaxArea(22);
                                                    } else if (tp.getPowerConsumption().contains("18000") || tp.getPowerConsumption().contains("18.000")) {
                                                        tp.setPowerConsumption("18000BTU");
                                                        tp.setMinArea(20);
                                                        tp.setMaxArea(30);
                                                    } else if (tp.getPowerConsumption().contains("21000") || tp.getPowerConsumption().contains("21.000")) {
                                                        tp.setPowerConsumption("21000BTU");
                                                        tp.setMinArea(25);
                                                        tp.setMaxArea(35);
                                                    } else if (tp.getPowerConsumption().contains("22000") || tp.getPowerConsumption().contains("22.000")) {
                                                        tp.setPowerConsumption("22000BTU");
                                                        tp.setMinArea(27);
                                                        tp.setMaxArea(37);
                                                    } else if (tp.getPowerConsumption().contains("24000") || tp.getPowerConsumption().contains("24.000")) {
                                                        tp.setPowerConsumption("24000BTU");
                                                        tp.setMinArea(30);
                                                        tp.setMaxArea(40);
                                                    } else if (tp.getPowerConsumption().contains("28000") || tp.getPowerConsumption().contains("28.000")) {
                                                        tp.setPowerConsumption("28000BTU");
                                                        tp.setMinArea(37);
                                                        tp.setMaxArea(47);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (items.contains("CHIỀU") && tp.getType().isEmpty()) {
                                        tp.setType(items);
                                    }

                                } catch (Exception e) {
                                    System.out.println("");
                                }
                            }
                        }
                    }
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("span")) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        event = reader.nextEvent();
                        if (item.getValue().equals("price")) {
                            Characters price = (Characters) event;
                            String a = price.getData().trim();

                            String subPrice = a.split(" ₫")[0];
//                            System.out.println("price: " + subPrice);
                            subPrice = subPrice.replaceAll("\\.", "");
                            tp.setPrice(Float.parseFloat(subPrice));

                            constant.LIST_PRODUCT.getProductDetails().add(tp);
                            tp = new ProductDetails();
                        }
                    }
                }

                constant.LIST_LINK_PRODUCT_DETAIL.clear();
                for (String a : listProduct) {
                    constant.LIST_LINK_PRODUCT_DETAIL.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromThienPhuProductDetails(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> listProduct = new ArrayList<>();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("ol") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("products list items product-items")) {
                                isProductTag = true;

                            }
                        }

                    }
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("img") && se.getAttributeByName(new QName("src")) != null) {
                        Attribute img = se.getAttributeByName(new QName("src"));
//                        System.out.println(img.getValue());
                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) != null && se.getAttributeByName(new QName("tabindex")) == null) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        if (item.getValue().equals("product-item-link")) {
                            Attribute attribute = se.getAttributeByName(new QName("href"));
                            event = reader.nextEvent();
                            Characters characters = (Characters) event;
//                            System.out.println(characters.getData());
//                            System.out.println(attribute.getValue());
                            String details = attribute.getValue().trim();
                            listProduct.add(details);
                        }
                    }
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("span")) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        event = reader.nextEvent();
                        if (item.getValue().equals("price")) {
                            Characters price = (Characters) event;
//                            System.out.println(price.getData());
                        }
                    }
                }

                constant.LIST_LINK_PRODUCT_DETAIL.clear();
                for (String a : listProduct) {
                    constant.LIST_LINK_PRODUCT_DETAIL.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromThienPhuListPage(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> list = new ArrayList();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("li") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        if (item.getValue().equals(("item"))) {
                            se = (StartElement) reader.nextTag();
                            if (se.getName().toString().equals("a") && se.getAttributeByName(new QName("href")) != null && se.getAttributeByName(new QName("class")) != null) {
                                Attribute href = se.getAttributeByName(new QName("href"));
                                list.add(href.getValue().trim());

                            }
                        }
                    }

                }
            }
            constant.LIST_PAGE.clear();
            for (String a : list) {
                constant.LIST_PAGE.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromTheGioiDienMay(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("div") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("menu-menu-chinh-container")) {
                                isProductTag = true;

                            }
                        }

                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) == null) {
                        Attribute attribute = se.getAttributeByName(new QName("href"));
                        attribute.getValue();
                        event = reader.nextEvent();
                        Characters character = (Characters) event;
                        String url = character.getData().trim();
                        if (url.contains("Điều hòa")) {
                            constant.THE_GIOI_DIEN_MAY_PRODUCT_LIST.add(attribute.getValue());
//                            System.out.println("\t" + attribute.getValue());
                        }

                    }

                }

            }
            int a = constant.THE_GIOI_DIEN_MAY_PRODUCT_LIST.size();
//            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromTGDMPageDetails(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> listProduct = new ArrayList<>();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath)));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;

                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("img") && se.getAttributeByName(new QName("src")) != null
                            && se.getAttributeByName(new QName("srcset")) != null) {
                        Attribute img = se.getAttributeByName(new QName("src"));
                        System.out.println(img.getValue());
                    }
                    if (se.getName().toString().equals("h3") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute item = se.getAttributeByName(new QName("class"));
                        if (item.getValue().equals("title_p")) {
                            se = (StartElement) reader.nextTag();
                            if (se.getName().toString().equals("a") && se.getAttributeByName(new QName("href")) != null) {
                                Attribute href = se.getAttributeByName(new QName("href"));
                                event = reader.nextEvent();
                                Characters characters = (Characters) event;
                                System.out.println(characters.getData());
                                System.out.println(href.getValue());
                                String details = href.getValue().trim();
                                listProduct.add(details);

                            }
                        }
                    }
                    if (se.getName().toString().equals("strong") && se.getAttributeByName(new QName("class")) != null) {
                        event = reader.nextEvent();
                        Characters price = (Characters) event;
                        System.out.println(price.getData());

                    }

                }

                constant.LIST_LINK_PRODUCT_DETAIL_TGDM.clear();
                for (String a : listProduct) {
                    constant.LIST_LINK_PRODUCT_DETAIL_TGDM.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staxIteratorFromDMGR(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        int count = 0;
        ProductDetails tp = new ProductDetails();
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            boolean isProductTag = false;
            int isCount = 0;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("ul") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("dropdown-menu megamenu full-width light")) {
                                isProductTag = true;
                                tp = new ProductDetails();
                            }
                        }

                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) == null) {
                        Attribute attribute = se.getAttributeByName(new QName("href"));
                        attribute.getValue();
                        se = (StartElement) event;
                        event = reader.nextEvent();
                        Characters character = (Characters) event;
                        String url = character.getData();
                        if (url.contains("Điều hòa")) {
                            if (isCount <= 11) {
                                constant.TGDM_PRODUCT_LIST.add(attribute.getValue());
                                constant.DMGR_PRODUCT_CATEGORY.add(url);
//                                System.out.println(character.getData().trim() + "\t" + attribute.getValue());
                                isCount++;
                            }
                        }
//                                constant.THIEN_PHU_PRODUCT_LIST.add(attribute.getValue());
//                                constant.THIEN_PHU_PRODUCT_CATEGORY.add(character.getData().trim().substring(10));
//                                System.out.println(character.getData().trim() + "\t" + attribute.getValue());
//                                tp.setLink(attribute.getValue());
//                                tp.setCategory(character.getData().trim());
//                                tp = new ThienPhu() {
//                                };

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductDetails> staxIteratorFromDMGRPageDetails(String filePath) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> listProduct = new ArrayList<>();
        ArrayList<ProductDetails> listDetails = new ArrayList<>();
        ProductDetails tp = new ProductDetails();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("ul") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("row list-products")) {
                                isProductTag = true;
                                tp = new ProductDetails();
                            }
                        }

                    }
                    if (se.getName().toString().equals("span") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute.getValue().equals("woocommerce-Price-amount amount")) {
                            event = reader.nextEvent();
                            Characters price = (Characters) event;
                            String a = price.getData().trim();

//                            System.out.println("price: " + subPrice);
                            a = a.replaceAll("\\,", "");
                            tp.setPrice(Float.parseFloat(a));

//                                System.out.println(price.getData());
//                                tp.setPrice(price.getData().trim());
                        }
                    }
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("img") && se.getAttributeByName(new QName("src")) != null) {
                        Attribute img = se.getAttributeByName(new QName("src"));
                        tp.setImage(img.getValue());

//                        tp.setImage(img.getValue());
//                        System.out.println(img.getValue());
//                        tp.setImage(img.getValue());
                    }
                    if (se.getAttributeByName(new QName("href")) != null && se.getName().toString().equals("a") && se.getAttributeByName(new QName("class")) == null && se.getAttributeByName(new QName("title")) != null) {
                        Attribute name = se.getAttributeByName(new QName("title"));
                        Attribute href = se.getAttributeByName(new QName("href"));
                        listProduct.add(href.getValue());
                        tp.setName(name.getValue().toUpperCase());
                        tp.setLinkDetails(href.getValue());
                        String nameTmp = tp.getName();
                        if (nameTmp.contains("1 CHIỀU")) {
                            tp.setType("1 Chiều");
                        } else if (nameTmp.contains("2 CHIỀU")) {
                            tp.setType("2 Chiều");
                        }
                        if (nameTmp.contains("INVERTER")) {
                            tp.setInverter("yes");
                        } else {
                            tp.setInverter("no");
                        }
//                        System.out.println("buu: "+ name.getValue());
                        listDetails.add(tp);
//                    constant.LIST_PRODUCT.getProductDetails().add(tp);
                        tp = new ProductDetails();
                    }
//                    System.out.println(dto.toString());

//                    constant.LIST_LINK_PRODUCT_DETAIL_DMGR.clear();
//                    for (String a : listProduct) {
//                        constant.LIST_LINK_PRODUCT_DETAIL_DMGR.add(a);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDetails;
    }

    public ProductDetails staxIteratorFromDMGRProductDetails(String filePath, ProductDetails dto) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = null;
        ArrayList<String> listProduct = new ArrayList<>();
        ArrayList<String> listDetails = new ArrayList<>();
        int count = 0;
        try {
            reader = xif.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            boolean isProductTag = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement se = (StartElement) event;
                    if (se.getName().toString().equals("table") && se.getAttributeByName(new QName("class")) != null) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("woocommerce-product-attributes shop_attributes")) {
                                isProductTag = true;
//                                dto = new ProductDetails();
                            }
                        }
                    }
                    listDetails = new ArrayList<>();
                    if (se.getAttributeByName(new QName("class")) != null && se.getName().toString().equals("td") && count == 0) {
                        Attribute attribute = se.getAttributeByName(new QName("class"));
                        if (attribute.getValue().equals("woocommerce-product-attributes-item__value")) {
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            Characters a = (Characters) event;
                            listDetails.add(a.getData().toUpperCase());
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            Characters b = (Characters) event;
                            listDetails.add(b.getData().toUpperCase());
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            Characters c = (Characters) event;
                            listDetails.add(c.getData().toUpperCase());
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextTag();
                            event = reader.nextEvent();
                            Characters d = (Characters) event;
                            listDetails.add(d.getData().toUpperCase());

                            for (String item : listDetails) {
                                if (item.contains("BTU")) {
                                    dto.setPowerConsumption(item);
                                    if (dto.getPowerConsumption().contains("9000")) {
                                        dto.setMinArea(0);
                                        dto.setMaxArea(15);
                                    } else if (dto.getPowerConsumption().contains("12000")) {
                                        dto.setMinArea(15);
                                        dto.setMaxArea(20);
                                    } else if (dto.getPowerConsumption().contains("18000")) {
                                        dto.setMinArea(20);
                                        dto.setMaxArea(30);
                                    } else if (dto.getPowerConsumption().contains("24000")) {
                                        dto.setMinArea(30);
                                        dto.setMaxArea(40);
                                    }
                                }
                                if (item.contains("ĐIỀU HÒA")) {
                                    dto.setCategory(item);
                                }
                                
                            }
                            if(dto.getType() == null){
                                    dto.setType("2 chiều");
                                }
                            constant.LIST_PRODUCT.getProductDetails().add(dto);
                            dto = new ProductDetails();
                            count++;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

}
