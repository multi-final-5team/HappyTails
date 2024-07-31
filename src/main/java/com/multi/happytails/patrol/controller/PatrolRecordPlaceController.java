package com.multi.happytails.patrol.controller;



import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.patrol.model.dao.PatrolPlaceDAO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceDTO;
import com.multi.happytails.patrol.model.dto.PrecordPlaceHistoryDTO;
import com.multi.happytails.patrol.model.dto.PlacePointListDTO;
import com.multi.happytails.patrol.service.PatrolPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.multi.happytails.patrol.controller
 * fileName       : PatrolRecordPlaceController
 * author         : wss18
 * date           : 2024-07-25
 * 설명    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        wss18       최초 생성
 */


@Controller
@RequiredArgsConstructor
@RequestMapping("/patrolRecordPlace")
public class PatrolRecordPlaceController {

    @Autowired
    PatrolPlaceService patrolPlaceService;

    @RequestMapping("patrolRecordPlace")
    public String patrolRecordPlace(){
        return "/patrol/patrolRecordPlace";
    }

    @RequestMapping("patrolRecordPlaceInsert")
    public String  patrolRecordPlaceInsert(){
        return "/patrol/patrolRecordPlaceInsert";
    }

    @PostMapping("placeInsert")
    @ResponseBody
    public String placeInsert(@RequestBody List<PrecordPlaceHistoryDTO> tracepoint , @AuthenticationPrincipal CustomUser customUser){
        System.out.println(tracepoint);

        int patrolNo = patrolPlaceService.findPatrolNo((int) customUser.getNo());
        double precordTotal = 0;

        for (int i = 0; i < tracepoint.size() - 1; i++) {
            precordTotal += distance(tracepoint.get(i).getLatitude() , tracepoint.get(i).getLongitude() , tracepoint.get(i + 1).getLatitude() , tracepoint.get(i + 1).getLongitude());
        }

        System.out.println("총거리 >> " + precordTotal);

        PrecordPlaceDTO precordPlaceDTO = new PrecordPlaceDTO();
        precordPlaceDTO.setPatrolNo(patrolNo);
        precordPlaceDTO.setPrecordTotal(precordTotal);

        int precordPlaceInsertResult = patrolPlaceService.patrolPlaceInsert(precordPlaceDTO);

        int lastPlaceNo = patrolPlaceService.findLastPlaceNo(patrolNo);

        for (int i = 0; i < tracepoint.size(); i++) {
            PrecordPlaceHistoryDTO precordPlaceHistoryDTO = tracepoint.get(i);

            precordPlaceHistoryDTO.setPrecordPlaceNo(lastPlaceNo);

            patrolPlaceService.precordPlaceHistoryInsert(precordPlaceHistoryDTO);
        }

        return "patrol/patrolRecordPlaceInsert";
    }

    @GetMapping("findAllPlace")
    public String findAllPlace(Model model, @AuthenticationPrincipal CustomUser customUser) throws Exception {
        int patrolNo = patrolPlaceService.findPatrolNo((int) customUser.getNo());

        List<PrecordPlaceDTO> list = patrolPlaceService.findAllPrecordPlace(patrolNo);
        model.addAttribute("placeList", list);
        return "patrol/patrolRecordPlace::#placeTbody";
    }

    @PostMapping("deletePlace")
    public String  deletePlace(@RequestBody PrecordPlaceDTO targetplace){

        int deletePlaceHistoryResult = patrolPlaceService.precordPlaceHistoryDelete(targetplace.getPrecordPlaceNo());

        int deletePlaceResult = patrolPlaceService.deletePlace(targetplace);

        return "/patrol/patrolRecordPlace";
    }




    // 두 좌표 사이의 거리를 구하는 함수
    // dsitance(첫번쨰 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    public double distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 meter
    }

    //10진수를 radian(라디안)으로 변환
    public double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    public double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

}
