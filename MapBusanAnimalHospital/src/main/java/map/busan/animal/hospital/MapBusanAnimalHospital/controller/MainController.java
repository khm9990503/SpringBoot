package map.busan.animal.hospital.MapBusanAnimalHospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import map.busan.animal.hospital.MapBusanAnimalHospital.vo.ItemVO;
import map.busan.animal.hospital.MapBusanAnimalHospital.vo.ResultVO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class MainController {

    @GetMapping(value={"/","/index"})
    public String index(Model model) throws UnsupportedEncodingException {
        //API 정보
        String apiURL = "http://apis.data.go.kr/6260000/BusanAnimalHospService/getTblAnimalHospital";
        String serviceKey = "WWltWyH%2BBfK2QsquwkUSkcF7sT5RXBLwPTyIqgwayge40%2BhNxWswEaYhOfL29YQcTBPCyxp4vA%2BIEP3Y7dmo6Q%3D%3D";
        String resultType = "json";
        String pageNo = "1";
        String numOfRows = "1000";

        URI uri = UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("resultType", resultType)

                .build(true)
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);


        //JSON 문자열
        String jsonData = result.getBody();

        //json 파싱
        ObjectMapper om = new ObjectMapper();
        try {

            ResultVO resultVO = om.readValue(jsonData, ResultVO.class );
            ItemVO[] items = resultVO.getGetTblAnimalHospital().getBody().getItems().getItem();
            System.out.println(items[0].getGugun());
            model.addAttribute("items",items);

        } catch (JsonMappingException e){
            e.getStackTrace();
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "index";
    }

}
