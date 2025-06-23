package kr.co.noerror.Model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.co.noerror.DAO.mrp_DAO;
import kr.co.noerror.DTO.*;
import kr.co.noerror.Service.goods_service;
import kr.co.noerror.Service.inventory_service;

@Repository("mrp_Calulation")
public class M_mrp_Calulation {

    @Autowired
    private mrp_DAO mdao;

    @Autowired
    private goods_service g_svc;

    @Autowired
    private inventory_service inv_svc;

    public List<mrp_result2_DTO> mrp_calc(List<mrp_input_DTO> mrp_inputs) {
        Map<String, Integer> ind_item_all_stock = inv_svc.ind_item_all_stock();

        // 상세 결과 리스트 (plan_code + item_code)
        List<mrp_result2_DTO> detailList = new ArrayList<>();

        for (mrp_input_DTO input : mrp_inputs) {
            List<bom_DTO> boms = mdao.boms_for_mrp(input.getBom_code());

            for (bom_DTO bom : boms) {
                String plan_code = input.getPlan_code();
                String bom_code = input.getBom_code();
                System.out.println(bom_code);
                String item_code = bom.getITEM_CODE();
                int required_qty = bom.getBOM_QTY() * input.getProduct_qty();
                int total_stock = ind_item_all_stock.getOrDefault(item_code, 0);
                int safety_stock = mdao.itm_safe_stock(item_code);
                int reserved_stock = 0;
                int available_stock = total_stock - safety_stock;
                int shortage_stock = Math.min(available_stock - required_qty, 0);

                mrp_result2_DTO detail = new mrp_result2_DTO(
                        plan_code,
                        bom_code,
                        item_code,
                        bom.getITEM_TYPE(),
                        bom.getITEM_NAME(),
                        required_qty,
                        bom.getITEM_UNIT(),
                        bom.getITEM_COST(),
                        total_stock,
                        safety_stock,
                        reserved_stock,
                        available_stock,
                        shortage_stock
                );

                detailList.add(detail);
            }
        }
        System.out.println(detailList);
        return detailList; // 전체 상세 결과 반환 (요약은 JS에서 처리)
    }
}
