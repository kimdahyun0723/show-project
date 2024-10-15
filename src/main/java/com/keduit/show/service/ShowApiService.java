package com.keduit.show.service;

import com.keduit.show.constant.Genre;
import com.keduit.show.constant.Location;
import com.keduit.show.constant.State;
import com.keduit.show.constant.TicketStatus;
import com.keduit.show.dto.ShowFacilityDTO;
import com.keduit.show.dto.ShowingDTO;
import com.keduit.show.entity.ShowFacility;
import com.keduit.show.entity.Showing;
import com.keduit.show.repository.ShowFacilityRepository;
import com.keduit.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowApiService {

    //api 파싱 서비스

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowFacilityRepository showFacilityRepository;

    //api 서비스키
    @Value("${serviceKey}")
    private String serviceKey;

    //xml 노드 값과 필드변수 매칭
    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if(nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    //공연아이디만 리스트로 파싱
    public List<String> showApiIdParseXml(String xmlData) throws Exception{
        List<String> showIds = new ArrayList<>();

        //xml 에서 필요한 데이터만 추출
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlData)));

        NodeList dbListNodes = document.getElementsByTagName("db");

        for (int i = 0; i < dbListNodes.getLength(); i++){
            Node dbNode = dbListNodes.item(i);

            if(dbNode.getNodeType() == Node.ELEMENT_NODE){
                Element dbElement = (Element) dbNode;
                String mt20id = getElementValue(dbElement, "mt20id");
                showIds.add(mt20id);
            }
        }
        return showIds;
    }

    //공연정보 xml 하나를 showDTO 로 파싱
    public ShowingDTO showApiParseXml(String xmlData) throws Exception{
        ShowingDTO showDto = null;

        //xml 에서 필요한 데이터만 추출
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlData)));

        NodeList dbListNodes = document.getElementsByTagName("db");

        for (int i = 0; i < dbListNodes.getLength(); i++) {
            Node dbNode = dbListNodes.item(i);

            if (dbNode.getNodeType() == Node.ELEMENT_NODE) {
                Element dbElement = (Element) dbNode;

                ShowingDTO showDTO;

                //Show 필드변수 추출
                String mt20id = getElementValue(dbElement, "mt20id");
                String prfnm = getElementValue(dbElement, "prfnm");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate prfpdfrom = LocalDate.parse(getElementValue(dbElement, "prfpdfrom"), formatter);
                LocalDate prfpdto = LocalDate.parse(getElementValue(dbElement, "prfpdto"), formatter);

                String fcltynm = getElementValue(dbElement, "fcltynm");
                String prfcast = getElementValue(dbElement, "prfcast");
                String prfcrew = getElementValue(dbElement, "prfcrew");

                String prfruntime = getElementValue(dbElement, "prfruntime");
                String prfage = getElementValue(dbElement, "prfage");

                String entrpsnm = getElementValue(dbElement, "entrpsnm");
                String entrpsnmH = getElementValue(dbElement, "entrpsnmH");

                //첫번째 가격만 파싱
                String str = getElementValue(dbElement, "pcseguidance");
                if(str == null || str.length() == 0) {
                    str = "0";
                } else if (str.indexOf("원") < 0) {
                    str = "0";
                } else{
                    str = str.substring(0, str.indexOf("원")).replaceAll("[^0-9]", "");
                }
                Integer pcseguidance = Integer.valueOf(str);

                String poster = getElementValue(dbElement, "poster");

                Location area = Location.nameOf(getElementValue(dbElement, "area"));
                Genre genrenm = Genre.nameOf(getElementValue(dbElement, "genrenm"));
                State prfstate = State.nameOf(getElementValue(dbElement, "prfstate"));

                //첫번쨰 상세이미지
                String styurl = null;
                NodeList styurlListNodes = document.getElementsByTagName("styurls");
                Node styurlNode = styurlListNodes.item(0);
                if (styurlNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element styurlElement = (Element) styurlNode;
                    styurl = getElementValue(dbElement, "styurl");
                }

                String mt10id = getElementValue(dbElement, "mt10id");
                Integer likeCount = 0;
                Integer ticket = 30;
                TicketStatus ticketStatus = TicketStatus.SELL;

                showDto = new ShowingDTO(mt20id, prfnm, prfpdfrom, prfpdto
                        , fcltynm, prfcast, prfcrew, prfruntime, prfage
                        , entrpsnm, entrpsnmH, pcseguidance, poster, area
                        , genrenm, prfstate, styurl, mt10id, likeCount, ticket, ticketStatus);
            }
        }
        return showDto;
    }

    //showing api 연결 -> xml 파싱 -> db 저장
