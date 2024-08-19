package com.multi.happytails.main.controller;

import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import com.multi.happytails.shop.service.SalesService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.multi.happytails.main.controller
 * fileName       : AdminSalesListController
 * author         : EUN SOO
 * date           : 2024-08-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-16        EUN SOO       최초 생성
 */
@Controller
public class AdminSalesListController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private UploadService uploadService;

    @GetMapping("/admin/salesListAdmin")
    public String salesListAdmin(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 9;
        List<SalesGoodsDTO> salesGoodsList = salesService.salesList(page, pageSize);
        int totalSalesCount = salesService.salesPageCount();
        int totalPages = (int) Math.ceil((double) totalSalesCount / pageSize);

        Map<Integer, List<UploadDto>> salesGoodsMap = new HashMap<>();

        for (SalesGoodsDTO SalesGoods : salesGoodsList) {
            int goodsNo = SalesGoods.getNo();
            salesGoodsMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }
        model.addAttribute("salesGoodsMap", salesGoodsMap);

        model.addAttribute("salesGoodsList", salesGoodsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/tables5";
    }
}
