package kr.co.noerror.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kr.co.noerror.DAO.Warehouse_DAO;
import kr.co.noerror.DTO.WareHouse_DTO;
import kr.co.noerror.DTO.Warehouse_Fiile_VO;
import kr.co.noerror.Model.M_File_Rename;
import java.util.Random;

@Service
@Repository("Warehouse_Service")
public class Warehouse_Service {

    @Resource(name="M_File_Rename")
    M_File_Rename fname;

    @Resource(name="Warehouse_Fiile_VO")
    Warehouse_Fiile_VO wh_file_vo;

    @Resource(name="Warehouse_DAO")
    Warehouse_DAO ws_dao;

    Random random = new Random();

    // 창고 등록
    public int warehouse_save(
            WareHouse_DTO wh_dto,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        int whsave_result = 0;

        MultipartFile wh_file = wh_dto.getWh_file();
        if(wh_file != null && wh_file.getSize() > 0) {

            try {
                // 파일명 변경
                String file_new = this.fname.rename(wh_file.getOriginalFilename());
                // 실제 저장 경로
                String url = req.getServletContext().getRealPath("/noerro_erp_upload/");

                FileCopyUtils.copy(wh_file.getBytes(), new File(url + file_new));

                wh_dto.setWh_file_url("/wh_images/" + file_new);
                wh_dto.setWh_file_new(file_new);
                wh_dto.setWh_file_ori(wh_file.getOriginalFilename());

                // 창고 코드 랜덤 생성
                int randomNumber = 10000 + this.random.nextInt(90000);
                String wh_code = "WHS-" + randomNumber;
                wh_dto.setWh_code(wh_code);

                // DTO를 맵에 담아 DAO로 전송
                Map<Object, Object> wh_map = new HashMap<>();
                wh_map.put("wh_dto", wh_dto);
                // wh_map.put("file_dto", wh_file_vo); // 필요시 주석 해제

                whsave_result = this.ws_dao.warehouse_save(wh_map);

            } catch(Exception e) {
                System.out.println("창고 등록 오류발생 : " + e);
            }

        }

        return whsave_result;
    }

    // 창고 게시판 목록 및 검색
    public Map<Object, Object> warehouse_list(int page, String wh_search) {
        Map<Object, Object> wh_map = new HashMap<>();

        try {
            // 한 페이지당 게시글 수
            final int pageSize = 5;
            int startIndex = (page - 1) * pageSize;
            int totalCount;

            List<WareHouse_DTO> wh_list_result;

            boolean isSearch = (wh_search != null && !wh_search.trim().isEmpty());

            if(isSearch) {
                wh_list_result = this.ws_dao.search_whpaged(wh_search, startIndex, pageSize);
                totalCount = this.ws_dao.searchTotal(wh_search);
            } else {
                wh_list_result = this.ws_dao.select_wh_list(startIndex, pageSize);
                totalCount = this.ws_dao.getTotalCount();
            }

            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            wh_map.put("wh_list", wh_list_result);
            wh_map.put("search_check", isSearch ? "yesdata" : "nodata");
            wh_map.put("wh_check", wh_list_result.isEmpty() ? "nodata" : "yesdata");
            wh_map.put("currentPage", page);
            wh_map.put("totalCount", totalCount);
            wh_map.put("totalPages", totalPages);
            wh_map.put("pageSize", pageSize);
            wh_map.put("wh_search", wh_search);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return wh_map;
    }

    //창고 게시판 게시물 상세 정보
    public List<WareHouse_DTO> view_wh_detail(String wh_code){
    	
    	List<WareHouse_DTO> wh_detail_result = ws_dao.view_wh_detail("wh_code");
    	
    	return wh_detail_result;
    }
    
}