//    @Async
//    @Scheduled(cron="0 0 0 * * *") //매일 자정에 실행
    public void saveShow() throws Exception{
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        RestTemplate restTemplate = new RestTemplate();

        //아이디 리스트 가져오기
        String showIdUrl = "http://kopis.or.kr/openApi/restful/pblprfr?" +
                "service=" + serviceKey +
                "&cpage=1" +
                "&rows=200";
        System.out.println(showIdUrl);

        URI uri = new URI(showIdUrl);
        String xmlData = restTemplate.getForObject(uri, String.class);

        List<String> showIdlist = showApiIdParseXml(xmlData);

        HttpURLConnection urlConnection2 = null;
        InputStream stream2 = null;
        String result2 = null;
        RestTemplate restTemplate2 = new RestTemplate();

        for(int i = 0; i < showIdlist.size(); i++){
            String showUrl = "http://kopis.or.kr/openApi/restful/pblprfr/" +
                    showIdlist.get(i) +
                    "?service=" + serviceKey;
            System.out.println(showUrl);

            URI uri2 = new URI(showUrl);
            String xmlData2 = restTemplate2.getForObject(uri2, String.class);

            ShowingDTO showDTO = showApiParseXml(xmlData2);

            //새로운 값만 추가
            if (!showRepository.existsByMt20id(showDTO.getMt20id())) {
                Showing show = showDTO.toEntity(); // DTO를 엔티티로 변환
                showRepository.save(show); // 데이터베이스에 저장
                System.out.println("-----------새로운 mt20id 저장됨: " + showIdlist.get(i));
            } else {
                System.out.println("-----------이미 존재하는 mt20id: " + showIdlist.get(i));
            }
        }
    }



    //공연 시설 xml -> list 로 파싱
    public ShowFacilityDTO showFacilityApiParseXml(String xmlData) throws Exception {
        ShowFacilityDTO showFacilityDTO = null;

        //xml 에서 필요한 데이터만 추출
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlData)));

        NodeList dbListNodes = document.getElementsByTagName("db");
        System.out.println("dbList 개수 : " + dbListNodes.getLength());
        Node dbNode = dbListNodes.item(0);

        if (dbNode.getNodeType() == Node.ELEMENT_NODE) {
            Element dbElement = (Element) dbNode;

            String mt10id = getElementValue(dbElement, "mt10id");
            String adres = getElementValue(dbElement, "adres");
            Double la = Double.valueOf(getElementValue(dbElement, "la"));
            Double lo = Double.valueOf(getElementValue(dbElement, "lo"));

            showFacilityDTO = new ShowFacilityDTO(mt10id, adres, la, lo);
        }
        return showFacilityDTO;
    }

    //showFacility api 연결 -> xml 파싱 -> db 저장
//    @Async
//    @Scheduled(cron="0 0 0 * * *") //매일 자정에 실행
    public void saveShowFacility() throws Exception{
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        RestTemplate restTemplate = new RestTemplate();

        List<String> showFacilityIds = showRepository.findMt10idAll(); //공연시설아이디 리스트

        //http://www.kopis.or.kr/openApi/restful/prfplc/FC001247?service={ServiceKey}
        for(int i = 0; i < showFacilityIds.size(); i++) {
            String startUrl = "http://kopis.or.kr/openApi/restful/prfplc/" +
                    showFacilityIds.get(i) +
                    "?service=" + serviceKey;
            System.out.println(startUrl);

            URI uri = new URI(startUrl);
            String xmlData = restTemplate.getForObject(uri, String.class);

            ShowFacilityDTO showFacilityDTO = showFacilityApiParseXml(xmlData);

            //새로운 값만 추가
            if (!showFacilityRepository.existsByMt10id(showFacilityDTO.getMt10id())) {
                ShowFacility showFacility = ShowFacility.toEntity(showFacilityDTO);
                showFacilityRepository.save(showFacility);
                System.out.println("-----------새로운 mt20id 저장됨: " + showFacilityIds.get(i));
            } else {
                System.out.println("-----------이미 존재하는 mt20id: " + showFacilityIds.get(i));
            }
        }
    }

    //공연종료 후 일주일이 지난 공연 삭제
//    @Async
//    @Scheduled(cron="0 0 0 * * *") //매일 자정에 실행
    public void deleteShow(){
        List<String> showIds = showRepository.findMt20idByPrfpdtoBefore(LocalDate.now().minusWeeks(2));
        for(String showId : showIds){
            showRepository.deleteById(showId);
        }
    }


}
